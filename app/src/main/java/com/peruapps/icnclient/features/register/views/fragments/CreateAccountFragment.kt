package com.peruapps.icnclient.features.register.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders

import com.peruapps.icnclient.R
import com.peruapps.icnclient.features.register.viewmodel.RegisterViewModel
import kotlinx.android.synthetic.main.fragment_create_account.*

class CreateAccountFragment : Fragment() {

    private lateinit var model: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        model = activity?.run {
            ViewModelProviders.of(this)[RegisterViewModel::class.java]
        } ?: throw Exception("Invalid Activity")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_account, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initFragmentEvents()
    }

    private fun initFragmentEvents() {
        tb_create_account.setOnClickListener {
            activity?.onBackPressed()
        }

        btnNext.setOnClickListener {
            setViewModelData()

            val fragmentTransaction = fragmentManager?.beginTransaction()
            fragmentTransaction?.setCustomAnimations(
                R.anim.transition_slide_right_in,
                R.anim.transition_slide_left_out,
                android.R.anim.slide_in_left,
                R.anim.transition_slide_right_out)

            fragmentTransaction?.replace(R.id.main_container, PasswordFragment(), "PasswordFragment")
            fragmentTransaction?.addToBackStack("PasswordFragment")
            fragmentTransaction?.commit()
        }
    }

    private fun setViewModelData() {
        model.name.set(et_name.text.toString())
        model.surname.set(et_surname.text.toString())
        model.age.set(et_age.text.toString())
        model.gender.set("M")
        model.email.set(et_email.text.toString())
    }
}
