package com.inspirecoding.workmanager_logger_periodic_1.workers

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.inspirecoding.workmanager_logger_periodic_1.LOG_WORKER_TAG
import com.inspirecoding.workmanager_logger_periodic_1.SHARED_PREFERENCES
import com.inspirecoding.workmanager_logger_periodic_1.SharedPreferencesRepository
import timber.log.Timber
import java.util.*

private var logCounter = 0
class LogWorker (val context: Context, params: WorkerParameters) : Worker(context, params)
{
    private var sharedPreferencesRepository: SharedPreferencesRepository = SharedPreferencesRepository()

    override fun doWork(): Result
    {
        try
        {
            sharedPreferencesRepository.initFilterSharedPreferences(
                context.getSharedPreferences(
                    SHARED_PREFERENCES, Context.MODE_PRIVATE
                )
            )

            var info = sharedPreferencesRepository.getInfo()

            if (info != null)
            {
                info += "\n${Calendar.getInstance().time.toLocaleString()}"
                sharedPreferencesRepository.setInfo(info)
            }
            else
            {
                info = Calendar.getInstance().time.toLocaleString()
                sharedPreferencesRepository.setInfo(info)
            }

            Log.d("LogWorker", "Saved info: $info")

            logCounter++

            val outputData = workDataOf(LOG_WORKER_TAG to logCounter.toString())
            return Result.success(outputData)
        }
        catch (exception: Exception)
        {
            return Result.retry()
        }
    }
}