package com.capstone.jaundiceye.util

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.capstone.jaundiceye.di.Injection
import com.capstone.jaundiceye.repositories.ArticlesRepository
import com.capstone.jaundiceye.repositories.HospitalsRepository
import com.capstone.jaundiceye.repositories.Repository
import com.capstone.jaundiceye.repositories.UserRepository
import com.capstone.jaundiceye.ui.article.ArticleViewModel
import com.capstone.jaundiceye.ui.authentication.AuthenticationViewModel
import com.capstone.jaundiceye.ui.main.MainViewModel
import com.capstone.jaundiceye.ui.profile.ProfileViewModel
import com.capstone.jaundiceye.ui.recommendation.RecommendationViewModel

class ViewModelFactory(private val repository: Repository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(repository as HospitalsRepository) as T
            }
            modelClass.isAssignableFrom(AuthenticationViewModel::class.java) -> {
                AuthenticationViewModel(repository as UserRepository) as T
            }
            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> {
                ProfileViewModel(repository as UserRepository) as T
            }
            modelClass.isAssignableFrom(RecommendationViewModel::class.java) -> {
                RecommendationViewModel(repository as HospitalsRepository) as T
            }
            modelClass.isAssignableFrom(ArticleViewModel::class.java) -> {
                ArticleViewModel(repository as ArticlesRepository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null
        @JvmStatic
        fun getInstance(context: Context, type: String): ViewModelFactory {
            synchronized(ViewModelFactory::class.java) {
                when (type) {
                    "user" -> INSTANCE = ViewModelFactory(Injection.provideUserRepository(context))
                    "hospitals" -> INSTANCE = ViewModelFactory(Injection.provideHospitalsRepository(context))
                    "articles" -> INSTANCE = ViewModelFactory(Injection.provideArticlesRepository(context))
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }
}