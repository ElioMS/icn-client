package com.peruapps.icnclient.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "substances",
    foreignKeys = [
        ForeignKey(
            entity = ServiceDetail::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("detail_id"),
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class SubstanceTable(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,

    @ColumnInfo(name = "substance_id")
    val substanceId: Int,

    @ColumnInfo(name = "substance_name")
    val substanceName: String,

    @ColumnInfo(name = "period")
    val period: Int,

    @ColumnInfo(name = "days")
    val days: Int,

    @ColumnInfo(name = "date")
    val date: String,

    @ColumnInfo(name = "hour")
    val hour: String,

    @ColumnInfo(name = "detail_id")
    val detailId: Int
) {
    fun periodToString (): String {
        return when(period) {
            0 -> "24 hrs"
            1 -> "8 hrs"
            2 -> "6 hrs"
            else -> ""
        }
    }
}