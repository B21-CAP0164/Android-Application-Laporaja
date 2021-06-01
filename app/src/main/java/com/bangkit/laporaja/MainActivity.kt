package com.bangkit.laporaja

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.bangkit.laporaja.databinding.ActivityMainBinding
import com.bangkit.laporaja.views.login.LoginActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_Laporaja)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        AppCompatDelegate
            .setDefaultNightMode(
                AppCompatDelegate.MODE_NIGHT_NO
            )

        val account = GoogleSignIn.getLastSignedInAccount(this)
        if (account == null) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finishAfterTransition()
        }

        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment)
        navView.setupWithNavController(navController)
    }

    fun destroy(){
        finishAfterTransition()
    }
}