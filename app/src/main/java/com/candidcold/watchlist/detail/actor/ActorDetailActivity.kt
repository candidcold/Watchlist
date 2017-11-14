package com.candidcold.watchlist.detail.actor

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import com.candidcold.watchlist.R
import com.candidcold.watchlist.WatchApp
import com.candidcold.watchlist.detail.movie.MovieDetailActivity
import com.candidcold.watchlist.detail.tv.TvShowDetailActivity
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import com.uber.autodispose.kotlin.autoDisposeWith
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_actor_detail.*
import timber.log.Timber
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
        setContentView(R.layout.activity_actor_detail)
        (application as WatchApp).appComponent.inject(this)

        val id = intent.getIntExtra(EXTRA_ACTOR_ID, 0)
        setupRecyclerview()
        observe(id)
        setupClickListeners()
    }

    private fun setupRecyclerview() {
        actor_detail_list.adapter = groupAdapter
        val layoutManager = GridLayoutManager(this, 3)
        layoutManager.spanSizeLookup = groupAdapter.spanSizeLookup
        actor_detail_list.layoutManager = layoutManager
    }

    private fun setupClickListeners() {
        groupAdapter.setOnItemClickListener { item, view ->
            if (item is RoleItem) {
                val role = item.role
                when (role.media_type) {
                    "movie" -> MovieDetailActivity.start(this, role.id)
                    "tv" -> TvShowDetailActivity.start(this, role.id)
                }
            } else if (item is ActorDetailItem) {
                // TODO: Setup adding to watchlist
            }
        }
    }

    private fun observe(id: Int) {
        viewModel.details(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .autoDisposeWith(AndroidLifecycleScopeProvider.from(this))
                .subscribe({
                    Timber.d("Got actor details")
                    setupActorDetails(it)
                }, {
                    Timber.e(it, "Unable to get actor details")
                })
    }

    private fun setupActorDetails(details: ActorDetails) {
        val topItem = ActorDetailItem(details.details)
        val roles = details.credits
                .filter { it.poster_path != null }
                .map { RoleItem(it.media_type, it) }
        groupAdapter.add(topItem)
        groupAdapter.addAll(roles)
    }
}