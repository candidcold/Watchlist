package com.candidcold.watchlist.home.discover

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.candidcold.watchlist.R
import com.candidcold.watchlist.UpdatingSection
import com.candidcold.watchlist.WatchApp
import com.candidcold.watchlist.detail.movie.MovieDetailActivity
import com.candidcold.watchlist.detail.tv.TvShowDetailActivity
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import com.uber.autodispose.kotlin.autoDisposeWith
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_discover.view.*
import timber.log.Timber
import javax.inject.Inject


class DiscoverFragment : Fragment() {

    private val TAG = "DiscoverFragment"

    companion object {
        fun newInstance(): DiscoverFragment {
            return DiscoverFragment()
        }
    }

    @Inject lateinit var factory: DiscoverViewModel.Factory
    private val viewModel: DiscoverViewModel by lazy {
        ViewModelProviders.of(this, factory).get(DiscoverViewModel::class.java)
    }

    private val groupieAdapter = GroupAdapter<ViewHolder>()
    private val popularMoviesSection = UpdatingSection("Popular Movies",
            "You know, the ones you've been meaning to see")
    private val topRatedMoviesSection = UpdatingSection("Top Rated Movies",
            "The ones Ashton keeps talking about")
    private val popularShowsSection = UpdatingSection("Popular Tv Shows",
            "Remember: Popular doesn't always mean good")
    private val topRatedShowsSection = UpdatingSection("Top Rated Tv Shows",
            "Let's be honest, it'll be hard to top The Wire")

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        (activity.application as WatchApp).appComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val itemView = inflater!!.inflate(R.layout.fragment_discover, container, false)
        itemView.discover_movies_list.adapter = groupieAdapter
        val layoutManager = GridLayoutManager(activity, 3)
        layoutManager.spanSizeLookup = groupieAdapter.spanSizeLookup
        itemView.discover_movies_list.layoutManager = layoutManager
        groupieAdapter.add(popularMoviesSection)
        groupieAdapter.add(topRatedMoviesSection)
        groupieAdapter.add(popularShowsSection)
        groupieAdapter.add(topRatedShowsSection)

        return itemView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observe()
        setupClickListener()
    }

    private fun setupClickListener() {
        groupieAdapter.setOnItemClickListener { item, _ ->
            when (item) {
                is DiscoverMovieItem -> MovieDetailActivity.start(activity, item.movie.id)
                is DiscoverTvItem -> TvShowDetailActivity.start(activity, item.tvShow.id)
            }
        }
    }

    private fun observe() {
        viewModel.popularMovies
                .map { list -> list.map { DiscoverMovieItem(it) } }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .autoDisposeWith(AndroidLifecycleScopeProvider.from(this))
                .subscribe({
                    popularMoviesSection.update(it)
                    Timber.tag(TAG).d("Displaying ${it.size} popular movies.")
                }, {
                    Timber.tag(TAG).e(it, "Failed to display popular movies.")
                })

        viewModel.topRatedMovies
                .map { list -> list.map { DiscoverMovieItem(it) } }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .autoDisposeWith(AndroidLifecycleScopeProvider.from(this))
                .subscribe({
                    topRatedMoviesSection.update(it)
                    Timber.tag(TAG).d("Displaying ${it.size} top rated movies.")
                }, {
                    Timber.tag(TAG).e(it, "Failed to display top rated movies.")
                })

        viewModel.popularShows
                .map { list -> list.map { DiscoverTvItem(it) } }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .autoDisposeWith(AndroidLifecycleScopeProvider.from(this))
                .subscribe({
                    popularShowsSection.update(it)
                    Timber.d("Displaying ${it.size} popular shows.")
                }, {
                    Timber.e(it, "Failed to display popular shows.")
                })

        viewModel.topRatedShows
                .map { list -> list.map { DiscoverTvItem(it) } }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .autoDisposeWith(AndroidLifecycleScopeProvider.from(this))
                .subscribe({
                    topRatedShowsSection.update(it)
                    Timber.d("Displaying ${it.size} top rated shows.")
                }, {
                    Timber.e(it, "Failed to display top rated shows.")
                })
    }
}