package com.candidcold.watchlist.jobscheduling

import com.candidcold.watchlist.BuildConfig
import com.candidcold.watchlist.data.AppDatabase
import com.candidcold.watchlist.data.Movie
import com.candidcold.watchlist.data.MovieDao
import com.candidcold.watchlist.network.TmdbClient
import io.reactivex.Completable
import io.reactivex.Observable
import timber.log.Timber
import javax.inject.Inject


class JobInteractor @Inject constructor(private val client: TmdbClient,
                                        private val dao: MovieDao) {

    fun refreshData(): Completable {
        return Observable.merge(fetchPopularMovies(), fetchTopRatedMovies())
                .toList()
                .flatMapCompletable { updateDatabase(it) }
    }

    private fun updateDatabase(movies: List<Movie>): Completable {
        Timber.d("Clearing database")
        return Completable.fromAction {
            dao.clearDatabase(AppDatabase.DESCRIPTOR_WATCHLIST)
            dao.insert(movies)
        }
    }

    private fun fetchPopularMovies(): Observable<Movie> =
            client.getPopularMovies(BuildConfig.TmdbApiKey)
                    .map { it.results }
                    .flattenAsObservable { it }
                    .map { AppDatabase.convertEntity(it, AppDatabase.DESCRIPTOR_POPULAR) }


    private fun fetchTopRatedMovies(): Observable<Movie> =
            client.getTopRatedMovies(BuildConfig.TmdbApiKey)
                    .map { it.results }
                    .flattenAsObservable { it }
                    .map { AppDatabase.convertEntity(it, AppDatabase.DESCRIPTOR_TOP_RATED) }

}