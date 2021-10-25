package com.example.musicplayer.callback

import android.app.Notification
import android.content.Intent
import androidx.core.content.ContextCompat
import com.example.musicplayer.service.MusicService
import com.google.android.exoplayer2.ui.PlayerNotificationManager

private const val NOTIFICATION_ID = 1

class MusicPlayerListener(private val musicService: MusicService) :
    PlayerNotificationManager.NotificationListener {

    override fun onNotificationCancelled(notificationId: Int, dismissedByUser: Boolean) {
        super.onNotificationCancelled(notificationId, dismissedByUser)
        musicService.stopForeground(true)
        musicService.forground = false
        musicService.stopSelf()
    }

    override fun onNotificationPosted(
        notificationId: Int,
        notification: Notification,
        ongoing: Boolean
    ) {
        super.onNotificationPosted(notificationId, notification, ongoing)
        musicService.apply {
            if (ongoing && forground) {
                ContextCompat.startForegroundService(
                    this,
                    Intent(applicationContext, this::class.java)
                )
            }
            startForeground(NOTIFICATION_ID, notification)
            forground = true
        }
    }
}