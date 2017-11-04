package com.candidcold.watchlist.home

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

class FragmentAdapter(fm: FragmentManager,
                      private val fragments: List<Fragment>,
                      private val names: List<String>) : FragmentStatePagerAdapter(fm) {

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getItem(position: Int): Fragment {
        return this.fragments[position]
    }

    override fun getPageTitle(position: Int): CharSequence {
        return names[position]
    }
}
