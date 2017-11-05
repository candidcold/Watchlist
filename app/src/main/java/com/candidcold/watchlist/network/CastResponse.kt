package com.candidcold.watchlist.network


data class CastResponse(val id: Int,
                        val cast: List<NetworkCast>,
                        val crew: List<NetworkCrew>)

data class NetworkCast(val cast_id: Int,
                       val character: String,
                       val id: Int,
                       val name: String,
                       val order: Int,
                       val profile_path: String?)

data class NetworkCrew(val credit_id: String,
                       val department: String,
                       val id: Int,
                       val job: String,
                       val name: String,
                       val profile_path: String?)