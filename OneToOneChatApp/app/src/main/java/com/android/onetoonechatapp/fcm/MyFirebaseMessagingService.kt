package com.android.onetoonechatapp.fcm

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import android.util.Log
import androidx.core.app.NotificationCompat
import com.android.onetoonechatapp.FirebaseChatMainApp
import com.android.onetoonechatapp.R
import com.android.onetoonechatapp.events.PushNotificationEvent
import com.android.onetoonechatapp.ui.activities.chat.ChatActivity
import com.android.onetoonechatapp.utils.Constants
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import org.greenrobot.eventbus.EventBus

class MyFirebaseMessagingService : FirebaseMessagingService() {

    private val TAG = "MyFirebaseMsgService"

    override fun onMessageReceived(remoteMessage: RemoteMessage) {

        if (remoteMessage.data.isNotEmpty()) {

            Log.d(TAG, "Message data payload: " + remoteMessage.data)
            val title = remoteMessage.data["title"]
            val message = remoteMessage.data["text"]
            val username = remoteMessage.data["username"]
            val uid = remoteMessage.data["uid"]
            val fcmToken = remoteMessage.data["fcm_token"]

            // Don't show notification if chat activity is open.
            if (!FirebaseChatMainApp.isChatActivityOpen()) {
                sendNotification(
                    title,
                    message,
                    username,
                    uid,
                    fcmToken
                )
            } else {
                EventBus.getDefault().post(
                    PushNotificationEvent(
                        title,
                        message,
                        username,
                        uid,
                        fcmToken
                    )
                )
            }
        }
    }

    private fun sendNotification(
        title: String?,
        message: String?,
        receiver: String?,
        receiverUid: String?,
        firebaseToken: String?
    ) {
        val intent = Intent(this, ChatActivity::class.java)
        intent.putExtra(Constants.ARG_RECEIVER, receiver)
        intent.putExtra(Constants.ARG_RECEIVER_UID, receiverUid)
        intent.putExtra(Constants.ARG_FIREBASE_TOKEN, firebaseToken)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_ONE_SHOT
        )

        val defaultSoundUri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder: NotificationCompat.Builder = NotificationCompat.Builder(this)
            .setSmallIcon(R.drawable.ic_baseline_message_24)
            .setContentTitle(title)
            .setContentText(message)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.notify(0, notificationBuilder.build())
    }
}