package com.peruapps.icnclient.features.service_type.presentation

interface ServiceTypeNavigator {
    fun showContactUsModelDialog()
    fun showTimePickerDialog()
    fun showNextView(viewName: String)
}