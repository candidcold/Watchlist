package com.candidcold.watchlist.network


data class NetworkMovie(val imdb_id: String?,
                        val id: Int,
                        val genres: List<Genre>,
                        val overview: String,
                        val backdrop_path: String?,
                        val poster_path: String?,
                        val release_date: String,
                        val runtime: Int?,
                        val status: String,
                        val tagline: String,
                        val title: String)

data class Genre(val id: Int,
                 val name: String)