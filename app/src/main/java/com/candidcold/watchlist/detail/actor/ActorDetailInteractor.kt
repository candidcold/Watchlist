package com.candidcold.watchlist.detail.actor

import com.candidcold.watchlist.BuildConfig
import com.candidcold.watchlist.network.ActorResponse
import com.candidcold.watchlist.network.NetworkActorCastCredit
import com.candidcold.watchlist.network.TmdbClient
import io.reactivex.Single
import javax.inject.Inject


class ActorDetailInteractor @Inject constructor(private val client: TmdbClient) {

    fun fetchActorDetails(id: Int): Single<ActorResponse> =
            client.getActor(id, BuildConfig.TmdbApiKey)

    fun fetchActorCredits(id: Int): Single<List<NetworkActorCastCredit>> =
            client.getActorsRoles(id, BuildConfig.TmdbApiKey)
                    .map { it.cast }
}