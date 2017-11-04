package com.candidcold.watchlist.home.watchlist

import com.candidcold.watchlist.data.AppDatabase
import com.candidcold.watchlist.data.Movie
import com.candidcold.watchlist.repositories.MovieRepository
import io.reactivex.Flowable
import javax.inject.Inject


class WatchlistInteractor @Inject constructor(private val repo: MovieRepository) {

    fun getWatchlistMovies(): Flowable<List<Movie>>
            = repo.getMoviesForDescriptor(AppDatabase.DESCRIPTOR_WATCHLIST)
}