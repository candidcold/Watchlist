package com.candidcold.watchlist.jobscheduling

import android.app.job.JobParameters
import android.app.job.JobService
import com.candidcold.watchlist.WatchApp
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject


class DiscoverJobService : JobService() {
    @Inject lateinit var interactor: JobInteractor
    private lateinit var jobParams: JobParameters
    private val TAG = "DiscoverJobService"

    companion object {
        val JOB_ID = 1
    }

    override fun onStartJob(params: JobParameters): Boolean {
        (application as WatchApp).appComponent.inject(this)
        jobParams = params

        interactor.refreshData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Timber.tag(TAG).d("Successfully updated database.")
                    jobFinished(jobParams, false)
                }, {
                    Timber.tag(TAG).e(it,"Error refreshing database, will retry later.")
                    jobFinished(jobParams, true)
                })

        // Return true because using another thread
        return true
    }

    override fun onStopJob(params: JobParameters?) = true
}