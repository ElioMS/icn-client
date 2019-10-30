package com.peruapps.icnclient.db.sharePreferences

import android.text.TextUtils
import com.peruapps.icnclient.model.response.LoginResponse
import com.peruapps.icnclient.resources.Constants

class PreferencesManager(private val mPreferences: PowerPreferences) {
    val TAG = PreferencesManager::class.java.simpleName


    val isLoggedIn: Boolean
        get() = !TextUtils.isEmpty(token)

    val token: String
        get() = mPreferences.getString(Constants.TOKEN_KEY, "")

    fun saveAuthData(response: LoginResponse) {
        mPreferences.saveString(Constants.TOKEN_KEY, response.token)
        mPreferences.saveObject(Constants.USER_DATA, response)
    }

    fun getUserData(): LoginResponse? {
        return mPreferences.getObject(Constants.USER_DATA, LoginResponse::class.java, "")
    }

    fun removeAuthData() {
        mPreferences.removeItemPreference(Constants.TOKEN_KEY)
        mPreferences.removeItemPreference(Constants.USER_DATA)
    }

    fun removeAll() {
        mPreferences.clearAllPreferences()
    }

}