package com.candidcold.watchlist.home.watchlist

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import javax.inject.Inject


class WatchlistViewModel(interactor: WatchlistInteractor) : ViewModel() {

    val movies = interactor.getWatchlistMovies()

    @Suppress("UNCHECKED_CAST")
    class Factory @Inject constructor(private val interactor: WatchlistInteractor) : ViewModelProvider.NewInstanceFactory() {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return WatchlistViewModel(interactor) as T
        }
    }
}