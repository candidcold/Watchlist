package com.candidcold.watchlist.repositories


import com.candidcold.watchlist.data.Movie
import com.candidcold.watchlist.data.MovieDao
import io.reactivex.Completable
import io.reactivex.Flowable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(private val dao: MovieDao) {

    fun getMoviesForDescriptor(descriptor: String): Flowable<List<Movie>> = dao.getMovies(descriptor)

    fun removeMovie(movie: Movie): Completable = Completable.fromAction { dao.delete(movie) }

    fun addMovie(movie: Movie): Completable = Completable.fromAction { dao.insert(movie) }
}