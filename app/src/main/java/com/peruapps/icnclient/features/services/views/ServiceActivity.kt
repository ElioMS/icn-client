package com.peruapps.icnclient.features.services.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.peruapps.icnclient.R
import com.peruapps.icnclient.adapter.ServiceAdapter
import com.peruapps.icnclient.databinding.ActivityServiceBinding
import com.peruapps.icnclient.features.profile.views.ProfileActivity
import com.peruapps.icnclient.features.services.viewmodel.ServiceViewModel
import com.peruapps.icnclient.helpers.NavigationHelper
import com.peruapps.icnclient.model.Service
import kotlinx.android.synthetic.main.activity_service.*

class ServiceActivity : AppCompatActivity(), ServiceListener {

    lateinit var binding: ActivityServiceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_service)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_service)
        val viewModel  = ViewModelProviders.of(this).get(ServiceViewModel::class.java)

        val arrayList = ArrayList<Service>()
        arrayList.add(Service(1, "Endovenoso", ""))
        arrayList.add(Service(2, "Intramuscular", ""))

        val adapter = ServiceAdapter(arrayList, this)
        binding.rvItemService.layoutManager = LinearLayoutManager(this)
        binding.rvItemService.adapter = adapter

        initActivityEvents()
    }

    override fun onClick() {
        NavigationHelper.redirectTo(this, ProfileActivity::class.java)
    }

    private fun initActivityEvents() {
        iv_back.setOnClickListener { onBackPressed() }
        iv_profile.setOnClickListener { NavigationHelper.toProfilePage(this) }
    }
}
