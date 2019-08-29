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
import com.peruapps.icnclient.databinding.FragmentNursingStaffBinding
import com.peruapps.icnclient.features.services.viewmodel.ServiceViewModel
import com.peruapps.icnclient.features.services.views.ServicesNavigator
import com.peruapps.icnclient.helpers.NavigationHelper
import com.peruapps.icnclient.model.ServiceType

class NursingStaffFragment : Fragment(), ServicesNavigator {

    private lateinit var model: ServiceViewModel
    private lateinit var binding: FragmentNursingStaffBinding

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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_nursing_staff, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.setVariable(BR.viewModel, model)
    }

    private fun subscribeToLiveData() {
        model.listServiceTypes.observe(this, Observer<ArrayList<ServiceType>> {
            model.addNurseServiceTypes(it)
        })
    }

    override fun showContactUsModelDialog() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showNextView(viewName: String) {
        when(viewName) {
            "CALENDAR" -> {
                NavigationHelper.changeFragment(fragmentManager!!,
                    R.id.main_container, CalendarFragment(), "CalendarFragment")
            }
            "DAY" -> {
                NavigationHelper.changeFragment(fragmentManager!!,
                    R.id.main_container, ScheduleDatesFragment(), "ScheduleDatesFragment")
            }
        }
    }

}
