package com.peruapps.icnclient.features.notifications.data

import com.peruapps.icnclient.model.response.NotificationResponse
import com.peruapps.icnclient.network.ApiService
import okhttp3.ResponseBody

class NotificationRepositoryImpl (private val apiService: ApiService): NotificationRepository {

    override suspend fun listNotifications(): List<NotificationResponse> {
        return apiService.notifications()
    }

    override suspend fun updateNotification(scheduleId: Long, status: Boolean): ResponseBody {
        return apiService.updateNotification(scheduleId, status)
    }

}