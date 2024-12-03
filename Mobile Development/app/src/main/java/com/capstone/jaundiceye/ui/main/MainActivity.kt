package com.capstone.jaundiceye.ui.main

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.TypefaceSpan
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.capstone.jaundiceye.R
import com.capstone.jaundiceye.databinding.ActivityMainBinding
import com.capstone.jaundiceye.ui.scanner.ScannerActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        navView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.navigation_home -> {
                    val fullText = getString(R.string.subheader_home_text)
                    val spannable = SpannableString(fullText)
                    val keyword = "JaundicEye."

                    val startIndex = fullText.indexOf(keyword)
                    val endIndex = startIndex + keyword.length

                    if (startIndex != -1) {
                        spannable.setSpan(
                            ForegroundColorSpan(ContextCompat.getColor(this, R.color.deep_blue)),
                            startIndex,
                            endIndex,
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                        )

                        spannable.setSpan(
                            RelativeSizeSpan(1.2f),
                            startIndex,
                            endIndex,
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                        )

                        val typeface = ResourcesCompat.getFont(this, R.font.abril_fatface_regular)
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                            spannable.setSpan(
                                typeface?.let { TypefaceSpan(it) },
                                startIndex,
                                endIndex,
                                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                            )
                        }
                    }

                    binding.subheaderText.text = spannable
                    binding.toolbarTitle.text = getString(R.string.header_home_text)
                }
                R.id.navigation_recommendation -> {
                    binding.subheaderText.text = getString(R.string.subheader_recommendation_text)
                    binding.toolbarTitle.text = getString(R.string.header_recommendation_text)
                }
                R.id.navigation_article -> {
                    binding.subheaderText.text = getString(R.string.subheader_article_text)
                    binding.toolbarTitle.text = getString(R.string.header_article_text)
                }
                R.id.navigation_profile -> {
                    binding.subheaderText.text = getString(R.string.subheader_profile_text)
                    binding.toolbarTitle.text = getString(R.string.header_profile_text)
                }
            }
        }

        binding.fabScanner.setOnClickListener {
            val intent = Intent(this, ScannerActivity::class.java)
            startActivity(intent)
        }
    }
}