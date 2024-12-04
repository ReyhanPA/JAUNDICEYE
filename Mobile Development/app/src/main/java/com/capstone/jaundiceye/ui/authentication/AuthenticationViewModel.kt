package com.capstone.jaundiceye.ui.authentication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.jaundiceye.data.pref.UserModel
import com.capstone.jaundiceye.data.remote.responses.LoginResponse
import com.capstone.jaundiceye.data.remote.responses.SignupResponse
import com.capstone.jaundiceye.repositories.UserRepository
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException

class AuthenticationViewModel(private val repository: UserRepository) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun saveSession(user: UserModel) {
        viewModelScope.launch {
            repository.saveSession(user)
        }
    }

    fun getSession() = repository.getSession()

    fun signup(username: String, password: String): LiveData<SignupResponse> {
        _isLoading.value = true
        val responseLiveData = MutableLiveData<SignupResponse>()
        viewModelScope.launch {
            try {
                val response = repository.signup(username, password)
                responseLiveData.postValue(response)
            } catch (e: HttpException) {
                val jsonInString = e.response()?.errorBody()?.string()
                val errorResponse = Gson().fromJson(jsonInString, SignupResponse::class.java)
                responseLiveData.postValue(errorResponse)
            }
            _isLoading.value = false
        }
        return responseLiveData
    }

    fun login(username: String, password: String): LiveData<LoginResponse> {
        _isLoading.value = true
        val responseLiveData = MutableLiveData<LoginResponse>()
        viewModelScope.launch {
            try {
                val response = repository.login(username, password)
                responseLiveData.postValue(response)
            } catch (e: HttpException) {
                val jsonInString = e.response()?.errorBody()?.string()
                val errorResponse = Gson().fromJson(jsonInString, LoginResponse::class.java)
                responseLiveData.postValue(errorResponse)
            }
            _isLoading.value = false
        }
        return responseLiveData
    }
}