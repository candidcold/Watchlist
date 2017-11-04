package com.candidcold.watchlist.home

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.candidcold.watchlist.R
import com.candidcold.watchlist.home.discover.DiscoverFragment
import com.candidcold.watchlist.home.watchlist.WatchlistFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(main_toolbar)

        initPaging()
    }

    private fun initPaging() {
        val fragments = listOf(DiscoverFragment.newInstance(),
                WatchlistFragment.newInstance())
        val names = listOf("Discover", "Watchlist")
        val adapter = FragmentAdapter(supportFragmentManager, fragments, names)
        main_viewpager.adapter = adapter
        main_tabs.setupWithViewPager(main_viewpager)
    }
}
