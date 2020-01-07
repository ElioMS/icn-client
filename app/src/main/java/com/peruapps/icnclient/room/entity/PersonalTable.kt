package com.peruapps.icnclient.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "personal",
    foreignKeys = [
        ForeignKey(
            entity = ServiceDetail::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("detail_id"),
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class PersonalTable(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,

    @ColumnInfo(name = "hour")
    val hour: String = "",

    @ColumnInfo(name = "turn")
    val turn: Int? = null,

    @ColumnInfo(name = "quantity")
    val quantity: Int? = null,

    @ColumnInfo(name = "detail_id")
    val detailId: Int
)