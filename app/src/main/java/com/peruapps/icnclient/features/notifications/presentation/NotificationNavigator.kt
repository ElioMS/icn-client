package com.peruapps.icnclient.features.notifications.presentation

interface NotificationNavigator {
    fun showNotificationDialog()
    fun confirmNotification(status: Boolean)
}