package com.peruapps.icnclient.features.profile.presentation

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.facebook.login.LoginManager
import com.peruapps.icnclient.BR
import com.peruapps.icnclient.R
import com.peruapps.icnclient.databinding.FragmentProfileBinding
import com.peruapps.icnclient.features.account.presentation.views.AccountActivity
import com.peruapps.icnclient.features.edit_profile.presentation.EditProfileFragment
import com.peruapps.icnclient.features.reservations.presentation.views.ReservationActivity
import com.peruapps.icnclient.helpers.NavigationHelper
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.facebook.GraphResponse
import com.facebook.GraphRequest
import retrofit2.http.DELETE
import com.facebook.AccessToken
import com.facebook.HttpMethod


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
        model.getUserDataFromPreferences()
        setParentData(true)
    }

    override fun redirectAfterLogOut() {
//        if (AccessToken.getCurrentAccessToken() == null) {
//            return  // already logged out
//        }

//        Log.d("token_fb", AccessToken.getCurrentAccessToken().toString())
//        GraphRequest(AccessToken.getCurrentAccessToken(), "/me/permissions/", null, HttpMethod.DELETE,
//            GraphRequest.Callback {
//                LoginManager.getInstance().logOut()
//            }).executeAsync()
        if (!isRemoving) {
            val alertDialog = AlertDialog.Builder(context!!)
            alertDialog.setMessage("¿Deseas cerrar sesión?")
                .setPositiveButton("Cerrar sesión") { _, _ ->
                    model.removeUserAuthData()
                    NavigationHelper.redirectTo(activity!!, AccountActivity::class.java, true)
                }
                .setNegativeButton("Cancelar") { dialog, which ->
                    dialog.dismiss()
                }

            alertDialog.create()
            alertDialog.show()
        }
    }

    override fun goBack() {
//        activity!!.onBackPressed()
        NavigationHelper.redirectTo(activity!!, ReservationActivity::class.java)
    }

    override fun showEditProfileView() {
        NavigationHelper.changeFragment(
            fragmentManager!!, R.id.main_container,
            EditProfileFragment(),
            "EditProfileFragment"
        )
    }


    private fun setParentData(value: Boolean) {
        val myParentActivity = (activity) as ReservationActivity
        myParentActivity.hideActionBar(value)
        myParentActivity.showNavigationIndicator(999)
    }
}