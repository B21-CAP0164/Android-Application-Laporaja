package com.bangkit.laporaja.views.home

import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.bangkit.laporaja.MainActivity
import com.bangkit.laporaja.R
import com.bangkit.laporaja.databinding.FragmentHomeBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var currentActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        currentActivity = activity as MainActivity

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val acct = GoogleSignIn.getLastSignedInAccount(activity)
        val personName = acct?.givenName
        val text = resources.getString(R.string.hello_string, personName)
        binding.tvHello.text = text

        setHasOptionsMenu(true)
        val toolbar = binding.topAppBar
        currentActivity.setSupportActionBar(toolbar)
        currentActivity.supportActionBar?.title = " "

        binding.cameraButton.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_navigation_home_to_navigation_camera, null)
        )
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.setting_menu) {

        }
        return super.onOptionsItemSelected(item)
    }
}