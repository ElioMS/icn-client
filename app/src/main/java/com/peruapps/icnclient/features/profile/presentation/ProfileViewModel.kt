package com.peruapps.icnclient.features.profile.presentation

import android.util.Log
import androidx.databinding.ObservableField
import com.peruapps.icnclient.db.sharePreferences.PreferencesManager
import com.peruapps.icnclient.model.response.LoginResponse
import com.peruapps.icnclient.ui.base.BaseViewModel
import java.util.*

class ProfileViewModel(private val preferencesManager: PreferencesManager): BaseViewModel<ProfileNavigator>() {

    val profile = ObservableField<LoginResponse>()

    init {
        getUserDataFromPreferences()
    }

    private fun getUserDataFromPreferences() {
        profile.set(preferencesManager.getUserData())
    }

    fun logOut() {
        preferencesManager.removeAuthData()
        getNavigator().redirectAfterLogOut()
    }

    fun hideProfileView() {
        getNavigator().goBack()
    }

    fun showEditProfileView() {
        getNavigator().showEditProfileView()
    }

}