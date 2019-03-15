package com.example.mplayer.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.IdRes;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;
import com.example.mplayer.R;
import com.example.mplayer.play.MusicController;

import static com.example.mplayer.utils.Constant.Action.ACTION_NOTIFICATION_BUTTON_CLICK;
import static com.example.mplayer.utils.Constant.Extras.EXTRA_BUTTON_CLICKED;
import static com.example.mplayer.utils.Constant.Notification.CHANNEL_DESCRIPTION;
import static com.example.mplayer.utils.Constant.Notification.CHANNEL_ID;
import static com.example.mplayer.utils.Constant.Notification.CHANNEL_NAME;
import static com.example.mplayer.utils.Constant.Notification.NOTIFICATION_ID;


public class MusicService extends Service {
    private MusicController mMusicController;
    private IBinder mBinder = new LocalBinder();
    private RemoteViews mNotificationLayout;

    @Override
    public void onCreate() {
        super.onCreate();
        if (mMusicController == null) {
            mMusicController = new MusicController(this);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        setControlsNotification();
        registerReceiver(receiver, new IntentFilter(ACTION_NOTIFICATION_BUTTON_CLICK));
        return mBinder;
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(receiver);
        super.onDestroy();
    }

    public class LocalBinder extends Binder {
        public MusicService getService() {
            return MusicService.this;
        }
    }

    public MusicController getMusicController() {
        return mMusicController;
    }

    public void showNotification() {
        if (mMusicController.isPlaying()) {
            mNotificationLayout.setImageViewResource(R.id.button_play, R.drawable.icon_pause);
        } else mNotificationLayout.setImageViewResource(R.id.button_play, R.drawable.icon_play);
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setCustomContentView(mNotificationLayout)
                .build();
        startForeground(NOTIFICATION_ID, notification);
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance);
            channel.setDescription(CHANNEL_DESCRIPTION);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private PendingIntent onButtonNotificationClick(@IdRes int id) {
        Intent intent = new Intent(ACTION_NOTIFICATION_BUTTON_CLICK);
        intent.putExtra(EXTRA_BUTTON_CLICKED, id);
        return PendingIntent.getBroadcast(this, id, intent, 0);
    }

    private void setControlsNotification() {
        createNotificationChannel();
        mNotificationLayout =
                new RemoteViews(getPackageName(), R.layout.notification_music);
        mNotificationLayout.setOnClickPendingIntent(R.id.button_play,
                onButtonNotificationClick(R.id.button_play));
        mNotificationLayout.setOnClickPendingIntent(R.id.button_next,
                onButtonNotificationClick(R.id.button_next));
        mNotificationLayout.setOnClickPendingIntent(R.id.button_previous,
                onButtonNotificationClick(R.id.button_previous));
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int id = intent.getIntExtra(EXTRA_BUTTON_CLICKED, -1);
            switch (id) {
                case R.id.button_play:
                    if (mMusicController.isPlaying()) {
                        mMusicController.pause();
                    } else mMusicController.play();
                    showNotification();
                    break;

                case R.id.button_previous:
                    mMusicController.previous();
                    break;

                case R.id.button_next:
                    mMusicController.next();
                    break;

                default:
                    break;
            }
        }
    };
}
