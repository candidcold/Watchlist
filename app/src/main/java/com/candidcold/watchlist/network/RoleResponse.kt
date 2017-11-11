package com.candidcold.watchlist.network


data class RoleResponse(val id: Int,
                        val cast: List<NetworkActorCastCredit>,
                        val crew: List<NetworkActorCrewCredit>)

data class NetworkActorCastCredit(val id: Int,
                                  val character: String,
                                  val title: String,
                                  val name: String,
                                  val credit_id: String,
                                  val media_type: String,
                                  val poster_path: String?,
                                  val backdrop_path: String?)

data class NetworkActorCrewCredit(val id: Int,
                                  val department: String,
                                  val job: String,
                                  val title: String,
                                  val name: String,
                                  val credit_id: String,
                                  val media_type: String,
                                  val backdrop_path: String?,
                                  val poster_path: String?)

enum class MediaType(val type: String) {
    ACTOR("person"),
    SHOW("tv"),
    MOVIE("movie")
}