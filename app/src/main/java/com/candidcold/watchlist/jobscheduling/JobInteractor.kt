package com.candidcold.watchlist.jobscheduling

import com.candidcold.watchlist.BuildConfig
import com.candidcold.watchlist.data.AppDatabase
import com.candidcold.watchlist.data.Movie
import com.candidcold.watchlist.data.TvShow
import com.candidcold.watchlist.network.TmdbClient
import com.candidcold.watchlist.repositories.MovieRepository
import com.candidcold.watchlist.repositories.TvRepository
import io.reactivex.Completable
import io.reactivex.Observable
import timber.log.Timber
import javax.inject.Inject


class JobInteractor @Inject constructor(private val client: TmdbClient,
                                        private val movieRepo: MovieRepository,
                                        private val tvRepo: TvRepository) {

    fun refreshData(): Completable {
        val movies = Observable.merge(fetchPopularMovies(), fetchTopRatedMovies())
                .toList()
                .flatMapCompletable { updateMovieTable(it) }

        val tvShows = Observable.merge(fetchPopularTvShows(), fetchTopRatedTvShows())
                .toList()
                .flatMapCompletable { updateTvShowTable(it) }

        return movies
                .andThen(tvShows)
    }

    private fun updateMovieTable(movies: List<Movie>): Completable {
        Timber.d("Clearing movie table")
        return movieRepo.clearDatabase()
                .andThen(movieRepo.insertMovies(movies))
    }

    private fun updateTvShowTable(tvShows: List<TvShow>): Completable {
        Timber.d("Clearing tv show table")
        return tvRepo.clearDatabase()
                .andThen(tvRepo.insertTvShows(tvShows))
    }

    private fun fetchPopularMovies(): Observable<Movie> =
            client.getPopularMovies(BuildConfig.TmdbApiKey)
                    .map { it.results }
                    .flattenAsObservable { it }
                    .filter { it.poster_path != null }
                    .map { movieRepo.convertEntity(it, AppDatabase.DESCRIPTOR_POPULAR) }

    private fun fetchTopRatedMovies(): Observable<Movie> =
            client.getTopRatedMovies(BuildConfig.TmdbApiKey)
                    .map { it.results }
                    .flattenAsObservable { it }
                    .filter { it.poster_path != null }
                    .map { movieRepo.convertEntity(it, AppDatabase.DESCRIPTOR_TOP_RATED) }

    private fun fetchPopularTvShows(): Observable<TvShow> =
            client.getPopularTvShows(BuildConfig.TmdbApiKey)
                    .map { it.results }
                    .flattenAsObservable { it }
                    .filter { it.poster_path != null }
                    .map { tvRepo.convertEntity(it, AppDatabase.DESCRIPTOR_POPULAR) }

    private fun fetchTopRatedTvShows(): Observable<TvShow> =
            client.getTopRatedTvShows(BuildConfig.TmdbApiKey)
                    .map { it.results }
                    .flattenAsObservable { it }
                    .filter { it.poster_path != null }
                    .map { tvRepo.convertEntity(it, AppDatabase.DESCRIPTOR_TOP_RATED) }

}