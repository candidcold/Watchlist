package com.candidcold.watchlist.detail.actor

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.candidcold.watchlist.network.ActorResponse
import com.candidcold.watchlist.network.NetworkActorCastCredit
import io.reactivex.Single
import javax.inject.Inject


class ActorDetailViewModel(private val interactor: ActorDetailInteractor) : ViewModel() {

    fun fetchDetails(id: Int): Single<ActorResponse> = interactor.fetchActorDetails(id)

    fun fetchRoles(id: Int): Single<List<NetworkActorCastCredit>> = interactor.fetchActorCredits(id)

    class Factory @Inject constructor(private val interactor: ActorDetailInteractor) : ViewModelProvider.NewInstanceFactory() {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return ActorDetailViewModel(interactor) as T
        }
    }
}