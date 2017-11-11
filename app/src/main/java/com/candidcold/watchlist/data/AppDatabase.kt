package com.candidcold.watchlist.data

import android.arch.persistence.room.*

@Database(entities = arrayOf(Movie::class, TvShow::class, Season::class), version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao
    abstract fun tvShowDao(): TvShowDao

    companion object {
        val DESCRIPTOR_WATCHLIST = "watchlist"
        val DESCRIPTOR_POPULAR = "popular"
        val DESCRIPTOR_TOP_RATED = "top_rated"
    }

}

@Entity(tableName = "Movies")
data class Movie(@PrimaryKey val id: Int,
                 @ColumnInfo(name = "imdb_id") val imdbId: String?,
                 @ColumnInfo(name = "overview") val overview: String,
                 @ColumnInfo(name = "backdrop_path") val backdropPath: String?,
                 @ColumnInfo(name = "poster_path") val posterPath: String?,
                 @ColumnInfo(name = "release_date") val releaseDate: String,
                 @ColumnInfo(name = "runtime") val runtime: Int?,
                 @ColumnInfo(name = "status") val status: String?,
                 @ColumnInfo(name = "tagline") val tagline: String?,
                 @ColumnInfo(name = "title") val title: String,
                 @ColumnInfo(name = "descriptor") val descriptor: String)


@Entity(tableName = "TvShows")
data class TvShow(@PrimaryKey val id: Int,
                  @ColumnInfo(name = "backdrop_path") val backdropPath: String?,
                  @ColumnInfo(name = "first_air_date") val firstAirDate: String,
                  @ColumnInfo(name = "last_air_date") val lastAirDate: String?,
                  @ColumnInfo(name = "name") val name: String,
                  @ColumnInfo(name = "number_of_episodes") val numberOfEpisodes: Int?,
                  @ColumnInfo(name = "number_of_seasons") val numberOfSeasons: Int?,
                  @ColumnInfo(name = "overview") val overview: String,
                  @ColumnInfo(name = "poster_path") val posterPath: String?,
                  @ColumnInfo(name = "descriptor") val descriptor: String)

@Entity(tableName = "Season")
data class Season(@PrimaryKey val id: Int,
                  @ColumnInfo(name = "show_id") val showId: Int,
                  @ColumnInfo(name = "air_date") val airDate: String,
                  @ColumnInfo(name = "episode_count") val episodeCount: Int,
                  @ColumnInfo(name = "poster_path") val posterPath: String?,
                  @ColumnInfo(name = "season_number") val seasonNumber: Int)


// TODO: Save the credits that an actor has, similar to Season, only the ones on the watch list
// TODO: Save the actors list that are in a movie/tv show, only the ones on the watch list, maybe everything idk