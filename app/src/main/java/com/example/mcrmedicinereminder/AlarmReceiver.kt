package com.example.mcrmedicinereminder

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
const val ChannelID = "Channel1"
const val NotificationId = 1
class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {

        val notification = NotificationCompat.Builder(context, ChannelID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(intent.getStringExtra("Title Extra "))
            .setContentText(intent.getStringExtra("Message Extra "))
            .build()

        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(NotificationId,notification)
    }
}