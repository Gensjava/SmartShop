package ua.smartshop.Services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import ua.smartshop.Activity.MainActivity;
import ua.smartshop.Fragments.ProfileFragment;
import ua.smartshop.Fragments.ProfileRootFragment;
import ua.smartshop.R;
import ua.smartshop.Utils.Сonstants;

/**
 * Created by Gens on 11.05.2015.
 */
public class StatusService extends Service {

    //
    public static final long NOTIFY_INTERVAL =  60 * 15 * 1000;
    public static final String ACTION_SERVICE_STATUS = "ACTION_SERVICE_STATUS";
    private NotificationManager notificationManager;

    //
    private Handler mHandler = new Handler();
    //
    private Timer mTimer = null;
    private int numMessages;
    private int notifyID;

    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return Service.START_STICKY;
    }
    @Override
    public void onCreate() {
        Context context = getApplicationContext();
        notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        //
        if (mTimer != null) {
            mTimer.cancel();
        } else {
            //
            mTimer = new Timer();
        }
        //
        mTimer.scheduleAtFixedRate(new TimeDisplayTimerTask(), 0,
                NOTIFY_INTERVAL);
    }

    class TimeDisplayTimerTask extends TimerTask {

        @Override
        public void run() {
            //
            mHandler.post(new Runnable() {

                @Override
                public void run() {
                    showNotification();
                }
            });
        }

        // используйте метод в сообщении для вывода текущего времени
        private String getDateTime() {
            //
            SimpleDateFormat sdf = new SimpleDateFormat(
                    "[dd/MM/yyyy - HH:mm:ss]", Locale.getDefault());
            return sdf.format(new Date());
        }
    }

    private void showNotification() {

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(ACTION_SERVICE_STATUS, "ACTION_SERVICE_STATUS");
        ++numMessages;
        String textNotification = "Заказ №"+numMessages+" изменился на статус в пути.";

       PendingIntent contentIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        final Notification notification = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_action_notification_bar)
                .setContentTitle("Статус в заказах")
                .setContentIntent(contentIntent)
                .setNumber(numMessages)
                .setContentText(textNotification)
                .build();
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        //
        notificationManager.notify(notifyID++ , notification);
        //история
        Сonstants.mHistoryNotice.add(textNotification);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mTimer.cancel();
    }
}