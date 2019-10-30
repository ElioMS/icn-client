package com.peruapps.icnclient.features.reservations.presentation.views.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.peruapps.icnclient.features.reservations.presentation.views.fragments.TestFragment

class TestViewPagerAdapter(fragmentManager: FragmentManager,
                           private val tabTitles: Array<String>) : FragmentStatePagerAdapter(fragmentManager) {
    override fun getItem(position: Int): Fragment {
        return TestFragment.newInstance(position)
    }

    override fun getCount() = tabTitles.size

    override fun getPageTitle(position: Int) = tabTitles[position]
}