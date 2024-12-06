package com.capstone.jaundiceye.ui.home

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.capstone.jaundiceye.R
import com.capstone.jaundiceye.databinding.FragmentHomeBinding
import com.capstone.jaundiceye.ui.history.HistoryActivity
import com.capstone.jaundiceye.ui.scanner.ScannerActivity

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) : View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        playAnimation()

        binding?.apply {
            buttonHomeToScanner.setOnClickListener {
                val intent = Intent(requireActivity(), ScannerActivity::class.java)
                startActivity(intent)
            }
            buttonHomeToHistory.setOnClickListener {
                val intent = Intent(requireActivity(), HistoryActivity::class.java)
                startActivity(intent)
            }
            buttonHomeToArtikel.setOnClickListener {
                Toast.makeText(requireActivity(), resources.getString(R.string.go_to_tab_article), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun playAnimation() {
        val toScanner = ObjectAnimator.ofFloat(binding?.cardCheck, View.ALPHA, 1f).setDuration(100)
        val toHistory = ObjectAnimator.ofFloat(binding?.cardHistory, View.ALPHA, 1f).setDuration(100)
        val toArticle = ObjectAnimator.ofFloat(binding?.cardArtikel, View.ALPHA, 1f).setDuration(100)

        AnimatorSet().apply {
            playSequentially(
                toScanner,
                toHistory,
                toArticle
            )
            startDelay = 100
        }.start()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}