package com.peruapps.icnclient.features.register.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.peruapps.icnclient.R
import com.peruapps.icnclient.databinding.ActivityRegisterBinding

import com.peruapps.icnclient.features.register.presentation.fragments.CreateAccountFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterActivity : AppCompatActivity() {

    private val registerViewModel by viewModel<RegisterViewModel>()
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

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
