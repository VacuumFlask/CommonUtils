package cn.vacuumflask.commonlib.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Author: WeiCheng
 * Date: 2021/2/25 11:46 AM
 * Description: 通知栏工具栏 操作类
 */
public class NotificationOpt {

    private Context context;
    private String channelId;
    private String channelName;
    private int notificationId;

    public NotificationOpt(Context context) {
        this.context = context;
        channelId = "NotificationMessage";
        channelName = "通知消息";
        notificationId = 1;
    }

    public NotificationOpt(Context context, String channelId, String channelName, int notificationId) {
        this.context = context;
        this.channelId = channelId;
        this.channelName = channelName;
        this.notificationId = notificationId;
    }

    public void createNotification(int layoutId) {

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
        }

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), layoutId);
        NotificationCompat.DecoratedCustomViewStyle style = new NotificationCompat.DecoratedCustomViewStyle();
        Notification notification = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(android.R.mipmap.sym_def_app_icon)
                .setCustomBigContentView(remoteViews)
                .setCustomContentView(remoteViews)
                .setOngoing(true)
                .build();

        notificationManager.notify(notificationId, notification);
    }

    public void createNotification(int layoutId,int bigLayoutId) {

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
        }

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), layoutId);
        RemoteViews bigRemoteViews = new RemoteViews(context.getPackageName(), bigLayoutId);
        NotificationCompat.DecoratedCustomViewStyle style = new NotificationCompat.DecoratedCustomViewStyle();
        Notification notification = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(android.R.mipmap.sym_def_app_icon)
                .setCustomBigContentView(bigRemoteViews)
                .setCustomContentView(remoteViews)
                .setOngoing(true)
                .build();

        notificationManager.notify(notificationId, notification);
    }

}
