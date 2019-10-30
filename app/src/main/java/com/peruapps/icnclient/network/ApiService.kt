package com.peruapps.icnclient.network

import com.peruapps.icnclient.model.Appointment
import com.peruapps.icnclient.model.Service
import com.peruapps.icnclient.model.ServiceType
import com.peruapps.icnclient.model.Substance
import com.peruapps.icnclient.model.request.PasswordRequest
import com.peruapps.icnclient.model.request.UpdateProfileRequest
import com.peruapps.icnclient.model.response.LoginResponse
import com.peruapps.icnclient.model.response.NotificationResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*
import java.util.*

interface ApiService {

    @POST("login")
    @FormUrlEncoded
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("type") type: Int
    ): LoginResponse


    @GET("appointments/{categoryId}/{type}")
    suspend fun appointments(
        @Path("categoryId") categoryId: Int,
        @Path("type") type: Int
    ): List<Appointment>

    @GET("service-categories/{categoryId}/services")
    suspend fun services(
        @Path("categoryId") categoryId: Int
    ): List<Service>

    @GET("services/{serviceId}/items")
    suspend fun items(
        @Path("serviceId") serviceId: Int
    ): List<ServiceType>

    @GET("substances")
    suspend fun substances(): List<Substance>

    @GET("notifications")
    suspend fun notifications(): List<NotificationResponse>

    @PUT("notifications/{scheduleId}")
    @FormUrlEncoded
    suspend fun updateNotification(
        @Path("scheduleId") scheduleId: Long,
        @Field("status") status: Boolean
    ): ResponseBody

    @POST("me/profile")
    @Multipart
    suspend fun updateProfile(@Part photo: MultipartBody.Part? = null,
                              @Part("phone_number") phoneNumber: RequestBody,
                              @Part("email") email: RequestBody,
                              @Part("gender") gender: RequestBody,
                              @Part("age") age: RequestBody,
                              @Part("document_type") documentType: RequestBody,
                              @Part("document_number") documentNumber: RequestBody,
                              @Part("address") address: RequestBody,
                              @Part("address_reference") addressReference: RequestBody): Response<Unit>

    @PUT("me/changepassword")
    suspend fun changePassword(@Body data: PasswordRequest): ResponseBody
}