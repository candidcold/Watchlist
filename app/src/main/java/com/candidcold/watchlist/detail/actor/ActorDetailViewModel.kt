package com.candidcold.watchlist.detail.actor

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.candidcold.watchlist.network.ActorResponse
import com.candidcold.watchlist.network.NetworkActorCastCredit
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import javax.inject.Inject


class ActorDetailViewModel(private val interactor: ActorDetailInteractor) : ViewModel() {

    private fun fetchDetails(id: Int): Single<ActorResponse> = interactor.fetchActorDetails(id)

    private fun fetchRoles(id: Int): Single<List<NetworkActorCastCredit>> = interactor.fetchActorCredits(id)

    fun details(id: Int): Single<ActorDetails> {
        return Single.zip(fetchDetails(id),
                fetchRoles(id),
                BiFunction<ActorResponse, List<NetworkActorCastCredit>, ActorDetails>
                { details, roles -> ActorDetails(details, roles) })
    }

    class Factory @Inject constructor(private val interactor: ActorDetailInteractor) : ViewModelProvider.NewInstanceFactory() {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return ActorDetailViewModel(interactor) as T
        }
    }
}

data class ActorDetails(val details: ActorResponse,
                        val credits: List<NetworkActorCastCredit>)
