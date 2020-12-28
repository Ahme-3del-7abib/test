package com.android.onetoonechatapp.ui.activities.users

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.android.onetoonechatapp.ui.fragments.UsersFragment

@Suppress("DEPRECATION")
class UserListingPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    companion object {
        private val sFragments = arrayOf(UsersFragment.newInstance(UsersFragment.TYPE_ALL))
        private val sTitles = arrayOf("All Users")
    }

    override fun getCount(): Int {
        return sFragments.size
    }

    override fun getItem(position: Int): Fragment {
        return sFragments[position]
    }

    override fun getPageTitle(position: Int): CharSequence {
        return sTitles[position]
    }
}
