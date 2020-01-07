package com.peruapps.icnclient.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.peruapps.icnclient.room.dao.PersonalDao
import com.peruapps.icnclient.room.dao.ServiceDetailDao
import com.peruapps.icnclient.room.dao.SubstanceDao
import com.peruapps.icnclient.room.entity.PersonalTable
import com.peruapps.icnclient.room.entity.ServiceDetail
import com.peruapps.icnclient.room.entity.SubstanceTable

@Database(entities = [ServiceDetail::class, SubstanceTable::class, PersonalTable::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun serviceDetailDAO(): ServiceDetailDao
    abstract fun substanceDao(): SubstanceDao
    abstract fun personalDao(): PersonalDao

    companion object {
        fun buildDatabase(context: Context) =
            Room.databaseBuilder(context, AppDatabase::class.java, "icn_database")
                .fallbackToDestructiveMigration()
                .build()
    }
}