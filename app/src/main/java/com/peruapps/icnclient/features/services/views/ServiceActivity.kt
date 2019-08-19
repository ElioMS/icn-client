package com.peruapps.icnclient.features.services.views

import android.accounts.Account
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.peruapps.icnclient.R
import com.peruapps.icnclient.adapter.ServiceAdapter
import com.peruapps.icnclient.databinding.ActivityServiceBinding
import com.peruapps.icnclient.features.account.views.AccountActivity
import com.peruapps.icnclient.features.profile.views.ProfileActivity
import com.peruapps.icnclient.features.register.views.fragments.CreateAccountFragment
import com.peruapps.icnclient.features.services.viewmodel.ServiceViewModel
import com.peruapps.icnclient.features.services.views.fragments.EndovenosoFragment
import com.peruapps.icnclient.features.services.views.fragments.IntramuscularFragment
import com.peruapps.icnclient.features.services.views.fragments.ServicesFragment
import com.peruapps.icnclient.helpers.NavigationHelper
import com.peruapps.icnclient.model.Service
import kotlinx.android.synthetic.main.activity_service.*

class ServiceActivity : AppCompatActivity(), ServiceListener {

    lateinit var binding: ActivityServiceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service)

        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.setCustomAnimations(
            R.anim.transition_slide_right_in,
            R.anim.transition_slide_left_out,
            android.R.anim.slide_in_left,
            R.anim.transition_slide_right_out)

        fragmentTransaction.replace(R.id.main_container, ServicesFragment(), "ServicesFragment")
        fragmentTransaction.commit()


//        binding = DataBindingUtil.setContentView(this, R.layout.activity_service)
//        val viewModel  = ViewModelProviders.of(this).get(ServiceViewModel::class.java)
//
//        val arrayList = ArrayList<Service>()
//        arrayList.add(Service(1, "Endovenoso", ""))
//        arrayList.add(Service(2, "Intramuscular", ""))
//
//        val adapter = ServiceAdapter(arrayList, this)
//        binding.rvItemService.layoutManager = LinearLayoutManager(this)
//        binding.rvItemService.adapter = adapter

        initActivityEvents()
    }

    override fun onClick(id: Int) {


    }

    private fun initActivityEvents() {
//        iv_back.setOnClickListener { onBackPressed() }
//        iv_profile.setOnClickListener { NavigationHelper.toProfilePage(this) }
    }


}
