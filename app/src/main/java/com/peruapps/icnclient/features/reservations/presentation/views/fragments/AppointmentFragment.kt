package com.peruapps.icnclient.features.reservations.presentation.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.peruapps.icnclient.R
import com.peruapps.icnclient.databinding.ActivityReservationBinding
import com.peruapps.icnclient.features.reservations.presentation.viewmodel.AppointmentViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class AppointmentFragment : Fragment() {

    private lateinit var binding: ActivityReservationBinding
    private val model: AppointmentViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_appointment, container, false)
    }
}
