package com.peruapps.icnclient

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.peruapps.icnclient.adapter.ServiceAdapter
import com.peruapps.icnclient.databinding.ActivityMainBinding
import com.peruapps.icnclient.model.Service

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
//    private val serviceAdapter = ServiceAdapter(arrayListOf(), this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val arrayList = ArrayList<Service>()
        arrayList.add(Service(1, "Endovenoso", ""))
        arrayList.add(Service(2, "Intramuscular", ""))

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val viewModel  = ViewModelProviders.of(this).get(MainViewModel::class.java)
        binding.viewModel = viewModel
        binding.executePendingBindings()

//        val arrayList = viewModel.loadServices()

//        val serviceAdapter = ServiceAdapter(arrayList, this)
//
//        binding.rvItemService.layoutManager = LinearLayoutManager(this)
//        binding.rvItemService.adapter = serviceAdapter
//        viewModel.services.observe(this,
//            Observer<ArrayList<Service>> { it?.let { serviceAdapter.replaceData(it) } })

        //        binding.apply {
//            tvTitle.text = "MVVM"
//        }

//        val auth = Auth("Elio", "123123")

//        binding.repository = auth
//        binding.executePendingBindings()
//        Handler().postDelayed({auth.email="New Name"}, 5000)

    }


}
