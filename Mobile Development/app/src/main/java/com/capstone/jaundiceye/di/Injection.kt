package com.capstone.jaundiceye.di

import android.content.Context
import com.capstone.jaundiceye.data.pref.UserPreference
import com.capstone.jaundiceye.data.pref.dataStore
import com.capstone.jaundiceye.data.remote.retrofit.ApiConfig
import com.capstone.jaundiceye.repositories.ArticlesRepository
import com.capstone.jaundiceye.repositories.HospitalsRepository
import com.capstone.jaundiceye.repositories.UserRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injection {
    fun provideUserRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val user = runBlocking { pref.getSession().first() }
        val apiService = ApiConfig.getApiService(user.token)
        return UserRepository.getInstance(pref, apiService)
    }
    fun provideHospitalsRepository(context: Context): HospitalsRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val user = runBlocking { pref.getSession().first() }
        val apiService = ApiConfig.getApiService(user.token)
        return HospitalsRepository.getInstance(pref, apiService)
    }
    fun provideArticlesRepository(context: Context): ArticlesRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val user = runBlocking { pref.getSession().first() }
        val apiService = ApiConfig.getApiService(user.token)
        return ArticlesRepository.getInstance(pref, apiService)
    }
}