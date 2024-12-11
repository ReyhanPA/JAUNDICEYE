package com.capstone.jaundiceye.ui.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.capstone.jaundiceye.data.local.entity.HistoryEntity
import com.capstone.jaundiceye.repositories.HistoryRepository

class HistoryViewModel(private val repository: HistoryRepository) : ViewModel() {

    fun getAllHistory(): LiveData<List<HistoryEntity>> {
        return repository.getAllHistory()
    }
}