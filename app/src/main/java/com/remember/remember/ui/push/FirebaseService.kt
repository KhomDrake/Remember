package com.remember.remember.ui.push

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.remember.remember.R
import com.remember.repository.push.InformationDevice
import kotlin.random.Random

class FirebaseService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        InformationDevice.setupNewToken(token)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        if (message.containsData("title") && message.containsData("body") && message.containsData("category")) {
            handleNotification(
                title = message.data["title"] ?: "",
                body = message.data["body"] ?: "",
                actionLink = message.data["action_link"],
                category = message.data["category"] ?: ""
            )
        }
    }

    private fun RemoteMessage.containsData(key: String) : Boolean {
        return data.containsKey(key) && data[key].isNullOrEmpty().not()
    }

    private fun handleNotification(title: String, body: String, actionLink: String?, category: String) {
        val pushCategory = PushCategory.values().find {
            it.name == category.toUpperCase()
        } ?: PushCategory.APP_INFORMATION

        val notificationId = Random.nextInt(0, Int.MAX_VALUE)

        val notification = NotificationCompat.Builder(this, pushCategory.name).apply {
            setSmallIcon(R.drawable.app_ic_logo)
            setContentTitle(title)
            setContentText(body)
            color = resources.getColor(R.color.remember_primary_color, null)
            setStyle(NotificationCompat.BigTextStyle().bigText(body))
            if(actionLink.isNullOrEmpty().not()) {
                actionLink?.let {
                    setContentIntent(getPendingIntent(configureIntent(it), notificationId))
                }
            }
            setAutoCancel(true)
            priority = pushCategory.importance
        }.build()

        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.notify(notificationId, notification)
    }

    private fun configureIntent(actionLink: String) : Intent {
        val rememberLink = actionLink.contains("www.remember.com")

        val completeLink = when {
            actionLink.startsWith("/") -> "remember:/$actionLink"
            else -> actionLink
        }
        return Intent(
            if(rememberLink) Intent.ACTION_VIEW else packageName,
            Uri.parse(completeLink)
        )
    }

    private fun getPendingIntent(intent: Intent, notificationId: Int) =
        PendingIntent.getActivity(this, notificationId, intent, PendingIntent.FLAG_ONE_SHOT)
}