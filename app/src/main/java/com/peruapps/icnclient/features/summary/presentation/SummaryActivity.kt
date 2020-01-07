package com.peruapps.icnclient.features.summary.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.google.android.gms.common.api.Status
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.peruapps.icnclient.BR
import com.peruapps.icnclient.R
import com.peruapps.icnclient.databinding.ActivitySummaryBinding
import com.peruapps.icnclient.features.reservations.presentation.views.ReservationActivity
import com.peruapps.icnclient.features.service_category.views.ServiceCategoryActivity
import com.peruapps.icnclient.features.summary.presentation.dialogs.DialogSuccessReservation
import com.peruapps.icnclient.features.summary_detail.presentation.SummaryDetailActivity
import com.peruapps.icnclient.helpers.NavigationHelper
import com.peruapps.icnclient.room.dao.ServiceDetailDao
import com.peruapps.icnclient.room.database.AppDatabase
import kotlinx.android.synthetic.main.services_navigation_toolbar.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*
import androidx.lifecycle.Observer

class SummaryActivity : AppCompatActivity(), OnMapReadyCallback, SummaryNavigator {

    val model: SummaryViewModel by viewModel()
    private lateinit var mMap: GoogleMap

    private lateinit var binding: ActivitySummaryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        model.setNavigator(this)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_summary)
        binding.setVariable(BR.viewModel, model)
        binding.executePendingBindings()

        if (!Places.isInitialized()) {
            Places.initialize(this, getString(R.string.google_maps_key))
        }

        val mapFragment = supportFragmentManager.findFragmentById(R.id.main_branch_map) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)

        val autocompleteFragment =
            supportFragmentManager.findFragmentById(R.id.autocomplete_fragment) as AutocompleteSupportFragment


        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG))
        autocompleteFragment.setCountry("PE")
        autocompleteFragment.setHint("Buscar")

        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                val location = place.latLng
                mMap.clear()
                mMap.addMarker(MarkerOptions().position(location!!))
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 17.0f))
            }

            override fun onError(status: Status) {
                Log.i("MAPS", "An error occurred: $status")
            }
        })

        initEvents()
    }

    private fun initEvents() {
        ib_back.setOnClickListener { onBackPressed() }
        subscribeLiveData()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val location = LatLng(-12.1018127,-77.013974)
        mMap.addMarker(MarkerOptions().position(location))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 17.0f))
    }

    override fun showSuccessReservationDialog() {
        val dialog = DialogSuccessReservation(this, R.style.FullScreenDialogStyle, this)
        dialog.show()
    }

    override fun showAppointmentsView() {
        NavigationHelper.redirectTo(this, ReservationActivity::class.java)
    }

    override fun addNewService() {
        NavigationHelper.redirectTo(this, ServiceCategoryActivity::class.java, true)
    }

    override fun showDetail(id: Int) {
        NavigationHelper.redirecToWithData(this, SummaryDetailActivity::class.java, id)
    }

    private fun subscribeLiveData() {
        model.validationMessage.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })

        model.showError.observe(this, Observer {
            if  (it != "") {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        })
    }
}
