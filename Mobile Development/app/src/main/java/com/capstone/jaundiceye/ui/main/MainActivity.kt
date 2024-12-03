package com.capstone.jaundiceye.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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
                    binding.subheaderText.text = getString(R.string.subheader_home_text)
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
            startActivity(Intent(this, ScannerActivity::class.java))
        }
    }
}