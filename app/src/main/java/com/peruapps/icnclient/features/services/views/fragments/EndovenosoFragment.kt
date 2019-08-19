package com.peruapps.icnclient.features.services.views.fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager

import com.peruapps.icnclient.R
import com.peruapps.icnclient.adapter.ServiceTypeAdapter
import com.peruapps.icnclient.databinding.FragmentEndovenosoBinding
import com.peruapps.icnclient.databinding.FragmentServicesBinding
import com.peruapps.icnclient.helpers.NavigationHelper
import com.peruapps.icnclient.model.ServiceType
import kotlinx.android.synthetic.main.services_navigation_toolbar.*


class EndovenosoFragment : Fragment() {

    private lateinit var binding: FragmentEndovenosoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_endovenoso, container, false)

        val arrayList = ArrayList<ServiceType>()
        arrayList.add(ServiceType(1, "Canalización S/100"))
        arrayList.add(ServiceType(2, "Canalización y administración"))
        arrayList.add(ServiceType(3, "Administración"))

        val adapter = ServiceTypeAdapter(arrayList)
        binding.rvItemServiceType.layoutManager = LinearLayoutManager(activity!!.applicationContext)
        binding.rvItemServiceType.adapter = adapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initFragmentEvents()
    }

    private fun initFragmentEvents() {
        ib_back.setOnClickListener { activity?.onBackPressed() }
        ib_profile.setOnClickListener { NavigationHelper.toProfilePage(activity!!) }
    }
}
