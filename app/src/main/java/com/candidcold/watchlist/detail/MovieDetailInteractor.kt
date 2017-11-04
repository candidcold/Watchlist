package com.candidcold.watchlist.detail

import com.candidcold.watchlist.BuildConfig
import com.candidcold.watchlist.data.AppDatabase
import com.candidcold.watchlist.network.NetworkMovie
import com.candidcold.watchlist.network.TmdbClient
import com.candidcold.watchlist.repositories.MovieRepository
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

class MovieDetailInteractor @Inject constructor(private val client: TmdbClient,
                                                private val repo: MovieRepository) {

    fun addMovieToWatchlist(movie: NetworkMovie): Completable =
            repo.addMovieToWatchlist(AppDatabase.convertEntityToWatchlistEntity(movie))

    fun removeMovieFromWatchlist(movie: NetworkMovie): Completable =
            repo.removeMovieFromWatchlist(AppDatabase.convertEntityToWatchlistEntity(movie))

    fun getMovieDetails(id: Int): Single<NetworkMovie> = client.getMovie(id, BuildConfig.TmdbApiKey)

    fun onWatchlist(id: Int): Flowable<Boolean> = repo.onWatchlist(id)

}