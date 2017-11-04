package com.candidcold.watchlist.jobscheduling

import com.candidcold.watchlist.BuildConfig
import com.candidcold.watchlist.data.AppDatabase
import com.candidcold.watchlist.data.Movie
import com.candidcold.watchlist.data.MovieDao
import com.candidcold.watchlist.network.TmdbClient
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject


class JobInteractor @Inject constructor(private val client: TmdbClient,
                                        private val dao: MovieDao) {

    fun refreshData(): Completable {
        return Single.merge(fetchPopularMovies(), fetchTopRatedMovies())
                .flatMapCompletable { updateDatabase(it) }
    }

    private fun updateDatabase(movies: List<Movie>): Completable {
        return Completable.fromAction {
            dao.clearDatabase(AppDatabase.DESCRIPTOR_WATCHLIST)
            dao.insert(movies)
        }
    }

    private fun fetchPopularMovies(): Single<List<Movie>> =
            client.getPopularMovies(BuildConfig.TmdbApiKey)
                    .map { it.results }
                    .flattenAsObservable { it }
                    .map { AppDatabase.convertEntity(it, AppDatabase.DESCRIPTOR_POPULAR) }
                    .toList()

    private fun fetchTopRatedMovies(): Single<List<Movie>> =
            client.getTopRatedMovies(BuildConfig.TmdbApiKey)
                    .map { it.results }
                    .flattenAsObservable { it }
                    .map { AppDatabase.convertEntity(it, AppDatabase.DESCRIPTOR_TOP_RATED) }
                    .toList()
}