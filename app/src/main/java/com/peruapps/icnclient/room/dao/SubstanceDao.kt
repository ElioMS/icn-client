package com.peruapps.icnclient.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.peruapps.icnclient.room.entity.ServiceDetail
import com.peruapps.icnclient.room.entity.SubstanceTable

@Dao
interface SubstanceDao {

    @Query("SELECT * from substances WHERE detail_id =:id")
    suspend fun findByDetail(id: Int): List<SubstanceTable>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(data: SubstanceTable)

}