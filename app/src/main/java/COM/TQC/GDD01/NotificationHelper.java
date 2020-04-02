package COM.TQC.GDD01;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;

import java.nio.channels.Channel;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class NotificationHelper extends ContextWrapper {
    private NotificationManager manager;
    public static final String PRIMARY_CHANNEL = "default";
    public static final String SECONDARY_CHANNEL = "second";

    public NotificationHelper(Context ctx) {
        super(ctx);

        NotificationChannel channelA = new NotificationChannel(PRIMARY_CHANNEL, getString(R.string.str_primary_notification), NotificationManager.IMPORTANCE_HIGH);
        // TO DO
        getManager().createNotificationChannel(channelA);

        NotificationChannel channelB = new NotificationChannel(SECONDARY_CHANNEL, getString(R.string.str_secondary_notification), NotificationManager.IMPORTANCE_HIGH);
        // TO DO
        getManager().createNotificationChannel(channelB);
    }

    public Notification getNotification1(String title, String body) {
        Intent intent = new Intent(this, MyMsgHandler.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        // TO DO
        intent.putExtra(Constants.EXTRA_NOTIFICATION_MSG, body);
        PendingIntent pendingintent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.notificationbg);

        Notification notification = new NotificationCompat.Builder(getApplicationContext(), PRIMARY_CHANNEL)
                .setSmallIcon(R.drawable.sms)
                .setContentTitle(title)
                .setContentText(body)
                .setLargeIcon(icon)
                .setCategory(Notification.CATEGORY_MESSAGE)
                .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(icon).bigLargeIcon(null))
                .setAutoCancel(true)
                .setContentIntent(pendingintent)
                .build();

        // TO DO

        return notification;
    }

    public Notification getNotification2(String title, String body, String strUri) {
        Intent intent = new Intent(this, MyMsgHandler.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        // TO DO

        PendingIntent pendingintent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        // TO DO
        RemoteViews expandedView = new RemoteViews(getPackageName(), R.layout.notification_expanded);
        // TO DO

        Intent leftIntent = new Intent(this, NotificationIntentService.class);
        // TO DO
        leftIntent.setAction(Constants.EXTRA_REMOTE_LEFT_BUTTON);
        leftIntent.putExtra(Constants.EXTRA_NOTIFICATION_MSG, body);

        Intent rightIntent = new Intent(this, NotificationIntentService.class);
        // TO DO
        rightIntent.setAction(Constants.EXTRA_REMOTE_RIGHT_BUTTON);
        rightIntent.putExtra(Constants.EXTRA_NOTIFICATION_URI, strUri);

        PendingIntent leftpending = PendingIntent.getService(this, 1, leftIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent rightpending = PendingIntent.getService(this, 1, rightIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        expandedView.setOnClickPendingIntent(R.id.notification_collapsed_left_button, leftpending);
        expandedView.setOnClickPendingIntent(R.id.notification_collapsed_right_button, rightpending);

        RemoteViews collapsedView = new RemoteViews(getPackageName(), R.layout.notification_collapsed);
        // TO DO
        SimpleDateFormat simple = new SimpleDateFormat("a h:mm");
        Date date = new Date(System.currentTimeMillis());
        collapsedView.setTextViewText(R.id.timestamp, simple.format(date));

        Notification notification = new NotificationCompat.Builder(getApplicationContext(), SECONDARY_CHANNEL)
                .setSmallIcon(R.drawable.sms)
                .setContentTitle(title)
                .setContentText(body)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setCustomContentView(collapsedView)
                .setCustomBigContentView(expandedView)
                .setStyle(new NotificationCompat.DecoratedCustomViewStyle())
                .setContentIntent(pendingintent)
                .setAutoCancel(true)
                .build();
        return notification;
    }

    public void notify(int id, Notification notification) {
        getManager().notify(id, notification);
    }

    private NotificationManager getManager() {
        if (manager == null) {
            manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return manager;
    }
}
