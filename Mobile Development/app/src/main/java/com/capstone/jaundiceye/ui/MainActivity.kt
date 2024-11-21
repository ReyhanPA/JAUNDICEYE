package com.capstone.jaundiceye.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.capstone.jaundiceye.R
import com.capstone.jaundiceye.databinding.ActivityMainBinding
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
                    binding.textSubheader.text = getString(R.string.home_subheader_text)
                    binding.toolbarTitle.text = getString(R.string.home_toolbar_text)
                }
                R.id.navigation_recommendation -> {
                    binding.textSubheader.text = getString(R.string.recommendation_subheader_text)
                    binding.toolbarTitle.text = getString(R.string.recommendation_toolbar_text)
                }
                R.id.navigation_article -> {
                    binding.textSubheader.text = getString(R.string.article_subheader_text)
                    binding.toolbarTitle.text = getString(R.string.article_toolbar_text)
                }
                R.id.navigation_profile -> {
                    binding.textSubheader.text = getString(R.string.profile_subheader_text)
                    binding.toolbarTitle.text = getString(R.string.profile_toolbar_text)
                }
            }
        }
    }
}