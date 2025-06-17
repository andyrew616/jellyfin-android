package org.jellyfin.mobile.data

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import kotlinx.coroutines.runBlocking
import org.jellyfin.mobile.data.dao.OfflinePlaybackStatDao
import org.jellyfin.mobile.data.entity.OfflinePlaybackStatEntity
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class OfflinePlaybackStatDaoTest {
    private val db = Room.inMemoryDatabaseBuilder(
        ApplicationProvider.getApplicationContext(),
        JellyfinDatabase::class.java
    ).build()

    private val dao: OfflinePlaybackStatDao = db.offlinePlaybackStatDao

    @Test
    fun insertAndGet() = runBlocking {
        val id = dao.insert(OfflinePlaybackStatEntity(eventType = "start", eventJson = "{}"))
        val all = dao.getAll()
        assertEquals(1, all.size)
        assertEquals(id, all[0].id)
    }
}
