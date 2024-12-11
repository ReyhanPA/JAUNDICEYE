package com.capstone.jaundiceye.repositories

import android.content.Context
import androidx.lifecycle.LiveData
import com.capstone.jaundiceye.data.local.entity.HistoryEntity
import com.capstone.jaundiceye.data.local.room.HistoryDao
import com.capstone.jaundiceye.data.local.room.HistoryDatabase
import com.capstone.jaundiceye.data.pref.UserPreference
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class HistoryRepository(
    application: Context,
    private val userPreference: UserPreference
): Repository {
    private val mHistoryDao: HistoryDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = HistoryDatabase.getDatabase(application)
        mHistoryDao = db.historyDao()
    }

    fun getAllHistory(): LiveData<List<HistoryEntity>> {
        val username = getCurrentUsername()
        return mHistoryDao.getAllHistory(username)
    }

    fun insertHistory(history: HistoryEntity) {
        executorService.execute {
            mHistoryDao.insertHistory(history)
        }
    }

    private fun getCurrentUsername(): String {
        return runBlocking {
            userPreference.getSession().first().username
        }
    }
}