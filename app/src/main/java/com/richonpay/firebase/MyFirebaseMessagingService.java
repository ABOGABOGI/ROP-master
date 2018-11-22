package com.richonpay.firebase;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.richonpay.R;
import com.richonpay.RootActivity;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    public static final String NOTIFICATION_INTENT = "NOTIFICATION_INTENT";
    private static final String TAG = "MyFirebaseMsgService";
    private static int notificationId = 0;

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // [START_EXCLUDE]
        // There are two types of messages data messages and notification messages. Data messages are handled
        // here in onMessageReceived whether the app is in the foreground or background. Data messages are the type
        // traditionally used with GCM. Notification messages are only received here in onMessageReceived when the app
        // is in the foreground. When the app is in the background an automatically generated notification is displayed.
        // When the user taps on the notification they are returned to the app. Messages containing both notification
        // and data payloads are treated as notification messages. The Firebase console always sends notification
        // messages. For more see: https://firebase.google.com/docs/cloud-messaging/concept-options
        // [END_EXCLUDE]

        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Gson gson = new Gson();
//        Log.e(TAG, "remoteMessage.getNotification(): " + gson.toJson(remoteMessage.getNotification()));
//        Log.e(TAG, "remoteMessage.getNotification().getTitle(): " + String.valueOf(remoteMessage.getNotification().getTitle()));
//        Log.e(TAG, "remoteMessage.getNotification().getBody(): " + remoteMessage.getNotification().getBody());
//        Log.e(TAG, "Message data payload: type " + remoteMessage.getData().get("type"));
//        Log.e(TAG, "Message data payload: id " + remoteMessage.getData().get("id"));
//        Log.e(TAG, "Message data payload: " + remoteMessage.getData());
        // Check if message contains a data payload.
        if (remoteMessage.getData() != null) {
            if (remoteMessage.getData().get("type_string") != null) {
                if (remoteMessage.getData().get("type_string").equals("3")) {
                    Intent intent = new Intent("notif_topup");
                    //send broadcast
                    this.sendBroadcast(intent);
                }

            }
        }
        if (remoteMessage.getNotification() != null) {  //FOREGROUND
            showNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody(), remoteMessage.getData().get("type"));
        } else {  //BACKGROUND
            showNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody(), remoteMessage.getData().get("type"));
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }
    // [END receive_message]

    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param messageBody FCM message body received.
     */

    private void showNotification(String title, String messageBody, String type) {
        try {
            Intent intent = new Intent(this, RootActivity.class);
            intent.putExtra(NOTIFICATION_INTENT, type);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 2985 /* Request code */, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder notificationBuilder;

            if (Build.VERSION.SDK_INT >= 21) {
                notificationBuilder = new NotificationCompat.Builder(this, String.valueOf(notificationId))
                        .setSmallIcon(R.drawable.logo_home)
                        .setColor(this.getResources().getColor(R.color.colorPrimary))
                        .setGroupSummary(true)
                        .setGroup("NOTIFICATION")
                        .setContentTitle(title)
                        .setContentText(messageBody)
                        .setAutoCancel(true)
                        .setChannelId(String.valueOf(notificationId))
                        .setDefaults(Notification.DEFAULT_ALL)
                        .setPriority(Notification.PRIORITY_MAX)
                        .setContentIntent(pendingIntent);
            } else {
                notificationBuilder = new NotificationCompat.Builder(this, String.valueOf(notificationId))
                        .setSmallIcon(R.drawable.logo_home)
                        .setGroupSummary(true)
                        .setGroup("NOTIFICATION")
                        .setContentTitle(title)
                        .setContentText(messageBody)
                        .setAutoCancel(true)
                        .setChannelId(String.valueOf(notificationId))
                        .setSound(defaultSoundUri)
                        .setDefaults(Notification.DEFAULT_ALL)
                        .setPriority(Notification.PRIORITY_MAX)
                        .setContentIntent(pendingIntent);
            }

            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            if (Build.VERSION.SDK_INT >= 26) {
                NotificationChannel mChannel = new NotificationChannel("Mareco", "Mareco", NotificationManager.IMPORTANCE_HIGH);
                mChannel.setDescription(title);
                mChannel.enableVibration(true);
                if (notificationManager != null) {
                    notificationManager.createNotificationChannel(mChannel);
                }
            }

            notificationManager.notify(notificationId++, notificationBuilder.build());
            notificationId++;
        } catch (Exception exception) {
            Log.e("ERROR", "" + exception);
        }
    }

    @Override
    public void onSendError(String s, Exception e) {
        super.onSendError(s, e);
        Log.e("SEND ERROR", "s: " + s + "\n\n\n e: " + e);
    }
}