package com.capstone.jaundiceye.ui.recommendation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.capstone.jaundiceye.data.pref.UserModel
import com.capstone.jaundiceye.data.remote.responses.HospitalsResponseItem
import com.capstone.jaundiceye.repositories.HospitalsRepository
import com.capstone.jaundiceye.util.Event
import kotlinx.coroutines.launch

class RecommendationViewModel(private val repository: HospitalsRepository) : ViewModel() {
    private val _hospitals = MutableLiveData<List<HospitalsResponseItem>>()
    val hospitals: LiveData<List<HospitalsResponseItem>> = _hospitals

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<Event<String>>()
    val errorMessage: LiveData<Event<String>> = _errorMessage

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    fun getHospitals() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = repository.getHospitals()
                _hospitals.postValue(response)
            } catch (e: Exception) {
                if (e.message == "HTTP 403") {
                    _errorMessage.postValue(Event("Sesi berakhir! Silakan login kembali"))
                } else {
                    _errorMessage.postValue(Event("Error: ${e.message ?: "Unknown error"}"))
                }
            } finally {
                _isLoading.value = false
            }
        }
    }
}