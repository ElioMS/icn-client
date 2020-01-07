package com.peruapps.icnclient.model

data class SubstanceDetail(var substance: Substance? = null,
                           var daysQuantity: Int? = null,
                           var timePeriod: Int? = null,
                           var isoDate: String? = "",
                           var dateToString: String? = "",
                           var hour: String? = null) {

    fun periodToString (): String {
        return when(timePeriod) {
            0 -> "12 hrs"
            1 -> "8 hrs"
            2 -> "6 hrs"
            else -> ""
        }
    }
}