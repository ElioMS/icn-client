package com.peruapps.icnclient.features.profile.presentation

interface ProfileNavigator {
    fun redirectAfterLogOut()
    fun showEditProfileView()
    fun goBack()
}