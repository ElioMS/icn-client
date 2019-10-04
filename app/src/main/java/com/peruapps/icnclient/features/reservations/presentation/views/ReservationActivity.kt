package com.peruapps.icnclient.features.reservations.presentation.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.peruapps.icnclient.R
import com.peruapps.icnclient.features.reservations.presentation.views.fragments.AppointmentFragment
import com.peruapps.icnclient.helpers.NavigationHelper
import com.peruapps.icnclient.widgets.CustomBottomBar
import kotlinx.android.synthetic.main.navigation_bottom_bar.*

class ReservationActivity : AppCompatActivity() {

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
//        iv_profile.setOnClickListener { NavigationHelper.toProfilePage(this) }
        CustomBottomBar(btn_service_category, this).toServiceCategories()
    }
}
