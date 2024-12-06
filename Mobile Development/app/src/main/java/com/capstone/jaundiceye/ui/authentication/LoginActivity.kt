package com.capstone.jaundiceye.ui.authentication

import android.content.Intent
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.capstone.jaundiceye.R

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

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
}