package com.candidcold.watchlist.detail.movie

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.candidcold.watchlist.R
import com.candidcold.watchlist.UpdatingSection
import com.candidcold.watchlist.WatchApp
import com.candidcold.watchlist.detail.CastItem
import com.candidcold.watchlist.detail.actor.ActorDetailActivity
import com.candidcold.watchlist.extensions.loadImage
import com.candidcold.watchlist.network.NetworkCast
import com.candidcold.watchlist.network.NetworkMovie
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import com.uber.autodispose.kotlin.autoDisposeWith
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_movie_detail.*
import kotlinx.android.synthetic.main.content_movie_detail.*
import timber.log.Timber
import javax.inject.Inject


class MovieDetailActivity : AppCompatActivity() {

    companion object {
        val EXTRA_MOVIE_ID = "movie_id"

        fun start(from: Context, id: Int) {
            val intent = Intent(from, MovieDetailActivity::class.java)
            intent.putExtra(EXTRA_MOVIE_ID, id)
            from.startActivity(intent)
        }
    }

    @Inject lateinit var factory: MovieDetailViewModel.Factory
    private val viewModel: MovieDetailViewModel by lazy {
        ViewModelProviders.of(this, factory).get(MovieDetailViewModel::class.java)
    }

    private var isOnWatchlist = false

    private val groupAdapter = GroupAdapter<ViewHolder>()
    private val castSection = UpdatingSection("", "")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)
        (application as WatchApp).appComponent.inject(this)

        setSupportActionBar(detail_movie_toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        groupAdapter.add(castSection)
        detail_movie_cast_list.adapter = groupAdapter
        detail_movie_cast_list.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        detail_fab.isEnabled = false

        val movieId = intent.getIntExtra(EXTRA_MOVIE_ID, 0)
        observe(movieId)
        setupClickListeners()
    }

    private fun setupClickListeners() {
        groupAdapter.setOnItemClickListener { item, _ ->
            val id = (item as CastItem).castMember.id
            ActorDetailActivity.start(this, id)
        }
    }

    private fun observe(movieId: Int) {
        viewModel.onWatchlist(movieId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    setFabIcon(it)
                })

        viewModel.getMovieDetails(movieId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .autoDisposeWith(AndroidLifecycleScopeProvider.from(this))
                .subscribe({
                    setupMovieDetails(it)
                    Timber.d("Set up ${it.title} details.")
                }, {
                    Timber.e(it, "Failed to set up movie details.")
                })

        viewModel.getMovieCast(movieId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    updateCast(it)
                    Timber.d("Displaying movie cast.")
                }, {
                    Timber.e(it, "Unable to display cast.")
                })
    }

    private fun updateCast(cast: List<NetworkCast>) {
        val items = cast.filter { it.profile_path != null }
                .map { CastItem(it) }
        castSection.update(items)
    }

    // Could possibly combine these two operations/streams into one bigger model of the screen
    private fun setFabIcon(onWatchlist: Boolean) {
        isOnWatchlist = onWatchlist
        Timber.d("Is on the watchlist is updated with value $onWatchlist")
        if (onWatchlist) {
            detail_fab.setImageResource(R.drawable.ic_check_black_24dp)
        } else {
            detail_fab.setImageResource(R.drawable.ic_add)
        }
    }

    private fun setupMovieDetails(movie: NetworkMovie) {
        detail_movie_toolbar.title = movie.title
        detail_movie_toolbar.subtitle = movie.release_date
        detail_movie_overview.text = movie.overview
        val path = getString(R.string.tmdb_list_backdrop_base_url) + movie.backdrop_path
        detail_movie_backdrop_image.loadImage(path)

        detail_fab.isEnabled = true
        detail_fab.setOnClickListener {
            addOrRemoveFromWatchlist(movie)
        }
    }

    private fun addOrRemoveFromWatchlist(movie: NetworkMovie) {
        if (isOnWatchlist) {
            viewModel.removeMovieFromWatchlist(movie)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { Timber.d("Removed ${movie.title} from watchlist") }
        } else {
            viewModel.addMovieToWatchlist(movie)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { Timber.d("Added ${movie.title} to watchlist") }
        }
    }

}