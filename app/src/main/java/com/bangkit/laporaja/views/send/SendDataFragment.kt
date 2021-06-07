package com.bangkit.laporaja.views.send

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.bangkit.laporaja.MainActivity
import com.bangkit.laporaja.data.entity.Report
import com.bangkit.laporaja.databinding.SendDataFragmentBinding
import com.bangkit.laporaja.viewmodels.SendDataViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel

class SendDataFragment : Fragment() {
    private var _binding: SendDataFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var currentActivity: MainActivity
    private val viewModel by viewModel<SendDataViewModel>()
    private val args: SendDataFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SendDataFragmentBinding.inflate(inflater, container, false)

        currentActivity = activity as MainActivity

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbar = binding.topAppBar
        currentActivity.setSupportActionBar(toolbar)
        currentActivity.supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val model = args.model

        lifecycleScope.launch(Dispatchers.IO) {
            model.photo?.let {
                viewModel.sendDataToPredict(it).collectLatest { result ->
                    if (result.error == null) { // No error
                        Log.d("Prediction", result.prediction.toString())
                        val report = Report(
                            id = null,
                            userId = model.userId,
                            photo = model.photo,
                            description = model.description,
                            date = model.date,
                            location = model.location,
                            latitude = model.latitude,
                            longitude = model.longitude,
                            damageSeverity = result.prediction
                        )
                        Log.d("ID", report.userId.toString())
                        viewModel.sendDataToCloud(report).collectLatest { id ->
                            if (id != 0L) {
                                val curReport = Report(id = id, userId = report.userId)
                                withContext(Dispatchers.Main) {
                                    val toDetail =
                                        SendDataFragmentDirections.actionSendDataFragmentToReportDetailFragment(
                                            curReport,
                                            true
                                        )
                                    view.findNavController().navigate(toDetail)
                                }
                            } else {
                                withContext(Dispatchers.Main) {
                                    Toast.makeText(
                                        activity,
                                        "Data gagal dikirim ke server. Cek kembali koneksi anda.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }
                    } else {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                activity,
                                "Data gagal dikirim ke server. Cek kembali koneksi anda.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }
    }
}