package com.bangkit.laporaja

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.bangkit.laporaja.databinding.ActivityMainBinding
import com.bangkit.laporaja.views.detail.ReportDetailFragment
import com.bangkit.laporaja.views.detail.ReportDetailFragmentDirections
import com.bangkit.laporaja.views.login.LoginActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.io.File

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private var doubleBackToExitOnce: Boolean = false
    private var isGoingToHome: Boolean = false
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_Laporaja)

        AppCompatDelegate
            .setDefaultNightMode(
                AppCompatDelegate.MODE_NIGHT_NO
            )

        val account = GoogleSignIn.getLastSignedInAccount(this@MainActivity)
        if (account == null) {
            val intent = Intent(this@MainActivity, LoginActivity::class.java)
            startActivity(intent)
            this@MainActivity.finishAfterTransition()
        } else {
            lifecycleScope.launchWhenCreated {
                _binding = ActivityMainBinding.inflate(layoutInflater)
                setContentView(binding.root)

                navView = binding.navView
                val navController = findNavController(R.id.nav_host_fragment)
                navHostFragment =
                    supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
                navView.setupWithNavController(navController)
            }
        }

        super.onCreate(savedInstanceState)
    }

    fun destroy() {
        finishAfterTransition()
    }

    fun getOutputDirectory(): File {
        val mediaDir = externalMediaDirs.firstOrNull()?.let {
            File(it, resources.getString(R.string.app_name)).apply { mkdirs() }
        }
        return if (mediaDir != null && mediaDir.exists())
            mediaDir else filesDir
    }

    fun showBottomBar() {
        navView.visibility = View.VISIBLE
    }

    fun removeBottomBar() {
        navView.visibility = View.GONE
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return false
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
            Log.d("Current isGoingToHome", isGoingToHome.toString())
            if (isGoingToHome) {
                val currentFragment = navHostFragment.childFragmentManager.fragments[0]
                Log.d("Masuk", "Report Detail Fragment")
                val detailFragment = currentFragment as ReportDetailFragment
                val toHome =
                    ReportDetailFragmentDirections.actionReportDetailFragmentToNavigationHome()
                detailFragment.findNavController().navigate(toHome)
                isGoingToHome = false
            } else {
                super.onBackPressed()
                return
            }
        }
    }

    fun popToHome() {
        isGoingToHome = true
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}