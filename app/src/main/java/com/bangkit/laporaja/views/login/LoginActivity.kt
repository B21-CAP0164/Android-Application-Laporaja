package com.bangkit.laporaja.views.login

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bangkit.laporaja.MainActivity
import com.bangkit.laporaja.R
import com.bangkit.laporaja.databinding.ActivityLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity() {
    private var _binding: ActivityLoginBinding? = null
    private val binding get() = _binding!!
    private var doubleBackToExitOnce: Boolean = false

    companion object {
        private const val RC_SIGN_IN = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launchWhenCreated {
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()
            val mGoogleSignInClient = GoogleSignIn.getClient(this@LoginActivity, gso)

            binding.signInButton.setOnClickListener {
                binding.progressBar.visibility = View.VISIBLE
                val signInIntent = mGoogleSignInClient.signInIntent
                startActivityForResult(signInIntent, RC_SIGN_IN)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            completedTask.getResult(ApiException::class.java)

            updateUI(true)
        } catch (e: ApiException) {
            Log.d("TAG", "signInResult:failed code=" + e.statusCode)

            updateUI(false)
        }
    }

    private fun updateUI(bool: Boolean) {
        lifecycleScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.Main) {
                binding.progressBar.visibility = View.GONE
            }

            if (bool) {
                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                startActivity(intent)
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@LoginActivity,
                        resources.getString(
                            R.string.welcome_message,
                            GoogleSignIn.getLastSignedInAccount(this@LoginActivity)?.givenName
                        ),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                this@LoginActivity.finishAfterTransition()
            } else {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@LoginActivity,
                        resources.getString(R.string.login_failed),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    override fun onBackPressed() {
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
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
