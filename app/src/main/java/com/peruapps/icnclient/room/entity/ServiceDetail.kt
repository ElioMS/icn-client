package com.peruapps.icnclient.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.annotation.NonNull

@Entity(tableName = "service_details")
data class ServiceDetail(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,
    @ColumnInfo(name = "service_id")
    val serviceId: Int,
    @ColumnInfo(name = "service_name")
    val serviceName: String,
    @ColumnInfo(name = "service_type_id")
    val serviceTypeId: Int? = null,
    @ColumnInfo(name = "service_type_name")
    val serviceTypeName: String? = null,
    @ColumnInfo(name = "price")
    val price: Float,
    @ColumnInfo(name = "hour")
    val hour: String? = null
)