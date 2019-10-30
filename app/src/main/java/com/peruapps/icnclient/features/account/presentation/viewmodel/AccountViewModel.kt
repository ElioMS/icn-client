package com.peruapps.icnclient.features.account.presentation.viewmodel

import com.peruapps.icnclient.db.sharePreferences.PreferencesManager
import com.peruapps.icnclient.features.account.presentation.views.AccountNavigator
import com.peruapps.icnclient.ui.base.BaseViewModel

class AccountViewModel (private val preferences: PreferencesManager): BaseViewModel<AccountNavigator>() {

    fun authenticationCheck() {
        if (preferences.isLoggedIn) {
            getNavigator().showLoginView()
        } else {
            getNavigator().showAccountView()
        }
    }

}