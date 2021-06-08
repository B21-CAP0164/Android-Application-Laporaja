package com.bangkit.laporaja.views.camera

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.icu.text.SimpleDateFormat
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.util.Size
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.core.impl.utils.Exif
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.bangkit.laporaja.MainActivity
import com.bangkit.laporaja.databinding.FragmentCameraBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.io.File
import java.io.IOException
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class CameraFragment : Fragment() {
    private var _binding: FragmentCameraBinding? = null
    private val binding get() = _binding!!
    private lateinit var currentActivity: MainActivity

    private var imageCapture: ImageCapture? = null
    private var latitude: Double? = null
    private var longitude: Double? = null
    private lateinit var outputDirectory: File
    private lateinit var cameraExecutor: ExecutorService

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    companion object {
        private const val TAG = "CameraXBasic"
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS = arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCameraBinding.inflate(inflater, container, false)
        currentActivity = activity as MainActivity

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(currentActivity)

        if (allPermissionsGranted()) {
            startCamera()
            getLocation()
        } else {
            ActivityCompat.requestPermissions(
                currentActivity, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS
            )
        }

        currentActivity.removeBottomBar()

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        if (allPermissionsGranted()) {
            startCamera()
            getLocation()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbar = binding.topAppBar
        currentActivity.setSupportActionBar(toolbar)
        currentActivity.supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.cameraButton.setOnClickListener {
            binding.cameraLoading.visibility = View.VISIBLE
            takePhoto()
        }

        outputDirectory = currentActivity.getOutputDirectory()

        cameraExecutor = Executors.newSingleThreadExecutor()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults:
        IntArray
    ) {
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                startCamera()
            } else {
                Toast.makeText(
                    currentActivity,
                    "Permissions not granted by the user.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun takePhoto() {
        val geocoder = Geocoder(currentActivity, Locale.getDefault())
        try {
            val addresses: List<Address> = geocoder.getFromLocation(latitude as Double, longitude as Double, 1)
            val obj: Address = addresses[0]

            val imageCapture = imageCapture ?: return

            val photoFile = File(
                outputDirectory,
                SimpleDateFormat(
                    FILENAME_FORMAT,
                    Locale.getDefault()
                ).format(System.currentTimeMillis()) + ".jpg"
            )

            val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile)
                .build()

            imageCapture.takePicture(
                outputOptions, ContextCompat.getMainExecutor(currentActivity),
                object : ImageCapture.OnImageSavedCallback {
                    @SuppressLint("RestrictedApi")
                    override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                        val exif = Exif.createFromFile(photoFile)
                        val rotation = exif.rotation
                        val savedUri = Uri.fromFile(photoFile)
                        val msg = "Photo capture succeeded: $savedUri"
                        binding.cameraLoading.visibility = View.GONE
                        goToPost(
                            savedUri,
                            rotation,
                            obj.countryName,
                            obj.adminArea,
                            obj.subAdminArea,
                            obj.locality,
                            obj.latitude.toFloat(),
                            obj.longitude.toFloat()
                        )
                        Log.d(TAG, msg)
                    }

                    override fun onError(exception: ImageCaptureException) {
                        Log.e(TAG, "Photo capture failed: ${exception.message}", exception)
                    }
                })

        } catch (e: IOException) {
            Log.e(TAG, "Failed :  ${e.message}", e)
        }
    }

    private fun getLocation() {
        if (ActivityCompat.checkSelfPermission(
                currentActivity,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                currentActivity,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Toast.makeText(
                currentActivity,
                "Permissions not granted by the user.",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                latitude = location?.latitude
                longitude = location?.longitude
            }
    }

    private fun goToPost(
        uriFile: Uri,
        rotation: Int,
        country: String,
        province: String,
        city: String,
        region: String,
        latitude: Float,
        longitude: Float
    ) {
        val uri = uriFile.toString()
        val toPost = CameraFragmentDirections.actionNavigationCameraToPostFragment(
            uri,
            country,
            province,
            city,
            region,
            latitude,
            longitude,
            rotation
        )
        view?.findNavController()?.navigate(toPost)
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(currentActivity)

        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(binding.viewFinder.surfaceProvider)
                }

            imageCapture = ImageCapture.Builder()
                .setFlashMode(ImageCapture.FLASH_MODE_AUTO)
                .setTargetResolution(Size(200, 200))
                .build()

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    currentActivity, cameraSelector, preview, imageCapture
                )
            } catch (exc: Exception) {
                Log.e(TAG, "Use case binding failed", exc)
            }
        }, ContextCompat.getMainExecutor(currentActivity))
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            currentActivity, it
        ) == PackageManager.PERMISSION_GRANTED
    }
}
