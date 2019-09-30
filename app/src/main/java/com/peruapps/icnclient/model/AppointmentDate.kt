package com.peruapps.icnclient.model

data class AppointmentDate(val string_date: String, var hour: String, val date: String, var turn: Int?) {

    fun turnToString() : String {
        val data = when (turn) {
            0 -> "Día"
            1 -> "Noche"
            2 -> "24 hrs"
            else -> ""
        }

        return data
    }
}