package com.candidcold.watchlist.network


data class MovieListResponse(val results: List<NetworkListMovie>,
                             val page: Int,
                             val total_results: Int,
                             val total_pages: Int)

data class NetworkListMovie(val poster_path: String?,
                            val overview: String,
                            val release_date: String,
                            val genre_ids: List<Int>,
                            val id: Int,
                            val title: String,
                            val backdrop_path: String?)