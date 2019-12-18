package com.peruapps.icnclient.features.substances.presentation

import com.peruapps.icnclient.model.SubstanceDetail

interface SubstancesNavigator {
//    fun openSubstanceDialog()
    fun showTimePicker()
    fun showDatePicker()
    fun updateDetail(data: SubstanceDetail)
    fun showSummaryView()
}