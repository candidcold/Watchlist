package com.candidcold.watchlist.detail.movie

import com.candidcold.watchlist.BuildConfig
import com.candidcold.watchlist.network.CastResponse
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
            repo.addMovieToWatchlist(repo.convertEntityToWatchlistEntity(movie))

    fun removeMovieFromWatchlist(movie: NetworkMovie): Completable =
            repo.removeMovieFromWatchlist(repo.convertEntityToWatchlistEntity(movie))

    fun onWatchlist(id: Int): Flowable<Boolean> = repo.onWatchlist(id)

    // From Network

    fun getMovieDetails(id: Int): Single<NetworkMovie> = client.getMovie(id, BuildConfig.TmdbApiKey)

    fun getMovieCast(id: Int): Single<CastResponse> = client.getMovieCast(id, BuildConfig.TmdbApiKey)

}