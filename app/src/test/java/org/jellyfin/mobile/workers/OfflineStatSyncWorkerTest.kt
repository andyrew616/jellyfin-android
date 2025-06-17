package org.jellyfin.mobile.workers

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.work.WorkerParameters
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

class OfflineStatSyncWorkerTest {
    private val context: Context = ApplicationProvider.getApplicationContext()
    private val params = WorkerParameters(1, java.util.UUID.randomUUID(), androidx.work.Data.EMPTY, listOf(), androidx.work.Constraints.NONE, null, 0, 0, androidx.work.RetryPolicy.EXPONENTIAL, 0, 0)

    @Test
    fun workerConstructs() {
        val worker = OfflineStatSyncWorker(context, params)
        runBlocking {
            assertNotNull(worker)
        }
    }
}
