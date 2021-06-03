package com.bangkit.laporaja.views.post

import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.bangkit.laporaja.MainActivity
import com.bangkit.laporaja.data.entity.Report
import com.bangkit.laporaja.databinding.FragmentPostBinding
import com.bumptech.glide.Glide
import com.google.android.gms.auth.api.signin.GoogleSignIn
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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
        currentActivity.removeBottomBar()

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

        val notes = binding.notesInput.editText?.text.toString()

        Log.d("FILEPATH", args.filePost)

        lifecycleScope.launch(Dispatchers.IO) {
            val bitmap = MediaStore.Images.Media.getBitmap(
                currentActivity.contentResolver,
                args.filePost.toUri()
            )
            val stream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, stream)
            val byteArrayImage = stream.toByteArray()

            val base64 = Base64.encodeToString(byteArrayImage, Base64.DEFAULT)

            val todayDate: Date = Calendar.getInstance().getTime()
            val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
            val todayString: String = formatter.format(todayDate)

            currentReport = Report(
                id = null,
                userId = GoogleSignIn.getLastSignedInAccount(activity)?.id,
                photo = base64,
                description = notes,
                date = todayString
            )

            Log.d("BASE 64", currentReport.photo.toString())
        }

        binding.btnKirim.setOnClickListener {

        }
    }
}
