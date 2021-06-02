package com.bangkit.laporaja.views.detail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bangkit.laporaja.R
import com.bangkit.laporaja.viewmodels.ReportDetailViewModel

class ReportDetailFragment : Fragment() {

    companion object {
        fun newInstance() = ReportDetailFragment()
    }

    private lateinit var viewModel: ReportDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.report_detail_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ReportDetailViewModel::class.java)
        // TODO: Use the ViewModel
    }

}