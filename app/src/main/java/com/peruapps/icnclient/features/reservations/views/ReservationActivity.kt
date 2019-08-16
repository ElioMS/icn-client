package com.peruapps.icnclient.features.reservations.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.peruapps.icnclient.R
import com.peruapps.icnclient.adapter.ReservationAdapter
import com.peruapps.icnclient.databinding.ActivityReservationBinding
import com.peruapps.icnclient.model.Appointment
import com.peruapps.icnclient.features.reservations.viewmodel.ReservationViewModel
import com.peruapps.icnclient.helpers.NavigationHelper
import com.peruapps.icnclient.widgets.CustomBottomBar
import kotlinx.android.synthetic.main.activity_reservation.*
import kotlinx.android.synthetic.main.navigation_bottom_bar.*

class ReservationActivity : AppCompatActivity() {

    lateinit var binding: ActivityReservationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //TODO: IMPLEMENT REMOTE SOURCE DATA
        val arrayList = ArrayList<Appointment>()
        arrayList.add(Appointment("Endovenoso"))
        arrayList.add(Appointment("Intramuscular"))
        arrayList.add(Appointment("Endovenoso"))
        arrayList.add(Appointment("Intramuscular"))

        binding = DataBindingUtil.setContentView(this, R.layout.activity_reservation)
        val viewModel  = ViewModelProviders.of(this).get(ReservationViewModel::class.java)
        // View model
        binding.viewModel = viewModel
        binding.executePendingBindings()
        //Service adapter
        val reservationAdapter = ReservationAdapter(arrayList)
        binding.rvItemAppointment.layoutManager = LinearLayoutManager(this)
        binding.rvItemAppointment.adapter = reservationAdapter

        viewModel.appointments.observe(this,
            Observer<ArrayList<Appointment>> { it?.let { reservationAdapter.replaceData(it) } })

        initActivityEvents()
    }

    private fun initActivityEvents() {
        iv_profile.setOnClickListener { NavigationHelper.toProfilePage(this) }
        CustomBottomBar(btn_service_category, this).toServiceCategories()
    }
}
