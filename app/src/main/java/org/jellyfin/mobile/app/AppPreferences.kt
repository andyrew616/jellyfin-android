package org.jellyfin.mobile.app

import android.content.Context
import android.content.SharedPreferences
import android.os.Environment
import android.view.WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_NONE
import androidx.core.content.edit
import org.jellyfin.mobile.player.mediasegments.MediaSegmentAction
import org.jellyfin.mobile.player.mediasegments.toMediaSegmentActionsString
import org.jellyfin.mobile.settings.ExternalPlayerPackage
import org.jellyfin.mobile.settings.VideoPlayerType
import org.jellyfin.mobile.utils.Constants
import org.jellyfin.sdk.model.api.MediaSegmentType
import java.io.File

class AppPreferences(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("${context.packageName}_preferences", Context.MODE_PRIVATE)

    var currentServerId: Long?
        get() = sharedPreferences.getLong(Constants.PREF_SERVER_ID, -1).takeIf { it >= 0 }
        set(value) {
            sharedPreferences.edit {
                if (value != null) putLong(Constants.PREF_SERVER_ID, value) else remove(Constants.PREF_SERVER_ID)
            }
        }

    var currentUserId: Long?
        get() = sharedPreferences.getLong(Constants.PREF_USER_ID, -1).takeIf { it >= 0 }
        set(value) {
            sharedPreferences.edit {
                if (value != null) putLong(Constants.PREF_USER_ID, value) else remove(Constants.PREF_USER_ID)
            }
        }

    var ignoreBatteryOptimizations: Boolean
        get() = sharedPreferences.getBoolean(Constants.PREF_IGNORE_BATTERY_OPTIMIZATIONS, false)
        set(value) {
            sharedPreferences.edit {
                putBoolean(Constants.PREF_IGNORE_BATTERY_OPTIMIZATIONS, value)
            }
        }

    var ignoreWebViewChecks: Boolean
        get() = sharedPreferences.getBoolean(Constants.PREF_IGNORE_WEBVIEW_CHECKS, false)
        set(value) {
            sharedPreferences.edit {
                putBoolean(Constants.PREF_IGNORE_WEBVIEW_CHECKS, value)
            }
        }

    var ignoreBluetoothPermission: Boolean
        get() = sharedPreferences.getBoolean(Constants.PREF_IGNORE_BLUETOOTH_PERMISSION, false)
        set(value) {
            sharedPreferences.edit {
                putBoolean(Constants.PREF_IGNORE_BLUETOOTH_PERMISSION, value)
            }
        }

    var downloadMethod: Int?
        get() = sharedPreferences.getInt(Constants.PREF_DOWNLOAD_METHOD, -1).takeIf { it >= 0 }
        set(value) {
            if (value != null) {
                sharedPreferences.edit {
                    putInt(Constants.PREF_DOWNLOAD_METHOD, value)
                }
            }
        }

    var downloadLocation: String
        get() {
            val savedStorage = sharedPreferences.getString(Constants.PREF_DOWNLOAD_LOCATION, null)
            if (savedStorage != null) {
                if (File(savedStorage).parentFile?.isDirectory == true) {
                    // Saved location is still valid
                    return savedStorage
                } else {
                    // Reset download option if corrupt
                    sharedPreferences.edit {
                        remove(Constants.PREF_DOWNLOAD_LOCATION)
                    }
                }
            }

            // Return default storage location
            return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).absolutePath
        }
        set(value) {
            sharedPreferences.edit {
                if (File(value).parentFile?.isDirectory == true) {
                    putString(Constants.PREF_DOWNLOAD_LOCATION, value)
                }
            }
        }

    var downloadToInternal: Boolean?
        get() = sharedPreferences.getBoolean(Constants.PREF_DOWNLOAD_INTERNAL, true)
        set(value) {
            if (value != null) {
                sharedPreferences.edit {
                    putBoolean(Constants.PREF_DOWNLOAD_METHOD, value)
                }
            }
        }

    /**
     * The actions to take for each media segment type. Managed by the MediaSegmentRepository.
     */
    var mediaSegmentActions: String
        get() = sharedPreferences.getString(
            Constants.PREF_MEDIA_SEGMENT_ACTIONS,
            mapOf(
                MediaSegmentType.INTRO to MediaSegmentAction.ASK_TO_SKIP,
                MediaSegmentType.OUTRO to MediaSegmentAction.ASK_TO_SKIP,
            ).toMediaSegmentActionsString(),
        )!!
        set(value) = sharedPreferences.edit { putString(Constants.PREF_MEDIA_SEGMENT_ACTIONS, value) }

    val musicNotificationAlwaysDismissible: Boolean
        get() = sharedPreferences.getBoolean(Constants.PREF_MUSIC_NOTIFICATION_ALWAYS_DISMISSIBLE, false)

    @VideoPlayerType
    val videoPlayerType: String
        get() = sharedPreferences.getString(Constants.PREF_VIDEO_PLAYER_TYPE, VideoPlayerType.WEB_PLAYER)!!

    val exoPlayerStartLandscapeVideoInLandscape: Boolean
        get() = sharedPreferences.getBoolean(Constants.PREF_EXOPLAYER_START_LANDSCAPE_VIDEO_IN_LANDSCAPE, false)

    val exoPlayerAllowSwipeGestures: Boolean
        get() = sharedPreferences.getBoolean(Constants.PREF_EXOPLAYER_ALLOW_SWIPE_GESTURES, true)

    val exoPlayerAllowPressSpeedUp: Boolean
        get() = sharedPreferences.getBoolean(Constants.PREF_EXOPLAYER_ALLOW_PRESS_SPEED_UP, true)

    val exoPlayerRememberBrightness: Boolean
        get() = sharedPreferences.getBoolean(Constants.PREF_EXOPLAYER_REMEMBER_BRIGHTNESS, false)

    var exoPlayerBrightness: Float
        get() = sharedPreferences.getFloat(Constants.PREF_EXOPLAYER_BRIGHTNESS, BRIGHTNESS_OVERRIDE_NONE)
        set(value) {
            sharedPreferences.edit {
                putFloat(Constants.PREF_EXOPLAYER_BRIGHTNESS, value)
            }
        }

    val exoPlayerAllowBackgroundAudio: Boolean
        get() = sharedPreferences.getBoolean(Constants.PREF_EXOPLAYER_ALLOW_BACKGROUND_AUDIO, false)

    val exoPlayerDirectPlayAss: Boolean
        get() = sharedPreferences.getBoolean(Constants.PREF_EXOPLAYER_DIRECT_PLAY_ASS, false)

    @ExternalPlayerPackage
    var externalPlayerApp: String
        get() = sharedPreferences.getString(Constants.PREF_EXTERNAL_PLAYER_APP, ExternalPlayerPackage.SYSTEM_DEFAULT)!!
        set(value) = sharedPreferences.edit { putString(Constants.PREF_EXTERNAL_PLAYER_APP, value) }

    var lastPlayedItemId: String?
        get() = sharedPreferences.getString(Constants.LAST_PLAYED_ITEM_ID, null)
        set(
            value
        ) {
            sharedPreferences.edit {
                if (value != null) putString(
                    Constants.LAST_PLAYED_ITEM_ID,
                    value,
                ) else remove(Constants.LAST_PLAYED_ITEM_ID)
            }
        }

    var lastPlayedPosition: Long?
        get() = sharedPreferences.getLong(Constants.LAST_PLAYED_POSITION, -1L).takeIf { it >= 0 }
        set(
            value
        ) {
            sharedPreferences.edit {
                if (value != null) putLong(
                    Constants.LAST_PLAYED_POSITION,
                    value,
                ) else remove(Constants.LAST_PLAYED_POSITION)
            }
        }
}
