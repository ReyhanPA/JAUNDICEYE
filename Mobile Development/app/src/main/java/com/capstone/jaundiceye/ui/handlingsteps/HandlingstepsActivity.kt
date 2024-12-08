package com.capstone.jaundiceye.ui.handlingsteps

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import com.capstone.jaundiceye.R
import com.capstone.jaundiceye.databinding.ActivityHandlingstepsBinding

class HandlingstepsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHandlingstepsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHandlingstepsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.subheaderText.text = getString(R.string.subheader_handlingsteps_text)
        binding.toolbarTitle.text = getString(R.string.header_handlingsteps_text)
        binding.apply {
            toolbarBack.setOnClickListener { finish() }
        }
        binding.desc.text = HtmlCompat.fromHtml(
            getString(R.string.handlingsteps_text),
            HtmlCompat.FROM_HTML_MODE_LEGACY
        )
    }
}