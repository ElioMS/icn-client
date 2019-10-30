package com.peruapps.icnclient.features.reservations.presentation.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.peruapps.icnclient.R
import com.peruapps.icnclient.features.notifications.presentation.NotificationFragment
import com.peruapps.icnclient.features.profile.presentation.ProfileFragment
import com.peruapps.icnclient.features.reservations.presentation.viewmodel.AppointmentViewModel
import com.peruapps.icnclient.features.reservations.presentation.views.fragments.AppointmentFragment
import com.peruapps.icnclient.helpers.NavigationHelper
import com.peruapps.icnclient.widgets.CustomBottomBar
import kotlinx.android.synthetic.main.activity_reservation.*
import kotlinx.android.synthetic.main.navigation_actionbar.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ReservationActivity : AppCompatActivity() {

    private val model: AppointmentViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reservation)

        NavigationHelper.changeFragment(supportFragmentManager,
            R.id.main_container,
            AppointmentFragment(),
            "AppointmentFragment",
            false)

        initActivityEvents()
    }

    private fun initActivityEvents() {

        appointmentOption.setOnClickListener {
            NavigationHelper.changeFragment(supportFragmentManager, R.id.main_container,
                AppointmentFragment(), "AppointmentFragment")
        }

        notificationOption.setOnClickListener {
            NavigationHelper.changeFragment(supportFragmentManager, R.id.main_container,
                NotificationFragment(), "NotificationFragment")
        }

        profileOption.setOnClickListener {
            NavigationHelper.changeFragment(supportFragmentManager, R.id.main_container,
                ProfileFragment(), "ProfileFragment")
        }

        navigationBackOption.setOnClickListener {
            onBackPressed()
        }

        CustomBottomBar(btn_service_category, this).toServiceCategories()
    }

    fun hideActionBar(value: Boolean) {
        if (value) action_bar.visibility = View.GONE else action_bar.visibility = View.VISIBLE
    }

    fun hideProfileOption(value: Boolean) {
        if (value) profileOption.visibility = View.GONE else profileOption.visibility = View.VISIBLE
    }

    fun hideNavigationOption(value: Boolean) {
        if (value) navigationBackOption.visibility = View.GONE else navigationBackOption.visibility = View.VISIBLE
    }

    fun changeActionBarTitle(title: String) {
        action_bar_title.text = title
    }

    fun showNavigationIndicator(position: Int) {

        val indicatorList = ArrayList<Int>()
        indicatorList.add(R.id.appointmentIndicator)
        indicatorList.add(R.id.notificationIndicator)

        indicatorList.forEachIndexed { index, i ->
            val imageView: ImageView = findViewById(i)

            if (index == position) {
                imageView.visibility = View.VISIBLE
            } else {
                imageView.visibility = View.INVISIBLE
            }
        }
    }
}
