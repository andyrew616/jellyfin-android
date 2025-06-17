package org.jellyfin.mobile.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.serialization.json.Json
import org.jellyfin.mobile.data.dao.OfflinePlaybackStatDao
import org.jellyfin.sdk.api.client.exceptions.ApiClientException
import org.jellyfin.sdk.api.operations.PlayStateApi
import org.jellyfin.sdk.model.api.PlaybackProgressInfo
import org.jellyfin.sdk.model.api.PlaybackStartInfo
import org.jellyfin.sdk.model.api.PlaybackStopInfo
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class OfflineStatSyncWorker(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params), KoinComponent {

    private val dao: OfflinePlaybackStatDao by inject()
    private val playStateApi: PlayStateApi by inject()

    override suspend fun doWork(): Result {
        val stats = dao.getAll()
        for (stat in stats) {
            try {
                when (stat.eventType) {
                    "start" -> {
                        val data = Json.decodeFromString<PlaybackStartInfo>(stat.eventJson)
                        playStateApi.reportPlaybackStart(data)
                    }
                    "progress" -> {
                        val data = Json.decodeFromString<PlaybackProgressInfo>(stat.eventJson)
                        playStateApi.reportPlaybackProgress(data)
                    }
                    "stop" -> {
                        val data = Json.decodeFromString<PlaybackStopInfo>(stat.eventJson)
                        playStateApi.reportPlaybackStopped(data)
                    }
                }
                dao.delete(stat.id)
            } catch (e: ApiClientException) {
                return Result.retry()
            }
        }
        return Result.success()
    }
}
