package com.capstone.jaundiceye.repositories

import com.capstone.jaundiceye.data.pref.UserModel
import com.capstone.jaundiceye.data.pref.UserPreference
import com.capstone.jaundiceye.data.remote.retrofit.ApiService
import kotlinx.coroutines.flow.Flow

class HospitalsRepository private constructor(
    private val userPreference: UserPreference,
    private val apiService: ApiService
): Repository {

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    suspend fun logout() {
        userPreference.logout()
    }

    companion object {
        @Volatile
        private var instance: HospitalsRepository? = null
        fun getInstance(
            userPreference: UserPreference,
            apiService: ApiService
        ): HospitalsRepository =
            instance ?: synchronized(this) {
                instance ?: HospitalsRepository(userPreference, apiService)
            }.also { instance = it }
    }
}