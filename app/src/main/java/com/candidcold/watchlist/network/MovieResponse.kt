package com.candidcold.watchlist.network


data class NetworkMovie(val imdb_id: String?,
                        val id: Int,
                        val genres: List<NetworkGenre>,
                        val overview: String,
                        val backdrop_path: String?,
                        val poster_path: String?,
                        val release_date: String,
                        val runtime: Int?,
                        val status: String,
                        val tagline: String,
                        val title: String)

data class NetworkGenre(val id: Int,
                        val name: String)