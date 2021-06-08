package com.bangkit.laporaja.views.post

import android.graphics.Bitmap
import android.graphics.Matrix
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.camera.core.impl.utils.Exif
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.bangkit.laporaja.MainActivity
import com.bangkit.laporaja.data.entity.Report
import com.bangkit.laporaja.databinding.FragmentPostBinding
import com.bumptech.glide.Glide
import com.google.android.gms.auth.api.signin.GoogleSignIn
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.*

class PostFragment : Fragment() {
    private var _binding: FragmentPostBinding? = null
    private val binding get() = _binding!!
    private lateinit var currentActivity: MainActivity
    private lateinit var currentReport: Report
    private val args: PostFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPostBinding.inflate(inflater, container, false)

        currentActivity = activity as MainActivity

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbar = binding.topAppBar
        currentActivity.setSupportActionBar(toolbar)
        currentActivity.supportActionBar?.setDisplayHomeAsUpEnabled(true)

        Glide.with(view)
            .load(args.filePost)
            .into(binding.imagePreview)

        Log.d("FILEPATH", args.filePost)

        binding.btnKirim.setOnClickListener {
            lifecycleScope.launch(Dispatchers.Default) {
                prepareData()
                withContext(Dispatchers.Main) {
                    val toSendData =
                        PostFragmentDirections.actionPostFragmentToSendDataFragment(currentReport)
                    view.findNavController().navigate(toSendData)
                }
            }
        }
    }

    private fun prepareData() {
        var bitmap = MediaStore.Images.Media.getBitmap(
            currentActivity.contentResolver,
            args.filePost.toUri()
        )

        val matrix = Matrix()
        matrix.postRotate(args.rotation.toFloat())
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)

        val notes = binding.notesInput.editText?.text.toString()
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream)
        val byteArrayImage = stream.toByteArray()

        val base64 = Base64.encodeToString(byteArrayImage, Base64.NO_WRAP)

        val todayDate: Date = Calendar.getInstance().getTime()
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        val todayString: String = formatter.format(todayDate)

        currentReport = Report(
            id = null,
            userId = GoogleSignIn.getLastSignedInAccount(activity)?.id,
            photo = base64,
            description = notes,
            date = todayString,
            location = "${args.city}, ${args.province}",
            latitude = args.latitude,
            longitude = args.longitude
        )

        Log.d("BASE64", base64)
        println(base64.length.toString())
        Log.d("Country : ", args.country)
        Log.d("Province : ", args.province)
        Log.d("City : ", args.city)
        Log.d("Region : ", args.region)
    }
}
