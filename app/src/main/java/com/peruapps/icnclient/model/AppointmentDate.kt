package com.peruapps.icnclient.model

data class AppointmentDate(val string_date: String,
                           var hour: String,
                           val date: String,
                           var nurses: Int = 1,
                           var technicians: Int = 1,
                           var turn: Int?,
                           var stringHour: String? = "") {

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