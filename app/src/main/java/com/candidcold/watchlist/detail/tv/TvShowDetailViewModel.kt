package com.candidcold.watchlist.detail.tv

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.candidcold.watchlist.network.NetworkCast
import com.candidcold.watchlist.network.TvResponse
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject


class TvShowDetailViewModel(private val interactor: TvShowDetailInteractor) : ViewModel() {

    fun onWatchlist(id: Int): Flowable<Boolean> = interactor.onWatchlist(id)

    fun getTvShowDetails(id: Int): Single<TvResponse> = interactor.getTvShowDetails(id)

    fun addTvShowToWatchlist(tvResponse: TvResponse) = interactor.addTvShowToWatchlist(tvResponse)

    fun removeTvShowFromWatchlist(tvResponse: TvResponse): Completable = interactor.removeTvShowFromWatchlist(tvResponse)

    fun getTvShowCast(id: Int): Single<List<NetworkCast>> =
            interactor.getCast(id)
                    .map { it.cast }
                    .flattenAsObservable { it }
                    .filter { !it.profile_path.isNullOrBlank() }
                    .toList()

    // Should also have a mechanism of starting with some information if they come from known place

    class Factory @Inject constructor(private val interactor: TvShowDetailInteractor)
        : ViewModelProvider.NewInstanceFactory() {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return TvShowDetailViewModel(interactor) as T
        }
    }
}