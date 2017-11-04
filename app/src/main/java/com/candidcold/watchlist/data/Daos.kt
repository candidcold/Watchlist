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