package com.peruapps.icnclient.helpers

import android.app.Activity
import android.content.Intent
import com.peruapps.icnclient.R
import com.peruapps.icnclient.features.profile.views.ProfileActivity

object NavigationHelper {

    /**
     * @param activity Activity
     * */
    fun redirectTo(activity: Activity, c: Class<*>) {
        val page = Intent(activity, c)
        activity.startActivity(page)
    }

    fun toProfilePage(activity: Activity) {
        val page = Intent(activity, ProfileActivity::class.java)
        activity.startActivity(page)

        activity.overridePendingTransition(R.anim.transition_slide_up, R.anim.transition_slide_down)

    }

}