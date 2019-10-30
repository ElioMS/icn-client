package com.peruapps.icnclient.model

data class AppointmentDate(val string_date: String,
                           var hour: String,
                           val date: String,
                           var nurses: Int = 0,
                           var technicians: Int = 0,
                           var turn: Int?,
                           var stringHour: String? = "") {

    fun turnToString() : String {
        return when (turn) {
            0 -> "Día"
            1 -> "Noche"
            2 -> "24 hrs"
            else -> ""
        }
    }
}