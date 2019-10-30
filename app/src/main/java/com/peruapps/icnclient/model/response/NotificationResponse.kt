package com.peruapps.icnclient.model.response

import com.google.gson.annotations.SerializedName

data class NotificationResponse(
    val id: Long,
    @SerializedName("category_name") val categoryName: String,
    @SerializedName("service_name") val serviceName: String,
    val address: String,
    @SerializedName("string_date") val stringDate: String,
    @SerializedName("string_hour") val stringHour: String
)