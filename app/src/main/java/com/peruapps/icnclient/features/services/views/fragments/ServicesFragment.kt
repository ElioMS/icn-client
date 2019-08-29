package com.peruapps.icnclient.features.services.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager

import com.peruapps.icnclient.R
import com.peruapps.icnclient.adapter.ServiceAdapter
import com.peruapps.icnclient.databinding.FragmentServicesBinding
import com.peruapps.icnclient.dialogs.CustomDialogContactUs
import com.peruapps.icnclient.dialogs.CustomDialogOtherService
import com.peruapps.icnclient.features.services.viewmodel.ServiceViewModel
import com.peruapps.icnclient.features.services.views.ServiceListener
import com.peruapps.icnclient.features.services.views.ServicesNavigator
import com.peruapps.icnclient.helpers.NavigationHelper
import com.peruapps.icnclient.model.Service
import com.peruapps.icnclient.model.ServiceType
import kotlinx.android.synthetic.main.fragment_services.*
import kotlinx.android.synthetic.main.services_navigation_toolbar.*

class ServicesFragment : Fragment(), ServiceListener {

    private lateinit var binding: FragmentServicesBinding
    private lateinit var model: ServiceViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        model = activity?.run {
            ViewModelProviders.of(this)[ServiceViewModel::class.java]
        } ?: throw Exception("Invalid Activity")
    }

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
        // Inflate the layout for this fragment
        val myView: View = binding.root

        val arrayList = ArrayList<Service>()

        if  (model.category.get() == 1) {
            arrayList.add(Service(1, "Endovenoso", ""))
            arrayList.add(Service(2, "Intramuscular", ""))
            arrayList.add(Service(3, "Colocación de sondas", ""))
            arrayList.add(Service(4, "Otros", ""))
        } else {
            arrayList.add(Service(5, "Personal de enfermería", ""))
            arrayList.add(Service(6, "Personal técnico de enfermería", ""))
        }

        val adapter = ServiceAdapter(arrayList, this)
        binding.rvItemService.layoutManager = LinearLayoutManager(activity!!.applicationContext)
        binding.rvItemService.adapter = adapter

        return myView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initFragmentEvents()
    }

    fun initFragmentEvents() {
        iv_profile.setOnClickListener { NavigationHelper.toProfilePage(activity!!) }
    }

    override fun onClick(entity: Service) {
        model.adapter.cleanList()

        if (entity.id <= 3) {
            model.checkService(entity)

            NavigationHelper.changeFragment(fragmentManager!!,R.id.main_container,
                ServiceTypeFragment(), "ServiceTypeFragment")

        } else if (entity.id >= 5) {
            model.checkService(entity)

            NavigationHelper.changeFragment(fragmentManager!!,R.id.main_container,
                NursingStaffFragment(), "NursingStaffFragment")

        } else {
            val dialog = CustomDialogOtherService(context!!, R.style.FullScreenDialogStyle)
            dialog.show()
        }

    }

}
