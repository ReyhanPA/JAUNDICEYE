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
import com.capstone.jaundiceye.databinding.ActivityRegisterBinding
import com.capstone.jaundiceye.ui.welcome.WelcomeActivity
import com.capstone.jaundiceye.util.ViewModelFactory

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val viewModel by viewModels<AuthenticationViewModel> {
        ViewModelFactory.getInstance(this, "user")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
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
        binding.tvLogin.setOnClickListener {
            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.btnSignup.setOnClickListener {
            val username = binding.inputUsername.text.toString()
            val password = binding.inputPassword.text.toString()
            val confirmPassword = binding.inputKonfirmPassword.text.toString()

            if (password != confirmPassword) {
                showToast(resources.getString(R.string.confirm_password_not_match))
                return@setOnClickListener
            }

            viewModel.signup(username, password).observe(this) { signupResponse ->
                val message = signupResponse.message
                if (signupResponse.success == true) {
                    showToast(message ?: resources.getString(R.string.register_success_message))
                    val intent = Intent(this@RegisterActivity, WelcomeActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    showToast(message ?: resources.getString(R.string.register_failed_message))
                }
            }
        }
    }

    private fun playAnimation() {
        val username = ObjectAnimator.ofFloat(binding.etUsername, View.ALPHA, 1f).setDuration(100)
        val password = ObjectAnimator.ofFloat(binding.etPassword, View.ALPHA, 1f).setDuration(100)
        val confirmPassword = ObjectAnimator.ofFloat(binding.etKonfirmPassword, View.ALPHA, 1f).setDuration(100)
        val toLogin = ObjectAnimator.ofFloat(binding.llToLogin, View.ALPHA, 1f).setDuration(100)
        val btnSignup = ObjectAnimator.ofFloat(binding.btnSignup, View.ALPHA, 1f).setDuration(100)

        AnimatorSet().apply {
            playSequentially(
                username,
                password,
                confirmPassword,
                toLogin,
                btnSignup,
            )
            startDelay = 500
        }.start()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
        binding.btnSignup.isEnabled = !state
    }
}