package com.capstone.jaundiceye.repositories

import com.capstone.jaundiceye.data.pref.UserModel
import com.capstone.jaundiceye.data.pref.UserPreference
import com.capstone.jaundiceye.data.remote.request.LoginRequest
import com.capstone.jaundiceye.data.remote.request.SignupRequest
import com.capstone.jaundiceye.data.remote.responses.LoginResponse
import com.capstone.jaundiceye.data.remote.responses.SignupResponse
import com.capstone.jaundiceye.data.remote.retrofit.ApiService
import kotlinx.coroutines.flow.Flow

class UserRepository private constructor(
    private val userPreference: UserPreference,
    private val apiService: ApiService
): Repository {

    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    suspend fun signup(username: String, password: String): SignupResponse {
        val signupRequest = SignupRequest(username, password)
        return apiService.signup(signupRequest)
    }

    suspend fun login(username: String, password: String): LoginResponse {
        val loginRequest = LoginRequest(username, password)
        return apiService.login(loginRequest)
    }

    suspend fun logout() {
        userPreference.logout()
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(
            userPreference: UserPreference,
            apiService: ApiService
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(userPreference, apiService)
            }.also { instance = it }
    }
}