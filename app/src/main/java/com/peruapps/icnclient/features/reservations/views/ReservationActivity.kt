package com.peruapps.icnclient.features.reservations.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.peruapps.icnclient.R
import com.peruapps.icnclient.adapter.ReservationAdapter
import com.peruapps.icnclient.databinding.ActivityReservationBinding
import com.peruapps.icnclient.entity.Appointment

class ReservationActivity : AppCompatActivity() {

    lateinit var binding: ActivityReservationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val arrayList = ArrayList<Appointment>()
        arrayList.add(Appointment("Endovenoso"))
        arrayList.add(Appointment("Intramuscular"))
        arrayList.add(Appointment("Endovenoso"))
        arrayList.add(Appointment("Intramuscular"))

        binding = DataBindingUtil.setContentView(this, R.layout.activity_reservation)
        val serviceAdapter = ReservationAdapter(arrayList)

        binding.rvItemAppointment.layoutManager = LinearLayoutManager(this)
        binding.rvItemAppointment.adapter = serviceAdapter
//        viewModel.services.observe(this,
//            Observer<ArrayList<Service>> { it?.let { serviceAdapter.replaceData(it) } })

    }
}
