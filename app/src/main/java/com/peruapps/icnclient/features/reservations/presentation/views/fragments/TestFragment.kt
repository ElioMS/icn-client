package com.peruapps.icnclient.features.reservations.presentation.views.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.peruapps.icnclient.BR
import com.peruapps.icnclient.R
import com.peruapps.icnclient.databinding.FragmentTestBinding
import com.peruapps.icnclient.features.reservations.presentation.viewmodel.AppointmentViewModel
import kotlinx.android.synthetic.main.fragment_test.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class TestFragment : Fragment() {

    val model by sharedViewModel<AppointmentViewModel>()
    private lateinit var binding: FragmentTestBinding

    companion object {
        /**
         * Id to make the request.
         */
        fun newInstance(stateId: Int) = TestFragment().apply {
            arguments = Bundle().apply {
                putInt("stateId", stateId)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_test, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.setVariable(BR.viewModel, model)

        subscribeToLiveData()
    }

    private fun subscribeToLiveData() {
        model.appointments.observe(this, Observer {
            if  (it.size > 0) {
                appointmentText.visibility = View.GONE
            } else {
                appointmentText.visibility = View.VISIBLE
            }
        })
    }
}