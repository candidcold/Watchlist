package com.candidcold.watchlist.data

import android.arch.persistence.room.*
import io.reactivex.Flowable


interface BaseDao<in T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(obj: T)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg obj: T)

    @Update
    fun update(obj: T)

    @Delete
    fun delete(obj: T)
}

@Dao
abstract class MovieDao : BaseDao<Movie> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(movies: List<Movie>)

    @Query("SELECT * FROM Movies WHERE descriptor = :descriptor")
    abstract fun getMovies(descriptor: String): Flowable<List<Movie>>

    @Query("SELECT * FROM Movies WHERE id = :id")
    abstract fun getMovie(id: Int): Flowable<Movie>

    @Query("DELETE FROM Movies WHERE descriptor != :goodDescriptor")
    abstract fun clearDatabase(goodDescriptor: String)

}