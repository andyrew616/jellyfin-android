package org.jellyfin.mobile.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = OfflinePlaybackStatEntity.TABLE_NAME)
data class OfflinePlaybackStatEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = OfflinePlaybackStatEntity.ID)
    val id: Long = 0,
    @ColumnInfo(name = OfflinePlaybackStatEntity.EVENT_TYPE)
    val eventType: String,
    @ColumnInfo(name = OfflinePlaybackStatEntity.EVENT_JSON)
    val eventJson: String,
) {
    companion object {
        const val TABLE_NAME = "OfflinePlaybackStat"
        const val ID = "id"
        const val EVENT_TYPE = "event_type"
        const val EVENT_JSON = "event_json"
    }
}
