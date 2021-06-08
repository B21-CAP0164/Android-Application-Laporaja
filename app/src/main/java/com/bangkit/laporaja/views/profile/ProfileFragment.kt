package com.bangkit.laporaja.views.profile

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.bangkit.laporaja.MainActivity
import com.bangkit.laporaja.R
import com.bangkit.laporaja.databinding.FragmentProfileBinding
import com.bangkit.laporaja.views.login.LoginActivity
import com.bumptech.glide.Glide
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var currentActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        currentActivity = activity as MainActivity

        setHasOptionsMenu(true)
        val toolbar = binding.topAppBar
        currentActivity.setSupportActionBar(toolbar)
        currentActivity.showBottomBar()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val acct = GoogleSignIn.getLastSignedInAccount(activity)
        binding.tvName.text = acct?.displayName
        binding.tvEmail.text = acct?.email
        Glide.with(view)
            .load(acct?.photoUrl)
            .into(binding.profileImage)

        val logoutAlert = activity?.let {
            AlertDialog.Builder(it).setIcon(R.drawable.ic_exit_black)
                .setTitle("Keluar")
                .setMessage("Yakin ingin keluar?")
                .setPositiveButton("Keluar") { _, _ ->
                    signOut()
                    Toast.makeText(activity, "Berhasil keluar", Toast.LENGTH_SHORT).show()
                }.setNegativeButton("Tidak", null)
        }

        binding.logout.setOnClickListener {
            logoutAlert?.show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.setting_menu) {
            val toSetting = ProfileFragmentDirections.actionNavigationProfileToSettingsActivity()
            view?.findNavController()?.navigate(toSetting)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun signOut() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        val mGoogleSignInClient = GoogleSignIn.getClient(currentActivity, gso)

        mGoogleSignInClient.signOut().addOnCompleteListener(currentActivity) {
            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
            currentActivity.destroy()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}