package com.peruapps.icnclient.features.services.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.peruapps.icnclient.R
import com.peruapps.icnclient.databinding.ActivityServiceBinding
import com.peruapps.icnclient.features.services.viewmodel.ServiceViewModel
import com.peruapps.icnclient.features.services.views.fragments.ServicesFragment
import com.peruapps.icnclient.helpers.NavigationHelper
import kotlinx.android.synthetic.main.fragment_services.*

class ServiceActivity : AppCompatActivity() {

    lateinit var binding: ActivityServiceBinding
    private lateinit var model: ServiceViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service)

        model = ViewModelProviders.of(this)[ServiceViewModel::class.java]

        model.category.set(intent.extras?.get("data").toString().toInt())

        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.setCustomAnimations(
            R.anim.transition_slide_right_in,
            R.anim.transition_slide_left_out,
            android.R.anim.slide_in_left,
            R.anim.transition_slide_right_out)

        fragmentTransaction.replace(R.id.main_container, ServicesFragment(), "ServicesFragment")
        fragmentTransaction.commit()
    }
}
