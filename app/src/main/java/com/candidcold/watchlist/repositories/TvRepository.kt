package com.candidcold.watchlist.repositories

import com.candidcold.watchlist.data.AppDatabase
import com.candidcold.watchlist.data.Season
import com.candidcold.watchlist.data.TvShow
import com.candidcold.watchlist.data.TvShowDao
import com.candidcold.watchlist.network.NetworkSeasons
import com.candidcold.watchlist.network.NetworkTvShow
import com.candidcold.watchlist.network.TvResponse
import io.reactivex.Completable
import io.reactivex.Flowable
import javax.inject.Inject


class TvRepository @Inject constructor(private val dao: TvShowDao) {

    fun insertTvShows(tvShows: List<TvShow>): Completable =
            Completable.fromAction { dao.insert(tvShows) }

    fun addTvShowToWatchlist(tvShows: TvShow): Completable =
            Completable.fromAction { dao.insertOrReplace(tvShows) }

    fun removeTvShowFromWatchlist(tvShow: TvShow): Completable =
            Completable.fromAction { dao.delete(tvShow) }

    fun getTvShowsForDescriptor(descriptor: String): Flowable<List<TvShow>> =
            dao.getTvShows(descriptor)

    fun clearDatabase(): Completable =
            Completable.fromAction { dao.clearDatabase(AppDatabase.DESCRIPTOR_WATCHLIST) }

    fun onWatchlist(id: Int): Flowable<Boolean> =
            dao.getNumberOfTvShowsWithId(id, AppDatabase.DESCRIPTOR_WATCHLIST)
                    .map { it > 0 }

    // Can make these private instead, and just have it be used on entry
    fun convertEntity(networkTvShow: NetworkTvShow, descriptor: String): TvShow {
        return TvShow(networkTvShow.id,
                networkTvShow.backdrop_path,
                networkTvShow.first_air_date,
                null,
                networkTvShow.name,
                null,
                null,
                networkTvShow.overview,
                networkTvShow.poster_path,
                descriptor)
    }

    fun convertEntityToWatchlistEntity(tvResponse: TvResponse): TvShow {
        return TvShow(tvResponse.id,
                tvResponse.backdrop_path,
                tvResponse.first_air_date,
                tvResponse.last_air_date,
                tvResponse.name,
                tvResponse.number_of_episodes,
                tvResponse.number_of_seasons,
                tvResponse.overview,
                tvResponse.poster_path,
                AppDatabase.DESCRIPTOR_WATCHLIST)
    }

    fun convertSeasons(seasons: List<NetworkSeasons>, showId: Int): List<Season> {
        return seasons.map {
            Season(it.id,
                    showId,
                    it.air_date,
                    it.episode_count,
                    it.poster_path,
                    it.season_number)
        }
    }
}