package com.candidcold.watchlist.network


data class TvResponse(val backdrop_path: String?,
                      val episode_run_time: List<Int>,
                      val first_air_date: String,
                      val id: Int,
                      val last_air_date: String,
                      val name: String,
                      val genres: List<NetworkGenre>,
                      val number_of_episodes: Int,
                      val number_of_seasons: Int,
                      val overview: String,
                      val poster_path: String?,
                      val seasons: List<NetworkSeasons>)

data class NetworkSeasons(val air_date: String,
                          val episode_count: Int,
                          val id: Int,
                          val poster_path: String?,
                          val season_number: Int)