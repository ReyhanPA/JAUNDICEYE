package com.capstone.jaundiceye.ui.authentication

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.capstone.jaundiceye.R
import com.capstone.jaundiceye.data.pref.UserModel
import com.capstone.jaundiceye.databinding.ActivityLoginBinding
import com.capstone.jaundiceye.ui.main.MainActivity
import com.capstone.jaundiceye.util.ViewModelFactory

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val viewModel by viewModels<AuthenticationViewModel> {
        ViewModelFactory.getInstance(this, "user")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupAction()
        playAnimation()

        viewModel.isLoading.observe(this) {
            showLoading(it)
        }
    }

    private fun setupView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.show(WindowInsets.Type.statusBars())
        } else {
            window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }
        supportActionBar?.hide()
    }

    private fun setupAction() {
        binding.tvSignup.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.btnLogin.setOnClickListener {
            val username = binding.inputUsername.text.toString()
            val password = binding.inputPassword.text.toString()

            viewModel.login(username, password).observe(this) { loginResponse ->
                val message = loginResponse.message
                if (loginResponse.success == true) {
                    viewModel.saveSession(UserModel(username, loginResponse.token.toString()))
                    showToast(message ?: resources.getString(R.string.login_success_message))
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                } else {
                    showToast(message ?: resources.getString(R.string.login_failed_message))
                }
            }
        }
    }

    private fun playAnimation() {
        val username = ObjectAnimator.ofFloat(binding.etUsername, View.ALPHA, 1f).setDuration(100)
        val password = ObjectAnimator.ofFloat(binding.etPassword, View.ALPHA, 1f).setDuration(100)
        val toSignup = ObjectAnimator.ofFloat(binding.llToSignup, View.ALPHA, 1f).setDuration(100)
        val btnLogin = ObjectAnimator.ofFloat(binding.btnLogin, View.ALPHA, 1f).setDuration(100)

        AnimatorSet().apply {
            playSequentially(
                username,
                password,
                toSignup,
                btnLogin,
            )
            startDelay = 500
        }.start()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
        binding.btnLogin.isEnabled = !state
    }
}