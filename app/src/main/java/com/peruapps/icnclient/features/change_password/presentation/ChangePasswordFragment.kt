package com.peruapps.icnclient.features.change_password.presentation

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.peruapps.icnclient.BR

import com.peruapps.icnclient.R
import com.peruapps.icnclient.databinding.FragmentChangePasswordBinding
import com.peruapps.icnclient.features.reservations.presentation.views.ReservationActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class ChangePasswordFragment : Fragment(), ChangePasswordNavigator {

    private lateinit var binding: FragmentChangePasswordBinding
    val model: ChangePasswordViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_change_password, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.setVariable(BR.viewModel, model)
        model.setNavigator(this)
        subscribeLiveData()
        setParentData(hideProfile = true, hideNavigation = false)
    }

    override fun onDestroyView() {
        setParentData(hideProfile = false, hideNavigation = true)
        super.onDestroyView()
    }

    private fun setParentData(hideProfile: Boolean, hideNavigation: Boolean) {
        val myParentActivity = (activity) as ReservationActivity
        myParentActivity.changeActionBarTitle("Cambiar contraseña")
        myParentActivity.hideProfileOption(hideProfile)
        myParentActivity.hideNavigationOption(hideNavigation)
    }

    override fun showSuccessToast() {
        Toast.makeText(context!!, "Contraseña actualizada", Toast.LENGTH_SHORT).show()
    }

    private fun subscribeLiveData() {
        model.validationMessage.observe(this, Observer {
            Toast.makeText(context!!, it, Toast.LENGTH_SHORT).show()
            Log.d("validation", it)
        })
    }
}
