package COM.TQC.GDD01;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class NotificationIntentService extends IntentService
{
    public NotificationIntentService()
    {
        super("notificationIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable final Intent intent)
    {
        final Intent fromIntent = intent;
        switch(intent.getAction())
        {
            case Constants.EXTRA_REMOTE_LEFT_BUTTON:
                Handler leftHandler = new Handler(Looper.getMainLooper());
                leftHandler.post(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        Intent openIntent = new Intent(getApplicationContext(), MyMsgHandler.class);
                        if(fromIntent.getExtras()!=null)
                        {
                            openIntent.putExtras(fromIntent.getExtras());
                        }
                        openIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(openIntent);
                        Intent it = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
                        sendBroadcast(it);
                        Toast.makeText(getBaseContext(), "You clicked the left button", Toast.LENGTH_LONG).show();
                    }
                });
                break;
            case Constants.EXTRA_REMOTE_RIGHT_BUTTON:
                Handler rightHandler = new Handler(Looper.getMainLooper());
                rightHandler.post(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        if(fromIntent.getExtras()!=null && fromIntent.getExtras().containsKey(Constants.EXTRA_NOTIFICATION_URI))
                        {
                            Intent openIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(fromIntent.getExtras().getString(Constants.EXTRA_NOTIFICATION_URI)));
                            openIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(openIntent);
                        }
                        Intent it = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
                        sendBroadcast(it);
                        Toast.makeText(getBaseContext(), "You clicked the right button", Toast.LENGTH_LONG).show();
                    }
                });
                break;
        }
    }
}
