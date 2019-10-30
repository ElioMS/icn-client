package com.peruapps.icnclient.features.substances.presentation.dialogs

import com.peruapps.icnclient.model.SubstanceDetail

interface SubstanceDialogNavigator {
    fun showTimePicker()
    fun showDatePicker()
    fun sendDetailData(data: SubstanceDetail)
}