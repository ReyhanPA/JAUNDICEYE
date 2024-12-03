package com.capstone.jaundiceye.ui.scanner

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.capstone.jaundiceye.R
import com.capstone.jaundiceye.databinding.ActivityScannerBinding

class ScannerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScannerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityScannerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.subheaderText.text = getString(R.string.subheader_scanner_text)
        binding.toolbarTitle.text = getString(R.string.header_scanner_text)
    }
}