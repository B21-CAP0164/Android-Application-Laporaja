package com.bangkit.laporaja.views.detail

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.bangkit.laporaja.MainActivity
import com.bangkit.laporaja.R
import com.bangkit.laporaja.data.entity.Report
import com.bangkit.laporaja.databinding.ReportDetailFragmentBinding
import com.bangkit.laporaja.utils.ShimmerDrawableInit
import com.bangkit.laporaja.viewmodels.ReportDetailViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel

class ReportDetailFragment : Fragment() {
    private var _binding: ReportDetailFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<ReportDetailViewModel>()
    private lateinit var currentActivity: MainActivity
    private lateinit var currentReport: Report

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ReportDetailFragmentBinding.inflate(inflater, container, false)
        currentActivity = activity as MainActivity

        lifecycleScope.launchWhenCreated {
            val args: ReportDetailFragmentArgs by navArgs()
            if (args.isGoingBackToHome) {
                currentActivity.popToHome()
            }
            currentReport = args.report
        }

        currentActivity.removeBottomBar()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbar = binding.topAppBar
        currentActivity.setSupportActionBar(toolbar)
        toolbar.title = resources.getString(R.string.detail_title, currentReport.id.toString())
        currentActivity.supportActionBar?.setDisplayHomeAsUpEnabled(true)

        lifecycleScope.launch(Dispatchers.Default) {
            viewModel.getDetails(currentReport.userId as String, currentReport.id as Long)
                .collectLatest {
                    withContext(Dispatchers.Main) {
                        if (it.id != null) {
                            setData(it)
                            Handler(Looper.getMainLooper()).postDelayed({
                                binding.detailEmpty.visibility = View.GONE
                                binding.shimmerDetail.visibility = View.GONE
                                binding.appBarLayout.visibility = View.VISIBLE
                                binding.mainDetailView.visibility = View.VISIBLE
                            }, 300)
                        } else {
                            Handler(Looper.getMainLooper()).postDelayed({
                                binding.detailEmpty.visibility = View.VISIBLE
                                binding.shimmerDetail.visibility = View.GONE
                                binding.appBarLayout.visibility = View.VISIBLE
                                binding.mainDetailView.visibility = View.GONE
                            }, 300)
                        }
                    }
                }
        }
    }

    private fun setData(report: Report) {
        binding.tvIdDetail.text = resources.getString(R.string.id_laporan, report.id.toString())
        binding.tvLocation.text = report.location
        binding.tvNotes.text = report.description
        binding.tvTingkatKeparahan.text = report.damageSeverity
        binding.tvCreatedAt.text = report.date

        Glide.with(this)
            .load(report.photo)
            .apply(
                RequestOptions().override(400, 400).placeholder(ShimmerDrawableInit.shimmerDrawable)
                    .error(R.drawable.ic_broken_image_black)
            )
            .into(binding.imgDetail)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}