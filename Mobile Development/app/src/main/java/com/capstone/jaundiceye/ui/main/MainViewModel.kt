package com.capstone.jaundiceye.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.capstone.jaundiceye.data.pref.UserModel
import com.capstone.jaundiceye.data.remote.responses.HospitalsResponse
import com.capstone.jaundiceye.repositories.HospitalsRepository
import com.capstone.jaundiceye.util.Event

class MainViewModel(private val repository: HospitalsRepository) : ViewModel() {
    private val _hospitals = MutableLiveData<HospitalsResponse>()
    val hospitals: LiveData<HospitalsResponse> = _hospitals

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<Event<String>>()
    val errorMessage: LiveData<Event<String>> = _errorMessage

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }
}