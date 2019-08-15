package com.peruapps.icnclient.helpers

import android.app.Activity
import android.content.Intent

object NavigationHelper {

    /**
     * @param activity Activity
     * */
    fun redirectTo(activity: Activity, c: Class<*>) {
        val page = Intent(activity, c)
        activity.startActivity(page)
    }

//    fun profilePage(activity: Activity) {
//        val page = Intent(activity, ProfileActivity::class.java)
//        activity.startActivity(page)
//    }

}