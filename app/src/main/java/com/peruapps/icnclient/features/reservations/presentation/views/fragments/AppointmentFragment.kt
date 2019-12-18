package com.peruapps.icnclient.features.reservations.presentation.views.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import com.google.android.material.tabs.TabLayout
import com.peruapps.icnclient.BR

import com.peruapps.icnclient.R
import com.peruapps.icnclient.databinding.FragmentAppointmentBinding
import com.peruapps.icnclient.features.reservations.presentation.viewmodel.AppointmentViewModel
import com.peruapps.icnclient.features.reservations.presentation.views.AppointmentNavigator
import com.peruapps.icnclient.features.reservations.presentation.views.ReservationActivity
import com.peruapps.icnclient.features.reservations.presentation.views.adapter.TestViewPagerAdapter
import kotlinx.android.synthetic.main.fragment_appointment.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_test.*

class AppointmentFragment : Fragment(), AppointmentNavigator {

    private lateinit var binding: FragmentAppointmentBinding
    private val model: AppointmentViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_appointment, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.setVariable(BR.viewModel, model)
        model.setNavigator(this)

        viewPager.adapter = TestViewPagerAdapter(fragmentManager!!, resources.getStringArray(R.array.tabTitles))
        viewPager.offscreenPageLimit = viewPager!!.adapter!!.count
        tabLayout.setupWithViewPager(viewPager)

        val layout = (tabLayout.getChildAt(0) as LinearLayout).getChildAt(0) as LinearLayout
        val layoutParams = layout.layoutParams as LinearLayout.LayoutParams
        layoutParams.weight = 0f
        layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT // e.g. 0.5f
        layout.layoutParams = layoutParams

        tabLayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab?) {
                Log.d("change", p0?.position.toString())
            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {
                Log.d("change", p0?.position.toString())
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                val view = tab?.customView
                if (view is AppCompatTextView) {
                    val face = ResourcesCompat.getFont(view.context, R.font.muli_bold)
                    view.typeface = face
                }

                model.optionPosition.set(tab!!.position)
                model.loadAppointments(model.categoryId.get()!!, tab.position)
            }
        })

        setParentData()
    }

    private fun setParentData() {
        val myParentActivity = (activity) as ReservationActivity
        myParentActivity.changeActionBarTitle("Mis reservas")
        myParentActivity.showNavigationIndicator(0)
        myParentActivity.hideActionBar(false)
    }

    override fun showNotificationsView() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showProfileView() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
