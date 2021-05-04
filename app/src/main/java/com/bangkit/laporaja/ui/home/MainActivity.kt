package com.bangkit.laporaja.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.laporaja.databinding.ActivityMainBinding
import com.bangkit.laporaja.ui.login.LoginActivity
import com.bumptech.glide.Glide
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showLoading(true)
        view()
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
            binding.buttonLogout.visibility = View.GONE
        } else {
            binding.progressBar.visibility = View.GONE
            binding.buttonLogout.visibility = View.VISIBLE
        }
    }

    private fun view() {
        val account = GoogleSignIn.getLastSignedInAccount(this)
        if (account != null) {
            showLoading(false)
            binding.nama.text = account.displayName
            binding.email.text = account.email
            Glide.with(this)
                .load(account.photoUrl.toString())
                .into(binding.profileImage)
        }

        binding.buttonLogout.setOnClickListener {
            logOut()
        }
    }

    private fun logOut() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        val mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        binding.buttonLogout.setOnClickListener {
            mGoogleSignInClient.signOut()
                .addOnCompleteListener(this) {
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    this.finish()
                }
        }
    }
}