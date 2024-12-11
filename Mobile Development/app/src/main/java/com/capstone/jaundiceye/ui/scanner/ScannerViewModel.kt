package com.capstone.jaundiceye.ui.scanner

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.jaundiceye.data.remote.responses.ScannerResponse
import com.capstone.jaundiceye.repositories.ScannerRepository
import com.google.gson.Gson
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.HttpException
import java.io.File

class ScannerViewModel(private val repository: ScannerRepository) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun uploadImage(imageFile: File): LiveData<ScannerResponse> {
        val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
        val multipartBody = MultipartBody.Part.createFormData(
            "image",
            imageFile.name,
            requestImageFile
        )

        _isLoading.value = true
        val liveDataResponse = MutableLiveData<ScannerResponse>()
        viewModelScope.launch {
            try {
                val uploadSuccessResponse = repository.uploadImage(multipartBody)
                liveDataResponse.postValue(uploadSuccessResponse)
            } catch (e: HttpException) {
                val jsonInString = e.response()?.errorBody()?.string()
                val uploadFailResponse = Gson().fromJson(jsonInString, ScannerResponse::class.java)
                liveDataResponse.postValue(uploadFailResponse)
            }
            _isLoading.value = false
        }
        return liveDataResponse
    }
}