package com.candidcold.watchlist.home.watchlist

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.candidcold.watchlist.R
import com.candidcold.watchlist.UpdatingSection
import com.candidcold.watchlist.WatchApp
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import com.uber.autodispose.kotlin.autoDisposeWith
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_watchlist.view.*
import timber.log.Timber
import javax.inject.Inject


class WatchlistFragment : Fragment() {

    private val TAG = "WatchlistFragment"

    companion object {
        fun newInstance(): WatchlistFragment {
            return WatchlistFragment()
        }
    }

    @Inject lateinit var factory: WatchlistViewModel.Factory
    private val viewModel: WatchlistViewModel by lazy {
        ViewModelProviders.of(this, factory).get(WatchlistViewModel::class.java)
    }

    private val groupieAdapter = GroupAdapter<ViewHolder>()
    private val moviesSection = UpdatingSection("Movies",
            "You've got to get to these eventually")

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        (activity.application as WatchApp).appComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val itemView = inflater!!.inflate(R.layout.fragment_watchlist, container, false)
        itemView.watchlist_movies_list.adapter = groupieAdapter
        itemView.watchlist_movies_list.layoutManager = LinearLayoutManager(activity)
        groupieAdapter.add(moviesSection)

        return itemView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observe()
    }

    private fun observe() {
        viewModel.movies
                .map { list -> list.map { WatchlistMovieItem(it) } }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .autoDisposeWith(AndroidLifecycleScopeProvider.from(this))
                .subscribe({
                    moviesSection.update(it)
                    Timber.tag(TAG).d("Displaying ${it.size} watchlist movies.")
                }, {
                    Timber.tag(TAG).e(it, "Failed to display watchlist movies.")
                })
    }
}