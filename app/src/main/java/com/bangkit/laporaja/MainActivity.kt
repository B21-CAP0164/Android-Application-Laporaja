package com.bangkit.laporaja

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.bangkit.laporaja.databinding.ActivityMainBinding
import com.bangkit.laporaja.views.login.LoginActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var doubleBackToExitOnce: Boolean = false
    private lateinit var navHostFragment: NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_Laporaja)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        AppCompatDelegate
            .setDefaultNightMode(
                AppCompatDelegate.MODE_NIGHT_NO
            )

        lifecycleScope.launchWhenCreated {
            val account = GoogleSignIn.getLastSignedInAccount(this@MainActivity)
            if (account == null) {
                val intent = Intent(this@MainActivity, LoginActivity::class.java)
                startActivity(intent)
                this@MainActivity.finishAfterTransition()
            }
        }

        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment)
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navView.setupWithNavController(navController)
    }

    fun destroy(){
        finishAfterTransition()
    }

    override fun onBackPressed() {
        if (navHostFragment.childFragmentManager.backStackEntryCount == 0) {
            if (doubleBackToExitOnce) {
                finishAfterTransition()
                return
            }

            this.doubleBackToExitOnce = true

            Toast.makeText(
                this,
                resources.getString(R.string.exit_confirmation),
                Toast.LENGTH_SHORT
            ).show()

            Handler(Looper.getMainLooper()).postDelayed({
                kotlin.run { doubleBackToExitOnce = false }
            }, 2000)
        } else {
            super.onBackPressed()
            return
        }
    }
}