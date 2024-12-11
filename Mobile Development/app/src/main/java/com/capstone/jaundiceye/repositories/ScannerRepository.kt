package com.capstone.jaundiceye.repositories

import com.capstone.jaundiceye.data.pref.UserModel
import com.capstone.jaundiceye.data.pref.UserPreference
import com.capstone.jaundiceye.data.remote.responses.ScannerResponse
import com.capstone.jaundiceye.data.remote.retrofit.ApiService
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody

class ScannerRepository private constructor(
    private val userPreference: UserPreference,
    private val apiService: ApiService
): Repository {

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    suspend fun uploadImage(multipartBody: MultipartBody.Part): ScannerResponse {
        return apiService.uploadImage(multipartBody)
    }

    companion object {
        @Volatile
        private var instance: Repository? = null
        fun getInstance(userPref: UserPreference, apiService: ApiService) = ScannerRepository(userPref, apiService)
    }
}