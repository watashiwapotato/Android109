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

public class NotificationHelper extends ContextWrapper
{
    private NotificationManager manager;
    public static final String PRIMARY_CHANNEL = "default";
    public static final String SECONDARY_CHANNEL = "second";

    public NotificationHelper(Context ctx)
    {
        super(ctx);

        NotificationChannel channelA = null;
        // TO DO
        getManager().createNotificationChannel(channelA);

        NotificationChannel channelB = null;
        // TO DO
        getManager().createNotificationChannel(channelB);
    }

    public Notification getNotification1(String title, String body)
    {
        Intent intent = new Intent(this, MyMsgHandler.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        // TO DO

        PendingIntent pendingintent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.notificationbg);

        Notification notification = null;
        // TO DO

        return notification;
    }

    public Notification getNotification2(String title, String body, String strUri)
    {
        Intent intent = new Intent(this, MyMsgHandler.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        // TO DO

        PendingIntent pendingintent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        // TO DO
        RemoteViews expandedView = new RemoteViews(getPackageName(), R.layout.notification_expanded);
        // TO DO

        Intent leftIntent = new Intent(this, NotificationIntentService.class);
        // TO DO

        Intent rightIntent = new Intent(this, NotificationIntentService.class);
        // TO DO

        RemoteViews collapsedView = new RemoteViews(getPackageName(), R.layout.notification_collapsed);
        // TO DO

        Notification notification = new NotificationCompat.Builder(getApplicationContext(), PRIMARY_CHANNEL)
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

    public void notify(int id, Notification notification)
    {
        getManager().notify(id, notification);
    }

    private NotificationManager getManager()
    {
        if (manager == null)
        {
            manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return manager;
    }
}
