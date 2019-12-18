package com.peruapps.icnclient.helpers

import android.app.Activity
import android.content.Intent
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.peruapps.icnclient.R
//import com.peruapps.icnclient.features.profile.presentation.ProfileActivity

object NavigationHelper {

    /**
     * @param activity Activity
     * */
    fun redirectTo(activity: Activity, c: Class<*>, end: Boolean = false) {
        val page = Intent(activity, c)

        if (end) {
//            page.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            activity.finish()
        }

        activity.startActivity(page)
    }

    fun redirecToWithData(activity: Activity, c: Class<*>, data: Int) {
        val page = Intent(activity, c)
        page.putExtra("data", data)
        activity.startActivity(page)
    }

//    fun toProfilePage(activity: Activity) {
//        val page = Intent(activity, ProfileActivity::class.java)
//        activity.startActivity(page)
//
//        activity.overridePendingTransition(R.anim.transition_slide_up, R.anim.transition_slide_down)
//
//    }

    fun changeFragment(
        fragmentManager: FragmentManager, layout: Int, newFrag: Fragment, fragName: String,
        addToStack: Boolean = true
    ) {

        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.setCustomAnimations(
            R.anim.transition_slide_right_in,
            R.anim.transition_slide_left_out,
            android.R.anim.slide_in_left,
            R.anim.transition_slide_right_out
        )

        if (addToStack) {
            fragmentTransaction.replace(layout, newFrag, fragName)
            fragmentTransaction.addToBackStack(fragName)
            fragmentTransaction.commit()
        } else {
            fragmentTransaction.replace(layout, newFrag, fragName)
            fragmentTransaction.commit()
        }

//        if (addToStack) {
//            fragmentManager.beginTransaction()
//                .setCustomAnimations(
//                    R.anim.transition_slide_right_in,
//                    R.anim.transition_slide_left_out,
//                    android.R.anim.slide_in_left,
//                    R.anim.transition_slide_right_out
//                )
//                .replace(layout, newFrag)
//                .addToBackStack(fragName)
//                .commit()
//        } else {
//            fragmentManager.beginTransaction()
//                .setCustomAnimations(
//                    R.anim.transition_slide_right_in,
//                    R.anim.transition_slide_left_out,
//                    android.R.anim.slide_in_left,
//                    R.anim.transition_slide_right_out
//                )
//                .replace(layout, newFrag)
//                .commit()
//        }
    }
}