package com.peruapps.icnclient.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.peruapps.icnclient.room.dao.ServiceDetailDao
import com.peruapps.icnclient.room.entity.ServiceDetail

@Database(entities = [ServiceDetail::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun serviceDetailDAO(): ServiceDetailDao

    companion object {
        fun buildDatabase(context: Context) =
            Room.databaseBuilder(context, AppDatabase::class.java, "icn_database")
                .fallbackToDestructiveMigration()
                .build()
    }
}