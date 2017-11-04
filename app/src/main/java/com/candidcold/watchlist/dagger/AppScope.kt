package com.candidcold.watchlist.dagger

import android.app.Application
import android.arch.persistence.room.Room
import android.content.Context
import com.candidcold.watchlist.BuildConfig
import com.candidcold.watchlist.WatchApp
import com.candidcold.watchlist.data.AppDatabase
import com.candidcold.watchlist.data.MovieDao
import com.candidcold.watchlist.home.MainActivity
import com.candidcold.watchlist.home.discover.DiscoverFragment
import com.candidcold.watchlist.home.watchlist.WatchlistFragment
import com.candidcold.watchlist.jobscheduling.DiscoverJobService
import com.candidcold.watchlist.network.TmdbClient
import dagger.Component
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton



@Singleton
@Component(modules = arrayOf(AppModule::class, NetworkModule::class, DataModule::class))
interface AppComponent {

    fun inject(target: DiscoverJobService)
    fun inject(target: MainActivity)
    fun inject(target: DiscoverFragment)
    fun inject(target: WatchlistFragment)

}

@Module
class NetworkModule {

    @Provides @Singleton
    internal fun provideOkHttpCache(application: Application): Cache {
        val cacheSize = 10 * 1024 * 1024 // 10 MiB
        return Cache(application.cacheDir, cacheSize.toLong())
    }

    @Provides @Singleton
    internal fun provideOkHttpClient(cache: Cache): OkHttpClient {
        return OkHttpClient.Builder().cache(cache).build()
    }

    @Provides @Singleton
    internal fun provideTmdbClient(client: OkHttpClient): TmdbClient {
        return Retrofit.Builder()
                .baseUrl(BuildConfig.TmdbBaseUrl)
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build()
                .create(TmdbClient::class.java)
    }
}

@Module
class AppModule(private val app: WatchApp) {

    @Provides
    internal fun provideApp(): Application = app

    @Provides
    internal fun provideAppContext(): Context = app.applicationContext

}

@Module
class DataModule {

    @Provides @Singleton
    internal fun provideDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(context,
                AppDatabase::class.java, "WatchDatabase").build()
    }

    @Provides @Singleton
    internal fun provideMovieDao(appDatabase: AppDatabase): MovieDao {
        return appDatabase.movieDao()
    }
}


