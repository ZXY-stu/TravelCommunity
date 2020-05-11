package com.bignerdranch.travelcommunity.workers

import android.content.Context
import android.util.JsonReader
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.bignerdranch.travelcommunity.util.PLANT_DATA_FILENAME
import kotlinx.coroutines.coroutineScope

class SeedDatabaseWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result = coroutineScope {
        try {
            applicationContext.assets.open(PLANT_DATA_FILENAME).use { inputStream ->
                JsonReader(inputStream.reader()).use { jsonReader ->
                  //  val user = object : TypeToken<List<User>>() {}.type
             //       val userList: List<User> = Gson().fromJson(jsonReader,user)

                 //   val database = TCDataBases.getInstance(applicationContext)
               //     database.userDao().insertAll(userList)

                    Result.success()
                }
            }
        } catch (ex: Exception) {
            Log.e(TAG, "Error seeding database", ex)
            Result.failure()
        }
    }

    companion object {
        private val TAG = SeedDatabaseWorker::class.java.simpleName
    }
}