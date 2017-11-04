package com.candidcold.watchlist.data

import android.arch.persistence.room.*
import com.candidcold.watchlist.network.NetworkListMovie
import com.candidcold.watchlist.network.NetworkMovie

@Database(entities = arrayOf(Movie::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao

    companion object {
        val DESCRIPTOR_WATCHLIST = "watchlist"
        val DESCRIPTOR_POPULAR = "popular"
        val DESCRIPTOR_TOP_RATED = "top_rated"

        fun convertEntity(networkMovie: NetworkListMovie, descriptor: String): Movie {
            return Movie(networkMovie.id,
                    null,
                    networkMovie.overview,
                    networkMovie.backdrop_path,
                    networkMovie.poster_path,
                    networkMovie.release_date,
                    null,
                    null,
                    null,
                    networkMovie.title,
                    descriptor)
        }

        fun convertEntityToWatchlistEntity(networkMovie: NetworkMovie): Movie {
            return Movie(networkMovie.id,
                    networkMovie.imdb_id,
                    networkMovie.overview,
                    networkMovie.backdrop_path,
                    networkMovie.poster_path,
                    networkMovie.release_date,
                    networkMovie.runtime,
                    networkMovie.status,
                    networkMovie.tagline,
                    networkMovie.title,
                    AppDatabase.DESCRIPTOR_WATCHLIST)
        }
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


