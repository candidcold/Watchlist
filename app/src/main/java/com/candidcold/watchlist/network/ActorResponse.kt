package com.candidcold.watchlist.network


data class ActorResponse(val id: Int,
                         val imdb_id: String,
                         val gender: Int,
                         val name: String,
                         val biography: String,
                         val birthday: String?,
                         val deathday: String?,
                         val place_of_birth: String?,
                         val profile_path: String?)

