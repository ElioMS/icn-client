package com.peruapps.icnclient.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.peruapps.icnclient.room.entity.PersonalTable

@Dao
interface PersonalDao {

    @Query("SELECT * from personal WHERE detail_id =:id")
    suspend fun list(id: Int): List<PersonalTable>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(data: PersonalTable)
}