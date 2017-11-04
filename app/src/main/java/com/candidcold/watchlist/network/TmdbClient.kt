package com.candidcold.watchlist.network

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query


interface TmdbClient {

    // Movies
    @GET("movie/popular")
    fun getPopularMovies(
            @Query("api_key") apiKey: String): Single<MovieListResponse>

    @GET("movie/top_rated")
    fun getTopRatedMovies(
            @Query("api_key") apiKey: String): Single<MovieListResponse>

//    @GET("movie/{id}")
//    fun getMovie(
//            @Path("id") id: Int,
//            @Query("api_key") apiKey: String): Single<MovieResponse>
//
//    @GET("movie/{id}/credits")
//    fun getMovieCast(
//            @Path("id") id: Int,
//            @Query("api_key") apiKey: String): Single<CastResponse>
//
//    // Actors
//    @GET("person/{id}")
//    fun getActor(
//            @Path("id") id: Int,
//            @Query("api_key") apiKey: String): Single<ActorResponse>
//
//    @GET("person/{id}/combined_credits")
//    fun getActorsRoles(
//            @Path("id") id: Int,
//            @Query("api_key") apiKey: String): Single<RoleResponse>
//
//    // Search
//    @GET("search/multi")
//    fun search(
//            @Query("api_key") apiKey: String,
//            @Query("query") userQuery: String): Single<SearchResponse>
}