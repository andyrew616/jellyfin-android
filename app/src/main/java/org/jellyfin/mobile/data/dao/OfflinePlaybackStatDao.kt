package org.jellyfin.mobile.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import org.jellyfin.mobile.data.entity.OfflinePlaybackStatEntity

@Dao
interface OfflinePlaybackStatDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: OfflinePlaybackStatEntity): Long

    @Query("SELECT * FROM ${OfflinePlaybackStatEntity.TABLE_NAME}")
    suspend fun getAll(): List<OfflinePlaybackStatEntity>

    @Query("DELETE FROM ${OfflinePlaybackStatEntity.TABLE_NAME} WHERE ${OfflinePlaybackStatEntity.ID} = :id")
    suspend fun delete(id: Long)
}
