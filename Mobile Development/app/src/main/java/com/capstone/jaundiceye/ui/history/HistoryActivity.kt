package com.capstone.jaundiceye.ui.history

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.capstone.jaundiceye.R
import com.capstone.jaundiceye.databinding.ActivityHistoryBinding

class HistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHistoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.subheaderText.text = getString(R.string.subheader_history_text)
        binding.toolbarTitle.text = getString(R.string.header_history_text)
        binding.apply {
            toolbarBack.setOnClickListener { finish() }
        }
    }
}