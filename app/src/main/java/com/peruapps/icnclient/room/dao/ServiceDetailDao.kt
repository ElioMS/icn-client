package com.peruapps.icnclient.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.peruapps.icnclient.room.entity.ServiceDetail

@Dao
interface ServiceDetailDao {

    @Query("SELECT * from service_details ORDER BY id ASC")
    suspend fun list(): List<ServiceDetail>

    @Query("SELECT COUNT(*) from service_details")
    suspend fun countItems(): Int

    @Query("SELECT SUM(price) from service_details")
    suspend fun sumPrice(): Float

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(data: ServiceDetail): Long

    @Query("DELETE FROM service_details")
    suspend fun deleteAll()

    @Query("SELECT * from service_details WHERE id =:id limit 1")
    suspend fun findById(id: Int): ServiceDetail

    @Query("DELETE from service_details WHERE id =:id")
    suspend fun deleteById(id: Int)
}