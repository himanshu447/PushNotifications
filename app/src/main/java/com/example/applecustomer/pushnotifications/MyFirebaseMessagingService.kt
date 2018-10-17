package com.example.applecustomer.pushnotifications

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context

import android.content.Intent
import android.support.v4.app.NotificationCompat
import android.util.Log

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import android.media.RingtoneManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.support.v4.app.NotificationManagerCompat
import java.net.HttpURLConnection
import java.net.URL
import android.widget.RemoteViews
import java.util.*


class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onNewToken(p0: String?) {
        Log.e("TAG", "token is $p0")

    }

    companion object {
        private  val name = mutableListOf<String?>()
    }



    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        super.onMessageReceived(remoteMessage)

        Log.e("TAG", "onmesg recivved $remoteMessage")

        val random = Random()

        val rendomId = random.nextInt(1000 + 1)

        name.add(remoteMessage?.data?.get("name"))

        //create notification

        // Create an Intent for the activity you want to start
        val intent = Intent(this, Main2Activity::class.java)

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.putExtra("name", remoteMessage?.data?.get("name"))
        intent.putExtra("address", remoteMessage?.data?.get("address"))
        intent.putExtra("id", rendomId)
        intent.putExtra("pin", remoteMessage?.data?.get("pinCode"))

        Log.e("NAME", "is${remoteMessage?.data?.get("name")}")

        // Create the TaskStackBuilder
        val resultPendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {

            TaskStackBuilder.create(this).run {

                // Add the intent, which inflates the back stack
                addNextIntentWithParentStack(intent)

                // Get the PendingIntent containing the entire back stack
                getPendingIntent(rendomId, PendingIntent.FLAG_UPDATE_CURRENT)
            }
        } else {
            TODO("VERSION.SDK_INT < JELLY_BEAN")
        }


        val GROUP_KEY_WORK_EMAIL = "com.android.example.NOTIFICATION"

        val SUMMARY_ID = 0

        val pendingIntent = PendingIntent.getActivity(this, 1410, intent, PendingIntent.FLAG_ONE_SHOT)

        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val contentViewSmall = RemoteViews(packageName, R.layout.custom_notification_small)

        contentViewSmall.setTextViewText(R.id.title, remoteMessage?.notification?.body)

        val bigview = RemoteViews(packageName, R.layout.custom_notification)

        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.knoxpo_bg)

        val bitmap1 = BitmapFactory.decodeResource(resources, R.drawable.googleg_disabled_color_18)


        val notificationBuilder = NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.googleg_standard_color_18)
                //.setLargeIcon(bitmap1)
                .setContentTitle("title")
                .setContentText(remoteMessage?.notification?.body)
                .setCustomContentView(contentViewSmall)
                .setSound(defaultSoundUri)
                .setAutoCancel(true)
                /*.setStyle(NotificationCompat.BigPictureStyle()
                        .bigPicture(bitmap)
                        .bigLargeIcon(null))*/
                .addAction(R.drawable.ic_send_black_24dp, "button 1", resultPendingIntent)
                .addAction(R.drawable.ic_highlight_off_black_24dp, "button 2", resultPendingIntent)
                .setContentIntent(resultPendingIntent)
                .setGroup(GROUP_KEY_WORK_EMAIL)
                .build()

        val inboxStyle = NotificationCompat.InboxStyle()
        inboxStyle.setBigContentTitle("the inbx title")
                .setSummaryText("summey text")

        for (i in 0 until name.size) {
            inboxStyle.addLine("name is :- ${name[i]}")
        }


        val summaryNotification = NotificationCompat.Builder(this)
                .setContentTitle("this is summery title")
                //set content text to support devices running API level < 24
                .setContentText("this is summery Text")
                .setSmallIcon(R.drawable.googleg_standard_color_18)
                //build summary info into InboxStyle template
                .setStyle(inboxStyle)
                //specify which group this notification belongs to
                .setGroup(GROUP_KEY_WORK_EMAIL)
                //set this notification as the summary for the group
                .setGroupSummary(true)
                .build()

        NotificationManagerCompat.from(this).apply {
            notify(rendomId, notificationBuilder)
            notify(SUMMARY_ID, summaryNotification)
        }

        /* val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

         notificationManager.notify(rendomId, notificationBuilder)
 */

        /*    val bitmap = getBitmapfromUrl(remoteMessage?.data?.get("image-url")!!) //obtain the image
            val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            val notificationBuilder = NotificationCompat.Builder(this)
                    .setLargeIcon(bitmap)  //set it in the notification
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(remoteMessage.data["title"])
                    .setContentText(remoteMessage.data["message"])
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent)*/
    }


    //Simple method for image downloading
    fun getBitmapfromUrl(imageUrl: String): Bitmap? {
        try {
            val url = URL(imageUrl)
            val connection = url.openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()
            val input = connection.inputStream
            return BitmapFactory.decodeStream(input)

        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }

    }

}

