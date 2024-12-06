package com.capstone.jaundiceye.repositories

import com.capstone.jaundiceye.data.pref.UserModel
import com.capstone.jaundiceye.data.pref.UserPreference
import com.capstone.jaundiceye.data.remote.responses.HospitalsResponseItem
import com.capstone.jaundiceye.data.remote.retrofit.ApiService
import kotlinx.coroutines.flow.Flow

class HospitalsRepository private constructor(
    private val userPreference: UserPreference,
    private val apiService: ApiService
): Repository {

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    suspend fun getHospitals(): List<HospitalsResponseItem> {
        return apiService.getHospitals()
    }

    companion object {
        @Volatile
        private var instance: Repository? = null
        fun getInstance(userPref: UserPreference, apiService: ApiService) = HospitalsRepository(userPref, apiService)
    }
}