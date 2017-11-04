package com.candidcold.watchlist

import android.app.AlarmManager
import android.app.Application
import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import com.candidcold.watchlist.dagger.*
import com.candidcold.watchlist.jobscheduling.DiscoverJobService
import timber.log.Timber


class WatchApp : Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .networkModule(NetworkModule())
                .dataModule(DataModule())
                .build()
    }

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        startJob()

    }

    private fun startJob() {
        val scheduler = getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
        val builder = JobInfo.Builder(DiscoverJobService.JOB_ID,
                ComponentName(packageName, DiscoverJobService::class.java.name))

        builder.setPersisted(true)
        builder.setPeriodic(AlarmManager.INTERVAL_HALF_DAY)
        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)

        scheduler.schedule(builder.build())
        Timber.d("Job started")
    }
}