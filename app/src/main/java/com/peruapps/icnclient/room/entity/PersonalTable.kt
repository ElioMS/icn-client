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

    @ColumnInfo(name = "string_hour")
    val stringHour: String = "",

    @ColumnInfo(name = "date")
    val date: String = "",

    @ColumnInfo(name = "string_date")
    val stringDate: String = "",

    @ColumnInfo(name = "turn")
    val turn: Int? = null,

    @ColumnInfo(name = "quantity")
    val quantity: Int? = null,

    @ColumnInfo(name = "price")
    val price: Float? = null,

    @ColumnInfo(name = "detail_id")
    val detailId: Int
) {

    fun turnToString() : String {
        return when (turn) {
            0 -> "Mañana"
            1 -> "Tarde"
            2 -> "Noche"
            3 -> "Mañana-Tarde"
            4 -> "Mañana-Tarde-Noche"
            5 -> "Tarde-Noche"
            6 -> "Noche-Mañana"
            else -> ""
        }
    }

}