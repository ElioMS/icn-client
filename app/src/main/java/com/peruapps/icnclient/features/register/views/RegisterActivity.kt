package com.peruapps.icnclient.features.register.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.peruapps.icnclient.R
import com.peruapps.icnclient.databinding.ActivityRegisterBinding

import com.peruapps.icnclient.features.register.viewmodel.RegisterViewModel
import com.peruapps.icnclient.features.register.views.fragments.CreateAccountFragment
import com.peruapps.icnclient.helpers.NavigationHelper
import kotlinx.android.synthetic.main.activity_reservation.*

class RegisterActivity : AppCompatActivity() {

    lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

//        AndroidNetworking.initialize(applicationContext)

        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.setCustomAnimations(
            R.anim.transition_slide_right_in,
            R.anim.transition_slide_left_out,
            android.R.anim.slide_in_left,
            R.anim.transition_slide_right_out)

        fragmentTransaction.replace(R.id.main_container, CreateAccountFragment(), "CreateAccountFragment")
        fragmentTransaction.commit()
    }


}
