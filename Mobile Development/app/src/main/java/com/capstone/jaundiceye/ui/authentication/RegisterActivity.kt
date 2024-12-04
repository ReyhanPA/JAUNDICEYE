package com.capstone.jaundiceye.ui.authentication

import android.content.Intent
import android.os.Build
import android.os.Bundle
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
//        playAnimation()

//        viewModel.isLoading.observe(this) {
//            showLoading(it)
//        }
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
        binding.btnLogin.setOnClickListener {
            val username = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()

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

//    private fun playAnimation() {
//        ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_X, -30f, 30f).apply {
//            duration = 6000
//            repeatCount = ObjectAnimator.INFINITE
//            repeatMode = ObjectAnimator.REVERSE
//        }.start()
//
//        val title = ObjectAnimator.ofFloat(binding.titleTextView, View.ALPHA, 1f).setDuration(100)
//        val nameTextView =
//            ObjectAnimator.ofFloat(binding.nameTextView, View.ALPHA, 1f).setDuration(100)
//        val nameEditTextLayout =
//            ObjectAnimator.ofFloat(binding.edRegisterNameLayout, View.ALPHA, 1f).setDuration(100)
//        val emailTextView =
//            ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA, 1f).setDuration(100)
//        val emailEditTextLayout =
//            ObjectAnimator.ofFloat(binding.edRegisterEmailLayout, View.ALPHA, 1f).setDuration(100)
//        val passwordTextView =
//            ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA, 1f).setDuration(100)
//        val passwordEditTextLayout =
//            ObjectAnimator.ofFloat(binding.edRegisterPasswordLayout, View.ALPHA, 1f).setDuration(100)
//        val register = ObjectAnimator.ofFloat(binding.registerButton, View.ALPHA, 1f).setDuration(100)
//
//
//        AnimatorSet().apply {
//            playSequentially(
//                title,
//                nameTextView,
//                nameEditTextLayout,
//                emailTextView,
//                emailEditTextLayout,
//                passwordTextView,
//                passwordEditTextLayout,
//                register
//            )
//            startDelay = 100
//        }.start()
//    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

//    private fun showLoading(state: Boolean) {
//        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
//        binding.btnLogin.isEnabled = !state
//    }
}