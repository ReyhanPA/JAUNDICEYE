package com.capstone.jaundiceye.ui.scanner

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.capstone.jaundiceye.R
import com.capstone.jaundiceye.data.local.entity.HistoryEntity
import com.capstone.jaundiceye.databinding.ActivityScannerBinding
import com.capstone.jaundiceye.util.ViewModelFactory
import com.capstone.jaundiceye.util.getImageUri
import com.capstone.jaundiceye.util.reduceFileImage
import com.capstone.jaundiceye.util.uriToFile
import com.yalantis.ucrop.UCrop
import kotlinx.coroutines.launch
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ScannerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScannerBinding
    private val viewModel by viewModels<ScannerViewModel> {
        ViewModelFactory.getInstance(this, "scanner")
    }
    private var currentImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityScannerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (!allPermissionsGranted()) {
            requestPermissionLauncher.launch(Manifest.permission.CAMERA)
        }

        binding.subheaderText.text = getString(R.string.subheader_scanner_text)
        binding.toolbarTitle.text = getString(R.string.header_scanner_text)
        binding.apply {
            buttonScannerGallery.setOnClickListener { startGallery() }
            buttonScannerCamera.setOnClickListener { startCamera() }
            buttonScannerUpload.setOnClickListener { upload() }
            buttonScannerSave.setOnClickListener { save() }
            toolbarBack.setOnClickListener { finish() }
        }

        viewModel.isLoading.observe(this) {
            showLoading(it)
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                Toast.makeText(this, "Permission request granted", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Permission request denied", Toast.LENGTH_LONG).show()
            }
        }

    private fun allPermissionsGranted() =
        ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED

    private val uCropResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val resultUri = UCrop.getOutput(result.data!!)
            if (resultUri != null) {
                currentImageUri = resultUri
                showImage()
            }
        } else if (result.resultCode == UCrop.RESULT_ERROR) {
            val error = UCrop.getError(result.data!!)
            showToast(error?.message ?: getString(R.string.cropping_failed))
        }
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { imageUri: Uri? ->
        if (imageUri != null) {
            val timeStamp = SystemClock.uptimeMillis()
            val destinationUri = Uri.fromFile(File(cacheDir, "image_crop-$timeStamp"))

            val uCropIntent = UCrop.of(imageUri, destinationUri)
                .withAspectRatio(1f, 1f)
                .getIntent(this)

            uCropResultLauncher.launch(uCropIntent)
        }
    }

    private fun startGallery() {
        launcherGallery.launch(
            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
        )
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { isSuccess ->
        if (isSuccess) {
            val timeStamp = SystemClock.uptimeMillis()
            val destinationUri = Uri.fromFile(File(cacheDir, "image_crop-$timeStamp"))

            val uCropIntent = UCrop.of(currentImageUri!!, destinationUri)
                .withAspectRatio(1f, 1f)
                .getIntent(this)

            uCropResultLauncher.launch(uCropIntent)
        } else {
            currentImageUri = null
            showToast(getString(R.string.camera_failed))
        }
    }

    private fun startCamera() {
        currentImageUri = getImageUri(this)
        launcherIntentCamera.launch(currentImageUri!!)
    }

    private fun showImage() {
        currentImageUri?.let {
            binding.imgPreview.setImageURI(it)
        }
    }

    private fun upload() {
        currentImageUri?.let { uri ->
            val imageFile = uriToFile(uri, this).reduceFileImage()

            viewModel.uploadImage(imageFile).observe(this) { result ->
                if (result != null) {
                    val uploadResult = result.result?.label ?: resources.getString(R.string.unknown_error)
                    Log.d("ScannerActivity", "Upload result: $uploadResult")
                    showResult(uploadResult)
                } else {
                    showToast(getString(R.string.unknown_error))
                }
            }

        } ?: showToast(getString(R.string.empty_image_warning))
    }

    private fun save() {
        val currentTimeMillis = System.currentTimeMillis()
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val readableTime = sdf.format(Date(currentTimeMillis))

        viewModel.getSession().observe(this) { userModel ->
            val result = HistoryEntity().apply {
                username = userModel.username
                imageUri = currentImageUri
                result = binding.textResult.text.toString()
                inferenceTime = readableTime
            }

            lifecycleScope.launch {
                viewModel.insertHistory(result)
            }

            showToast(getString(R.string.success_save_history))
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showResult(result: String) {
        binding.apply {
            textResultTitle.visibility = View.VISIBLE
            cardResult.visibility = View.VISIBLE
            buttonScannerSave.visibility = View.VISIBLE
            when (result) {
                getString(R.string.result_positive) -> {
                    textResult.text = getString(R.string.result_positive_text)
                    textResult.setTextColor(ContextCompat.getColor(this@ScannerActivity, R.color.green))
                }
                getString(R.string.result_negative) -> {
                    textResult.text = getString(R.string.result_negative_text)
                    textResult.setTextColor(ContextCompat.getColor(this@ScannerActivity, R.color.red))
                }
                else -> {
                    textResult.text = getString(R.string.result_unknown_text)
                    textResult.setTextColor(ContextCompat.getColor(this@ScannerActivity, R.color.gray))
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.buttonScannerCamera.isEnabled = !isLoading
        binding.buttonScannerGallery.isEnabled = !isLoading
        binding.buttonScannerUpload.isEnabled = !isLoading
        binding.buttonScannerSave.isEnabled = !isLoading
    }
}