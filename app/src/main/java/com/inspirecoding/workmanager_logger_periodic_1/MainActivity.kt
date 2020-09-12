package com.inspirecoding.workmanager_logger_periodic_1

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.inspirecoding.workmanager_logger_periodic_1.workers.LogWorker
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity()
{
    private val workManager = WorkManager.getInstance(this)
    private lateinit var periodicWorkRequest : PeriodicWorkRequest
    private var sharedPreferencesRepository: SharedPreferencesRepository = SharedPreferencesRepository()

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPreferencesRepository.initFilterSharedPreferences(
            getSharedPreferences(
                SHARED_PREFERENCES, Context.MODE_PRIVATE
            )
        )

        sharedPreferencesRepository.getInfo()?.let {
            textView.text = it
        }

        periodicWorkRequest = PeriodicWorkRequestBuilder<LogWorker>(
            15, TimeUnit.MINUTES)
            .addTag(LOG_WORKER_TAG)
            .build()

        btn_start.setOnClickListener {
            workManager.enqueueUniquePeriodicWork("MyUniqueWorkName", ExistingPeriodicWorkPolicy.REPLACE, periodicWorkRequest)
        }
        btn_stop.setOnClickListener {
            WorkManager.getInstance(this).cancelAllWorkByTag(LOG_WORKER_TAG)
        }
    }
}