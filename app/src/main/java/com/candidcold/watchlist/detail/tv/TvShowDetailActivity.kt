package com.candidcold.watchlist.detail.tv

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.candidcold.watchlist.R
import com.candidcold.watchlist.UpdatingSection
import com.candidcold.watchlist.WatchApp
import com.candidcold.watchlist.detail.CastItem
import com.candidcold.watchlist.network.NetworkCast
import com.candidcold.watchlist.network.TvResponse
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import com.uber.autodispose.kotlin.autoDisposeWith
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_tv_show_detail.*
import kotlinx.android.synthetic.main.content_tv_show_detail.*
import timber.log.Timber
import javax.inject.Inject


class TvShowDetailActivity : AppCompatActivity() {

    companion object {
        val EXTRA_TV_SHOW_ID = "tv_show_id"

        fun start(from: Context, id: Int) {
            val intent = Intent(from, TvShowDetailActivity::class.java)
            intent.putExtra(EXTRA_TV_SHOW_ID, id)
            from.startActivity(intent)
        }
    }

    @Inject lateinit var factory: TvShowDetailViewModel.Factory
    private val viewModel: TvShowDetailViewModel by lazy {
        ViewModelProviders.of(this, factory).get(TvShowDetailViewModel::class.java)
    }

    private var isOnWatchlist = false

    private val groupAdapter = GroupAdapter<ViewHolder>()
    private val castSection = UpdatingSection("", "")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tv_show_detail)
        (application as WatchApp).appComponent.inject(this)

        setSupportActionBar(tv_show_detail_toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        groupAdapter.add(castSection)
        tv_show_detail_cast_list.adapter = groupAdapter
        tv_show_detail_cast_list.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        tv_show_detail_fab.isEnabled = false

        val tvShowId = intent.getIntExtra(EXTRA_TV_SHOW_ID, 0)
        observe(tvShowId)
    }

    private fun setupClickListeners() {
        // Will eventually be used when the cast is shown
    }

    private fun observe(tvShowId: Int) {
        viewModel.onWatchlist(tvShowId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    setFabIcon(it)
                })

        viewModel.getTvShowDetails(tvShowId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .autoDisposeWith(AndroidLifecycleScopeProvider.from(this))
                .subscribe({
                    setupTvShowDetails(it)
                    Timber.d("Set up ${it.name} details.")
                }, {
                    Timber.e(it, "Failed to set up tvShow details.")
                })

        viewModel.getTvShowCast(tvShowId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    updateCast(it)
                    Timber.d("Displaying tvShow cast.")
                }, {
                    Timber.e(it, "Unable to display cast.")
                })
    }

    private fun updateCast(cast: List<NetworkCast>) {
        castSection.update(cast.map { CastItem(it) })
    }

    // Could possibly combine these two operations/streams into one bigger model of the screen
    private fun setFabIcon(onWatchlist: Boolean) {
        isOnWatchlist = onWatchlist
        Timber.d("Is on the watchlist is updated with value $onWatchlist")
        if (onWatchlist) {
            tv_show_detail_fab.setImageResource(R.drawable.ic_check_black_24dp)
        } else {
            tv_show_detail_fab.setImageResource(R.drawable.ic_add)
        }
    }

    private fun setupTvShowDetails(tvShow: TvResponse) {
        tv_show_detail_toolbar.title = tvShow.name
        tv_show_detail_toolbar.subtitle = "${tvShow.number_of_seasons} seasons"
        tv_show_detail_overview.text = tvShow.overview
        val backdropBaseUrl = getString(R.string.tmdb_list_backdrop_base_url)
        Glide.with(this)
                .load(backdropBaseUrl + tvShow.backdrop_path)
                .crossFade()
                .into(tv_show_detail_backdrop_image)

        tv_show_detail_fab.isEnabled = true
        tv_show_detail_fab.setOnClickListener {
            addOrRemoveFromWatchlist(tvShow)
        }
    }

    private fun addOrRemoveFromWatchlist(tvShow: TvResponse) {
        if (isOnWatchlist) {
            viewModel.removeTvShowFromWatchlist(tvShow)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { Timber.d("Removed ${tvShow.name} from watchlist") }
        } else {
            viewModel.addTvShowToWatchlist(tvShow)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { Timber.d("Added ${tvShow.name} to watchlist") }
        }
    }
}