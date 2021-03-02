package cn.vacuumflask.commonutils;

import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import cn.vacuumflask.commonlib.notification.NotificationOpt;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void create(View view) {
        NotificationOpt notificationUtils = new NotificationOpt(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            notificationUtils.createNotification(R.layout.notification_custom,R.layout.notification_big_custom);
        }
    }
}
