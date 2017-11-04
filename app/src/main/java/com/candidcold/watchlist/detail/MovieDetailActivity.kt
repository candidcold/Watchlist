package com.candidcold.watchlist.detail

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.candidcold.watchlist.R
import com.candidcold.watchlist.WatchApp
import com.candidcold.watchlist.network.NetworkMovie
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import com.uber.autodispose.kotlin.autoDisposeWith
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)
        (application as WatchApp).appComponent.inject(this)

        setSupportActionBar(detail_movie_toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)


        val movieId = intent.getIntExtra(EXTRA_MOVIE_ID, 0)
        observe(movieId)
    }

    private fun observe(movieId: Int) {
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
    }

    private fun setupMovieDetails(movie: NetworkMovie) {
        detail_movie_toolbar.title = movie.title
        detail_movie_toolbar.subtitle = movie.release_date
        detail_movie_overview.text = movie.overview
        val backdropBaseUrl = getString(R.string.tmdb_list_backdrop_base_url)
        Glide.with(this)
                .load(backdropBaseUrl + movie.backdrop_path)
                .crossFade()
                .into(detail_movie_backdrop_image)
    }

}