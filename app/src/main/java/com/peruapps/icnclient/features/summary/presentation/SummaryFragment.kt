package com.peruapps.icnclient.features.summary.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.peruapps.icnclient.BR

import com.peruapps.icnclient.R
import com.peruapps.icnclient.databinding.FragmentSummaryBinding
import com.peruapps.icnclient.features.reservations.presentation.views.ReservationActivity
import com.peruapps.icnclient.features.summary.presentation.dialogs.DialogSuccessReservation
import com.peruapps.icnclient.helpers.NavigationHelper
import kotlinx.android.synthetic.main.services_navigation_toolbar.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SummaryFragment : Fragment(), OnMapReadyCallback, SummaryNavigator {

    private lateinit var mMap: GoogleMap
    val model: SummaryViewModel by viewModel()
    private lateinit var binding: FragmentSummaryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_summary, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.setVariable(BR.viewModel, model)
        model.setNavigator(this)

        val mapFragment = childFragmentManager.findFragmentById(R.id.main_branch_map) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)

    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val location = LatLng(-12.1018127,-77.013974)
        mMap.addMarker(MarkerOptions().position(location))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 17.0f))
    }

    override fun showSuccessReservationDialog() {
        val dialog = DialogSuccessReservation(context!!, R.style.FullScreenDialogStyle, this)
        dialog.show()
    }

    override fun showAppointmentsView() {
        NavigationHelper.redirectTo(activity!!, ReservationActivity::class.java)
    }
}
