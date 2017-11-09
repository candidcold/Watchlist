package com.candidcold.watchlist.repositories


import com.candidcold.watchlist.data.AppDatabase
import com.candidcold.watchlist.data.Movie
import com.candidcold.watchlist.data.MovieDao
import com.candidcold.watchlist.network.NetworkListMovie
import com.candidcold.watchlist.network.NetworkMovie
import io.reactivex.Completable
import io.reactivex.Flowable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(private val dao: MovieDao) {

    fun getMoviesForDescriptor(descriptor: String): Flowable<List<Movie>> =
            dao.getMovies(descriptor)

    fun removeMovieFromWatchlist(movie: Movie): Completable =
            Completable.fromAction { dao.delete(movie) }

    fun insertMovies(movies: List<Movie>): Completable =
            Completable.fromAction { dao.insert(movies) }

    fun addMovieToWatchlist(movie: Movie): Completable =
            Completable.fromAction { dao.insertOrReplace(movie) }

    fun onWatchlist(id: Int): Flowable<Boolean> =
            dao.getNumberOfMoviesWithId(id, AppDatabase.DESCRIPTOR_WATCHLIST)
                    .map { it > 0 }

    fun clearDatabase(): Completable =
            Completable.fromAction { dao.clearDatabase(AppDatabase.DESCRIPTOR_WATCHLIST) }

    // Can make these private instead, and just have it be used on entry
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