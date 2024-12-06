package com.capstone.jaundiceye.ui.authentication

import android.content.Intent
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import android.os.Build
import android.os.Bundle
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
//        playAnimation()

//        viewModel.isLoading.observe(this) {
//            showLoading(it)
//        }
//KODE BUAT SIGN UP NYA BIAR JADI BUTTON
        val signUpText = findViewById<TextView>(R.id.tvSignUp)

        // Kalimat lengkap
        val text = "Belum punya akun? Sign Up"

        // Membuat SpannableString
        val spannableString = SpannableString(text)

        // Menentukan klik pada bagian "Sign Up"
        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                // Intent ke halaman Register
                val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
                startActivity(intent)
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                // Menjaga agar underline tidak muncul
                ds.isUnderlineText = false
            }
        }

        // Memberi span pada kata "Sign Up" (posisi mulai dan akhir teks)
        spannableString.setSpan(clickableSpan, 17, text.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        // Menetapkan teks dan mengaktifkan LinkMovementMethod
        signUpText.text = spannableString
        signUpText.movementMethod = LinkMovementMethod.getInstance()
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
            val username = binding.etEmailUsername.text.toString()
            val password = binding.etPassword.text.toString()

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

//    private fun playAnimation() {
//        ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_X, -30f, 30f).apply {
//            duration = 6000
//            repeatCount = ObjectAnimator.INFINITE
//            repeatMode = ObjectAnimator.REVERSE
//        }.start()
//
//        val title = ObjectAnimator.ofFloat(binding.titleTextView, View.ALPHA, 1f).setDuration(100)
//        val message =
//            ObjectAnimator.ofFloat(binding.messageTextView, View.ALPHA, 1f).setDuration(100)
//        val emailTextView =
//            ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA, 1f).setDuration(100)
//        val emailEditTextLayout =
//            ObjectAnimator.ofFloat(binding.edLoginEmailLayout, View.ALPHA, 1f).setDuration(100)
//        val passwordTextView =
//            ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA, 1f).setDuration(100)
//        val passwordEditTextLayout =
//            ObjectAnimator.ofFloat(binding.edLoginPasswordLayout, View.ALPHA, 1f).setDuration(100)
//        val login = ObjectAnimator.ofFloat(binding.loginButton, View.ALPHA, 1f).setDuration(100)
//
//        AnimatorSet().apply {
//            playSequentially(
//                title,
//                message,
//                emailTextView,
//                emailEditTextLayout,
//                passwordTextView,
//                passwordEditTextLayout,
//                login
//            )
//            startDelay = 100
//        }.start()
//    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

//    private fun showLoading(state: Boolean) {
//        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
//        binding.loginButton.isEnabled = !state
//    }
}