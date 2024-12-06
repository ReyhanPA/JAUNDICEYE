package com.capstone.jaundiceye.ui.article

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.capstone.jaundiceye.data.pref.UserModel
import com.capstone.jaundiceye.data.remote.responses.ArticlesResponseItem
import com.capstone.jaundiceye.repositories.ArticlesRepository
import com.capstone.jaundiceye.util.Event
import kotlinx.coroutines.launch

class ArticleViewModel(private val repository: ArticlesRepository) : ViewModel() {
    private val _articles = MutableLiveData<List<ArticlesResponseItem>>()
    val articles: LiveData<List<ArticlesResponseItem>> = _articles

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<Event<String>>()
    val errorMessage: LiveData<Event<String>> = _errorMessage

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    fun getArticles() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = repository.getArticles()
                _articles.postValue(response)
            } catch (e: Exception) {
                _errorMessage.postValue(Event("Error: ${e.message ?: "Unknown error"}"))
            } finally {
                _isLoading.value = false
            }
        }
    }
}