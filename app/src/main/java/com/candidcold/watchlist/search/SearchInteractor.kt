package com.candidcold.watchlist.search

import com.candidcold.watchlist.BuildConfig
import com.candidcold.watchlist.network.SearchResponse
import com.candidcold.watchlist.network.TmdbClient
import io.reactivex.Single
import javax.inject.Inject


class SearchInteractor @Inject constructor(private val client: TmdbClient) {

    fun search(query: String): Single<SearchResponse> = client.search(query, BuildConfig.TmdbApiKey)
}