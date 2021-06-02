package com.bangkit.laporaja.views.detail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bangkit.laporaja.R
import com.bangkit.laporaja.databinding.FragmentHistoryBinding
import com.bangkit.laporaja.databinding.ReportDetailFragmentBinding
import com.bangkit.laporaja.viewmodels.HomeViewModel
import com.bangkit.laporaja.viewmodels.ReportDetailViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ReportDetailFragment : Fragment() {
    private var _binding: ReportDetailFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<ReportDetailViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ReportDetailFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }
}