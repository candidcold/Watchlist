package com.candidcold.watchlist.detail.actor

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.candidcold.watchlist.R
import com.candidcold.watchlist.WatchApp
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import javax.inject.Inject


class ActorDetailActivity : AppCompatActivity() {

    companion object {
        val EXTRA_ACTOR_ID = "actor_id"

        fun start(from: Context, id: Int) {
            val intent = Intent(from, ActorDetailActivity::class.java)
            intent.putExtra(EXTRA_ACTOR_ID, id)
            from.startActivity(intent)
        }
    }

    @Inject lateinit var factory: ActorDetailViewModel.Factory
    private val viewModel: ActorDetailViewModel by lazy {
        ViewModelProviders.of(this, factory).get(ActorDetailViewModel::class.java)
    }

    private var isOnWatchlist = false
    private val groupAdapter = GroupAdapter<ViewHolder>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)
        (application as WatchApp).appComponent.inject(this)

    }
}