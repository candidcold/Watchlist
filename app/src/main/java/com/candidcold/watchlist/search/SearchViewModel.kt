package com.candidcold.watchlist.search

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.candidcold.watchlist.network.SearchResponse
import io.reactivex.Single
import javax.inject.Inject


class SearchViewModel(private val interactor: SearchInteractor) : ViewModel() {

    fun search(query: String): Single<SearchResponse> =
            interactor.search(query)
//                    .subscribeOn(Schedulers.io())

    class Factory @Inject
    constructor(private val interactor: SearchInteractor) : ViewModelProvider.NewInstanceFactory() {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return SearchViewModel(interactor) as T
        }
    }
}