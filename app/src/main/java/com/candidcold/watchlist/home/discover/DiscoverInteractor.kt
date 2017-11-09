package com.candidcold.watchlist.home.discover

import com.candidcold.watchlist.data.AppDatabase
import com.candidcold.watchlist.data.Movie
import com.candidcold.watchlist.data.TvShow
import com.candidcold.watchlist.repositories.MovieRepository
import com.candidcold.watchlist.repositories.TvRepository
import io.reactivex.Flowable
import javax.inject.Inject

class DiscoverInteractor @Inject constructor(private val movieRepo: MovieRepository,
                                             private val showRepo: TvRepository) {

    fun getPopularMovies(): Flowable<List<Movie>> =
            movieRepo.getMoviesForDescriptor(AppDatabase.DESCRIPTOR_POPULAR)

    fun getTopRatedMovies(): Flowable<List<Movie>> =
            movieRepo.getMoviesForDescriptor(AppDatabase.DESCRIPTOR_TOP_RATED)

    fun getPopularShows(): Flowable<List<TvShow>> =
            showRepo.getTvShowsForDescriptor(AppDatabase.DESCRIPTOR_POPULAR)

    fun getTopRatedShows(): Flowable<List<TvShow>> =
            showRepo.getTvShowsForDescriptor(AppDatabase.DESCRIPTOR_TOP_RATED)

}
