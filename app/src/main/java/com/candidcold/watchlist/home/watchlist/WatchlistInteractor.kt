package com.candidcold.watchlist.home.watchlist

import com.candidcold.watchlist.data.AppDatabase
import com.candidcold.watchlist.data.Movie
import com.candidcold.watchlist.data.TvShow
import com.candidcold.watchlist.repositories.MovieRepository
import com.candidcold.watchlist.repositories.TvRepository
import io.reactivex.Flowable
import javax.inject.Inject


class WatchlistInteractor @Inject constructor(private val movieRepo: MovieRepository,
                                              private val tvShowRepo: TvRepository) {

    fun getWatchlistMovies(): Flowable<List<Movie>> =
            movieRepo.getMoviesForDescriptor(AppDatabase.DESCRIPTOR_WATCHLIST)

    fun getWatchlistTvShows(): Flowable<List<TvShow>> =
            tvShowRepo.getTvShowsForDescriptor(AppDatabase.DESCRIPTOR_WATCHLIST)
}