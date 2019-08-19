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
import com.peruapps.icnclient.features.services.viewmodel.ServiceViewModel
import com.peruapps.icnclient.features.services.views.ServiceListener
import com.peruapps.icnclient.model.Service

class ServicesFragment : Fragment(), ServiceListener {

    private lateinit var binding: FragmentServicesBinding
    private lateinit var model: ServiceViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        model = ViewModelProviders.of(this)[ServiceViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_services, container, false)
        // Inflate the layout for this fragment
        val myView: View = binding.root
        val arrayList = ArrayList<Service>()
        arrayList.add(Service(1, "Endovenoso", ""))
        arrayList.add(Service(2, "Intramuscular", ""))

        val adapter = ServiceAdapter(arrayList, this)
        binding.rvItemService.layoutManager = LinearLayoutManager(activity!!.applicationContext)
        binding.rvItemService.adapter = adapter

        return myView
    }

    override fun onClick(id: Int) {
        if (id === 1) {
            changeFragment(R.id.main_container, EndovenosoFragment(), "EndovenosoFragment")
        } else {
            changeFragment(R.id.main_container, IntramuscularFragment(), "IntramuscularFragment")
        }
    }

    private fun changeFragment(layout: Int, newFrag: Fragment, fragName: String) {
        fragmentManager!!.beginTransaction()
            .replace(layout, newFrag)
            .addToBackStack(fragName)
            .commit()
    }
}
