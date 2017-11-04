package com.candidcold.watchlist.home.discover

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import javax.inject.Inject


class DiscoverViewModel(interactor: DiscoverInteractor) : ViewModel() {

    val popularMovies = interactor.getPopularMovies()
    val topRatedMovies = interactor.getTopRatedMovies()

    @Suppress("UNCHECKED_CAST")
    class Factory @Inject constructor(private val interactor: DiscoverInteractor)
        : ViewModelProvider.NewInstanceFactory() {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return DiscoverViewModel(interactor) as T
        }
    }
}