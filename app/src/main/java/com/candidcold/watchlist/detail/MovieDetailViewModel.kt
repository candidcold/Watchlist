package com.candidcold.watchlist.detail

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.candidcold.watchlist.network.NetworkMovie
import io.reactivex.Single
import javax.inject.Inject


class MovieDetailViewModel(private val interactor: MovieDetailInteractor) : ViewModel() {

    fun getMovieDetails(id: Int): Single<NetworkMovie> = interactor.getMovieDetails(id)

    // Will add in stuff for getting the actors and such eventually
    // Should also have a mechanism of starting with some information if they come from known place

    class Factory @Inject constructor(private val interactor: MovieDetailInteractor)
        : ViewModelProvider.NewInstanceFactory() {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return MovieDetailViewModel(interactor) as T
        }
    }
}