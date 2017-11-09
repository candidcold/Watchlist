package com.candidcold.watchlist.data

import android.arch.persistence.room.*
import io.reactivex.Flowable


interface BaseDao<in T> {

    @Update
    fun update(obj: T)

    @Delete
    fun delete(obj: T)
}

@Dao
interface MovieDao : BaseDao<Movie> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrReplace(movie: Movie)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(movies: List<Movie>)

    @Query("SELECT * FROM Movies WHERE descriptor = :descriptor")
    fun getMovies(descriptor: String): Flowable<List<Movie>>

    @Query("SELECT * FROM Movies WHERE id = :id")
    fun getMovie(id: Int): Flowable<Movie>

    @Query("DELETE FROM Movies WHERE descriptor != :goodDescriptor")
    fun clearDatabase(goodDescriptor: String)

    @Query("SELECT COUNT(*) FROM Movies WHERE id = :id AND descriptor = :descriptor")
    fun getNumberOfMoviesWithId(id: Int, descriptor: String): Flowable<Int>

}

@Dao
interface TvShowDao : BaseDao<TvShow> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrReplace(tvShow: TvShow)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(tvShow: List<TvShow>)

    @Query("SELECT * FROM TvShows WHERE descriptor = :descriptor")
    fun getTvShows(descriptor: String): Flowable<List<TvShow>>

    @Query("SELECT * FROM TvShows WHERE id = :id")
    fun getTvShow(id: Int): Flowable<TvShow>

    @Query("DELETE FROM TvShows WHERE descriptor != :goodDescriptor")
    fun clearDatabase(goodDescriptor: String)

    @Query("SELECT COUNT(*) FROM TvShows WHERE id = :id AND descriptor = :descriptor")
    fun getNumberOfTvShowsWithId(id: Int, descriptor: String): Flowable<Int>

}