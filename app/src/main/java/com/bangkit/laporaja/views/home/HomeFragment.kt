package com.bangkit.laporaja.views.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import com.bangkit.laporaja.R
import com.bangkit.laporaja.databinding.FragmentHomeBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val acct = GoogleSignIn.getLastSignedInAccount(activity)
        val personName = acct?.displayName
        val text = resources.getString(R.string.hello_string,personName)
        val tvHello : AppCompatTextView = view.findViewById(R.id.tv_hello)
        tvHello.text = text
    }

}