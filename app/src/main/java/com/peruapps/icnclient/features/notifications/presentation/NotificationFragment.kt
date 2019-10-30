package com.peruapps.icnclient.features.notifications.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.peruapps.icnclient.BR

import com.peruapps.icnclient.R
import com.peruapps.icnclient.databinding.FragmentNotificationsBinding
import com.peruapps.icnclient.features.notifications.presentation.dialog.NotificationDialog
import com.peruapps.icnclient.features.reservations.presentation.views.ReservationActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class NotificationFragment : Fragment(), NotificationNavigator {

    val model: NotificationViewModel by viewModel()
    private lateinit var binding: FragmentNotificationsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_notifications, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.setVariable(BR.viewModel, model)
        model.setNavigator(this)
        setParentData()
    }

    private fun setParentData() {
        val myParentActivity = (activity) as ReservationActivity
        myParentActivity.changeActionBarTitle("Notificaciones")
        myParentActivity.showNavigationIndicator(1)
    }

    override fun showNotificationDialog() {
        val dialog = NotificationDialog(context!!, R.style.FullScreenDialogStyle, this)
        dialog.show()
    }

    override fun confirmNotification(status: Boolean) {
        model.updateNotifications(status)
    }

}
