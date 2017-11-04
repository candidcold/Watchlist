package com.candidcold.watchlist.data

import android.arch.persistence.room.*
import io.reactivex.Flowable


interface BaseDao<in T> {

    @Insert
    fun insert(obj: T)

    @Insert
    fun insert(vararg obj: T)

    @Update
    fun update(obj: T)

    @Delete
    fun delete(obj: T)
}

@Dao
abstract class MovieDao : BaseDao<Movie> {

    @Insert
    abstract fun insert(movies: List<Movie>)

    @Query("SELECT * FROM Movies WHERE descriptor = :descriptor")
    abstract fun getMovies(descriptor: String): Flowable<List<Movie>>

    @Query("SELECT * FROM Movies WHERE id = :id")
    abstract fun getMovie(id: Int): Flowable<Movie>

    @Query("DELETE FROM Movies WHERE descriptor != :goodDescriptor")
    abstract fun clearDatabase(goodDescriptor: String)

}