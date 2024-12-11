package com.capstone.jaundiceye.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.capstone.jaundiceye.data.local.entity.HistoryEntity
import com.capstone.jaundiceye.util.Converter

@Database(entities = [HistoryEntity::class], version = 1)
@TypeConverters(Converter::class)
abstract class HistoryDatabase : RoomDatabase() {
    abstract fun historyDao(): HistoryDao
    companion object {
        @Volatile
        private var INSTANCE: HistoryDatabase? = null
        @JvmStatic
        fun getDatabase(context: Context): HistoryDatabase {
            if (INSTANCE == null) {
                synchronized(HistoryDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        HistoryDatabase::class.java, "history_database")
                        .build()
                }
            }
            return INSTANCE as HistoryDatabase
        }
    }
}