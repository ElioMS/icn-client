package com.peruapps.icnclient.features.services.views.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.peruapps.icnclient.BR

import com.peruapps.icnclient.R
import com.peruapps.icnclient.databinding.FragmentServicesBinding
import com.peruapps.icnclient.dialogs.CustomDialogOtherService
import com.peruapps.icnclient.features.nursing_staff.presentation.NursingStaffFragment
import com.peruapps.icnclient.features.service_type.presentation.ServiceTypeFragment
import com.peruapps.icnclient.features.services.viewmodel.ServiceViewModel
import com.peruapps.icnclient.features.services.views.ServiceActivity
import com.peruapps.icnclient.features.services.views.ServicesNavigator
import com.peruapps.icnclient.helpers.NavigationHelper
import com.peruapps.icnclient.model.Service
import com.peruapps.icnclient.model.ServiceType
import kotlinx.android.synthetic.main.fragment_services.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ServicesFragment : Fragment(), ServicesNavigator {

    private lateinit var binding: FragmentServicesBinding
    val model: ServiceViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Initialize data
        model.doSchedule.set(true)
        model.hasMaterial.set(true)
        model.showTimePicker.set(false)
        model.showSubstances.set(false)

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_services, container, false)
        model.loadServices()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model.setNavigator(this)
        binding.setVariable(BR.viewModel, model)
        setParentData(true)
    }

    override fun onDestroyView() {
        setParentData(false)
        super.onDestroyView()
    }

    override fun showServiceTypeView(serviceTypes: ArrayList<ServiceType>, service: Service) {
        if  (service.id > 4) {
            NavigationHelper.changeFragment(fragmentManager!!, R.id.main_container,
                NursingStaffFragment.newInstance(serviceTypes, service), "NursingStaffFragment")
        } else {
            NavigationHelper.changeFragment(fragmentManager!!, R.id.main_container,
                ServiceTypeFragment.newInstance(serviceTypes, service), "ServiceTypeFragment")
        }
    }

    override fun showOthersDialog() {
        val dialog = CustomDialogOtherService(context!!, R.style.FullScreenDialogStyle)
        dialog.show()
    }

    private fun setParentData(value: Boolean) {
        val myParentActivity = (activity) as ServiceActivity
        myParentActivity.hideActionBar(value)
    }

    override fun goBack() {
        activity!!.onBackPressed()
    }
}
