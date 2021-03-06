package com.peruapps.icnclient.features.notifications.presentation

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.peruapps.icnclient.features.notifications.data.NotificationRepository
import com.peruapps.icnclient.features.notifications.presentation.adapter.ItemNotificationAdapter
import com.peruapps.icnclient.model.response.NotificationResponse
import com.peruapps.icnclient.ui.base.BaseViewModel
import java.util.*
import kotlin.collections.ArrayList

class NotificationViewModel(private val repository: NotificationRepository) : BaseViewModel<NotificationNavigator>() {

    val adapter = ItemNotificationAdapter(arrayListOf()) {
        model, position -> onClickNotification(model, position)
    }

    val notifications = MutableLiveData<ArrayList<NotificationResponse>>()

    private var selectedItemId = ObservableField<Long>()

    init {
        loadNotifications()
    }

    private fun onClickNotification(model: NotificationResponse, position: Int) {
        selectedItemId.set(model.id)
        getNavigator().showNotificationDialog()
    }

    fun loadNotifications() {
        startJob {
            val response = repository.listNotifications()

            notifications.value = ArrayList(response)
            adapter.bindItems(ArrayList(response))
        }
    }

    fun updateNotifications(status: Boolean) {
        startJob {
            val response = repository.updateNotification(selectedItemId.get()!!, status)
            Log.d("update_notifications", response.toString())
        }
    }

}