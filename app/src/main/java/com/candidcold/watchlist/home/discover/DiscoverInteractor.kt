package com.candidcold.watchlist.home.discover

import com.candidcold.watchlist.data.AppDatabase
import com.candidcold.watchlist.data.Movie
import com.candidcold.watchlist.repositories.MovieRepository
import io.reactivex.Flowable
import javax.inject.Inject

class DiscoverInteractor @Inject constructor(private val repo: MovieRepository) {

    fun getPopularMovies(): Flowable<List<Movie>>
            = repo.getMoviesForDescriptor(AppDatabase.DESCRIPTOR_POPULAR)

    fun getTopRatedMovies(): Flowable<List<Movie>>
            = repo.getMoviesForDescriptor(AppDatabase.DESCRIPTOR_TOP_RATED)

}
