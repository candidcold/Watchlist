package com.candidcold.watchlist.detail

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.candidcold.watchlist.network.NetworkCast
import com.candidcold.watchlist.network.NetworkMovie
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject


class MovieDetailViewModel(private val interactor: MovieDetailInteractor) : ViewModel() {

    fun onWatchlist(id: Int): Flowable<Boolean> = interactor.onWatchlist(id)

    fun getMovieDetails(id: Int): Single<NetworkMovie> = interactor.getMovieDetails(id)

    fun addMovieToWatchlist(networkMovie: NetworkMovie) = interactor.addMovieToWatchlist(networkMovie)

    fun removeMovieFromWatchlist(movie: NetworkMovie): Completable = interactor.removeMovieFromWatchlist(movie)

    fun getMovieCast(id: Int): Single<List<NetworkCast>> =
            interactor.getMovieCast(id)
                    .map { it.cast }
                    .flattenAsObservable { it }
                    .filter { !it.profile_path.isNullOrBlank() }
                    .toList()

    // Should also have a mechanism of starting with some information if they come from known place

    class Factory @Inject constructor(private val interactor: MovieDetailInteractor)
        : ViewModelProvider.NewInstanceFactory() {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return MovieDetailViewModel(interactor) as T
        }
    }

}