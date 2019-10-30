package com.peruapps.icnclient.features.services.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.peruapps.icnclient.R
import com.peruapps.icnclient.databinding.ActivityServiceBinding
import com.peruapps.icnclient.features.services.viewmodel.ServiceViewModel
import com.peruapps.icnclient.features.services.views.fragments.ServicesFragment
import com.peruapps.icnclient.helpers.NavigationHelper
import kotlinx.android.synthetic.main.activity_service.*
import kotlinx.android.synthetic.main.fragment_services.*
import kotlinx.android.synthetic.main.navigation_actionbar.*
import kotlinx.android.synthetic.main.services_navigation_toolbar.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ServiceActivity : AppCompatActivity() {

    lateinit var binding: ActivityServiceBinding
    val model: ServiceViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service)
//        model.category.set(intent.extras?.get("data").toString().toInt())
        model.category.set(intent.extras?.get("data").toString().toInt())

        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.setCustomAnimations(
            R.anim.transition_slide_right_in,
            R.anim.transition_slide_left_out,
            android.R.anim.slide_in_left,
            R.anim.transition_slide_right_out)

        fragmentTransaction.replace(R.id.main_container, ServicesFragment(), "ServicesFragment")
        fragmentTransaction.commit()

        initActivityEvents()
    }

    fun hideActionBar(value: Boolean) {
        if (value) toolbar.visibility = View.GONE else toolbar.visibility = View.VISIBLE
    }

    private fun initActivityEvents() {
        ib_back.setOnClickListener {
            onBackPressed()
        }

//        ib_profile_actionbar.setOnClickListener {
//            NavigationHelper.toProfilePage(this@ServiceActivity)
//        }
    }
}
