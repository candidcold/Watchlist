package com.candidcold.watchlist.detail.tv

import com.candidcold.watchlist.BuildConfig
import com.candidcold.watchlist.network.CastResponse
import com.candidcold.watchlist.network.TmdbClient
import com.candidcold.watchlist.network.TvResponse
import com.candidcold.watchlist.repositories.TvRepository
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject


class TvShowDetailInteractor @Inject constructor(private val client: TmdbClient,
                                                 private val repo: TvRepository) {

    fun addTvShowToWatchlist(movie: TvResponse): Completable =
            repo.addTvShowToWatchlist(repo.convertEntityToWatchlistEntity(movie))

    fun removeTvShowFromWatchlist(movie: TvResponse): Completable =
            repo.removeTvShowFromWatchlist(repo.convertEntityToWatchlistEntity(movie))

    fun onWatchlist(id: Int): Flowable<Boolean> = repo.onWatchlist(id)

    // From Network

    fun getTvShowDetails(id: Int): Single<TvResponse> = client.getTvShow(id, BuildConfig.TmdbApiKey)

    fun getCast(id: Int): Single<CastResponse> = client.getTvShowCast(id, BuildConfig.TmdbApiKey)
}