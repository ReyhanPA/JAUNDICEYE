package com.capstone.jaundiceye.data.remote.retrofit

import com.capstone.jaundiceye.data.remote.request.LoginRequest
import com.capstone.jaundiceye.data.remote.request.SignupRequest
import com.capstone.jaundiceye.data.remote.responses.HospitalsResponseItem
import com.capstone.jaundiceye.data.remote.responses.LoginResponse
import com.capstone.jaundiceye.data.remote.responses.SignupResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @POST("users/signup")
    suspend fun signup (
        @Body signupRequest: SignupRequest
    ): SignupResponse

    @POST("users/login")
    suspend fun login (
        @Body loginRequest: LoginRequest
    ): LoginResponse

    @GET("hospitals")
    suspend fun getHospitals(): List<HospitalsResponseItem>
}