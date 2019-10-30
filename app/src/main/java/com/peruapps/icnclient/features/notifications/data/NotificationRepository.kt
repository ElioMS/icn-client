package com.peruapps.icnclient.features.notifications.data

import com.peruapps.icnclient.model.response.NotificationResponse
import okhttp3.ResponseBody

interface NotificationRepository {
    suspend fun listNotifications(): List<NotificationResponse>
    suspend fun updateNotification(scheduleId: Long, status: Boolean) : ResponseBody
}