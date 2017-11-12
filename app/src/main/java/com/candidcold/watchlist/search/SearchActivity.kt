package com.candidcold.watchlist.search

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.candidcold.watchlist.R
import com.candidcold.watchlist.UpdatingSection
import com.candidcold.watchlist.WatchApp
import com.candidcold.watchlist.detail.actor.ActorDetailActivity
import com.candidcold.watchlist.detail.movie.MovieDetailActivity
import com.candidcold.watchlist.detail.tv.TvShowDetailActivity
import com.candidcold.watchlist.network.SearchResponse
import com.jakewharton.rxbinding2.support.v7.widget.queryTextChanges
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import com.uber.autodispose.kotlin.autoDisposeWith
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_search.*
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SearchActivity : AppCompatActivity() {

    companion object {

        fun start(from: Context) {
            val intent = Intent(from, SearchActivity::class.java)
            from.startActivity(intent)
        }
    }

    @Inject lateinit var factory: SearchViewModel.Factory
    private val viewModel: SearchViewModel by lazy {
        ViewModelProviders.of(this, factory).get(SearchViewModel::class.java)
    }

    private val groupAdapter = GroupAdapter<ViewHolder>()
    private val searchResultSection = UpdatingSection("", "")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        (application as WatchApp).appComponent.inject(this)

        setSupportActionBar(search_toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        groupAdapter.add(searchResultSection)
        search_results_list.adapter = groupAdapter
        search_results_list.layoutManager = LinearLayoutManager(this)

        observe()
        setupClickListeners()
    }

    private fun setupClickListeners() {
        groupAdapter.setOnItemClickListener { item, _ ->
            val result = (item as SearchResultItem).result
            when(result.media_type) {
                "tv" -> TvShowDetailActivity.start(this, result.id)
                "movie" -> MovieDetailActivity.start(this, result.id)
                "person" -> ActorDetailActivity.start(this, result.id)
            }
        }
    }

    private fun observe() {
        search_view.isIconified = false

        search_view.queryTextChanges()
                .debounce(400, TimeUnit.MILLISECONDS)
                .filter { it.length > 1 }
                .map { it.toString() }
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(Schedulers.io())
                .flatMapSingle { viewModel.search(it) }
                .observeOn(AndroidSchedulers.mainThread())
                .autoDisposeWith(AndroidLifecycleScopeProvider.from(this))
                .subscribe({
                    updateSearchResults(it)
                }, {
                    Timber.e(it, "Error getting search results")
                })
    }

    private fun updateSearchResults(results: SearchResponse) {
        val list = results.results
                .filter { it.poster_path != null }
                .map { SearchResultItem(it) }
        searchResultSection.update(list)
    }


}
