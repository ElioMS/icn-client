package com.peruapps.icnclient.features.nursing_staff.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.peruapps.icnclient.BR

import com.peruapps.icnclient.R
import com.peruapps.icnclient.databinding.FragmentNursingStaffBinding
import com.peruapps.icnclient.features.calendar.presentation.CalendarFragment
import com.peruapps.icnclient.features.service_type.presentation.ServiceTypeFragment
import com.peruapps.icnclient.features.services.viewmodel.ServiceViewModel
import com.peruapps.icnclient.helpers.NavigationHelper
import com.peruapps.icnclient.model.Service
import com.peruapps.icnclient.model.ServiceType
import org.koin.androidx.viewmodel.ext.android.viewModel

class NursingStaffFragment : Fragment(), NursingStaffNavigator {

    val model: NursingStaffViewModel by viewModel()
    private lateinit var binding: FragmentNursingStaffBinding

    var list = ArrayList<ServiceType>()
    lateinit var service: Service

    companion object {
        fun newInstance(data: ArrayList<ServiceType>, service: Service) = NursingStaffFragment().apply {
            this.list = data
            this.service = service
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_nursing_staff, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.setVariable(BR.viewModel, model)
        model.setNavigator(this)
        model.setServiceTypeItems(list, service)
    }


    override fun showCalendarView() {
        NavigationHelper.changeFragment(fragmentManager!!,
            R.id.main_container, CalendarFragment.setData(2, service, model.serviceType.get()!!), "CalendarFragment")
    }

}
