package com.bangkit.laporaja.views.send

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bangkit.laporaja.R
import com.bangkit.laporaja.viewmodels.SendDataViewModel

class SendDataFragment : Fragment() {

    private lateinit var viewModel: SendDataViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.send_data_fragment, container, false)
    }
}