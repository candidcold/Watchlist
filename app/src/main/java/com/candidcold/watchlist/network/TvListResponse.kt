package com.candidcold.watchlist.network


data class TvListResponse(val page: Int,
                          val results: List<NetworkTvShow>,
                          val total_pages: Int,
                          val total_results: Int)

data class NetworkTvShow(val poster_path: String?,
                         val id: Int,
                         val backdrop_path: String?,
                         val overview: String,
                         val first_air_date: String,
                         val genre_ids: List<Int>,
                         val name: String)