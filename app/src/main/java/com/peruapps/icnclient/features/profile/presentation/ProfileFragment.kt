package com.peruapps.icnclient.features.profile.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.peruapps.icnclient.BR
import com.peruapps.icnclient.R
import com.peruapps.icnclient.databinding.FragmentProfileBinding
import com.peruapps.icnclient.features.account.presentation.views.AccountActivity
import com.peruapps.icnclient.features.edit_profile.presentation.EditProfileFragment
import com.peruapps.icnclient.features.reservations.presentation.views.ReservationActivity
import com.peruapps.icnclient.helpers.NavigationHelper
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : Fragment(), ProfileNavigator {

    private lateinit var binding: FragmentProfileBinding
    val model: ProfileViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.setVariable(BR.viewModel, model)
        model.setNavigator(this)

        setParentData(true)
    }

    override fun onDestroyView() {
        setParentData(false)
        super.onDestroyView()
    }

    override fun redirectAfterLogOut() {
        NavigationHelper.redirectTo(activity!!, AccountActivity::class.java, true)
    }

    override fun goBack() {
        activity!!.onBackPressed()
    }

    override fun showEditProfileView() {
        NavigationHelper.changeFragment(fragmentManager!!, R.id.main_container,
            EditProfileFragment(),
            "EditProfileFragment")
    }

    private fun setParentData(value: Boolean) {
        val myParentActivity = (activity) as ReservationActivity
        myParentActivity.hideActionBar(value)
    }
}