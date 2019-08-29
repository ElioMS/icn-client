package com.peruapps.icnclient.features.services.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.peruapps.icnclient.BR

import com.peruapps.icnclient.R
import com.peruapps.icnclient.databinding.FragmentServiceTypeBinding
import com.peruapps.icnclient.dialogs.CustomDialogContactUs
import com.peruapps.icnclient.features.services.viewmodel.ServiceViewModel
import com.peruapps.icnclient.features.services.views.ServicesNavigator
import com.peruapps.icnclient.helpers.NavigationHelper
import com.peruapps.icnclient.model.ServiceType
import kotlinx.android.synthetic.main.services_navigation_toolbar.*

class ServiceTypeFragment : Fragment(), ServicesNavigator {

    private lateinit var model: ServiceViewModel
    private lateinit var binding: FragmentServiceTypeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        model = activity?.run {
            ViewModelProviders.of(this)[ServiceViewModel::class.java]
        } ?: throw Exception("Invalid Activity")

        subscribeToLiveData()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        model.setNavigator(this)
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_service_type, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.setVariable(BR.viewModel, model)
        initFragmentEvents()
    }

    private fun subscribeToLiveData() {
        model.listServiceTypes.observe(this, Observer<ArrayList<ServiceType>> {
            model.addServiceTypes(it)
        })
    }

    private fun initFragmentEvents() {
        ib_back.setOnClickListener { activity!!.onBackPressed() }
    }

    /**
     * Show dialog when user give negative answer to has materials
     */
    override fun showContactUsModelDialog() {
        val dialog = CustomDialogContactUs(context!!, R.style.FullScreenDialogStyle)
        dialog.show()
    }


    /**
     * @param viewName
     * Change fragments depends on the current values assigned to viewName
     */
    override fun showNextView(viewName: String) {
        when (viewName) {
            "CALENDAR" -> {
                NavigationHelper.changeFragment(fragmentManager!!,
                    R.id.main_container, CalendarFragment(), "CalendarFragment")
            }
            "SUBSTANCES" -> {
                NavigationHelper.changeFragment(fragmentManager!!,
                    R.id.main_container, SubstancesFragment(), "SubstancesFragment")
            }
            "SCHEDULE" -> {
                NavigationHelper.changeFragment(fragmentManager!!,
                    R.id.main_container, ScheduleDatesFragment(), "ScheduleDatesFragment")
            }
            "SUMMARY" -> {
                NavigationHelper.changeFragment(fragmentManager!!,
                    R.id.main_container, SummaryFragment(), "SummaryFragment")
            }
            "DAY" -> {
                NavigationHelper.changeFragment(fragmentManager!!,
                    R.id.main_container, ScheduleDatesFragment(), "ScheduleDatesFragment")
            }
        }
    }
}
