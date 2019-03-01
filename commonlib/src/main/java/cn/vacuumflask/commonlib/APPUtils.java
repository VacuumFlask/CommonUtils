package cn.vacuumflask.commonlib;

import android.app.Application;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

//App工具类
public class APPUtils {

    /**
     * 获取MAC地址
     * 需要添加权限
     * <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>
     * <uses-permission android:name="android.permission.CHANGE_WIFI_STATE"/>
     * <uses-permission android:name="android.permission.WAKE_LOCK"/>
     *
     * @param app Application
     * @return MAC 地址
     */
    public static String getMacAddress(Application app) {
        String macAddress = "";
        try {
            WifiManager service = (WifiManager) app.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            if (service != null) {
                WifiInfo info = service.getConnectionInfo();

                if (!service.isWifiEnabled()) {
                    //必须先打开，才能获取到MAC地址
                    service.setWifiEnabled(true);
                    service.setWifiEnabled(false);
                }

                macAddress = info.getMacAddress();
            }

            return macAddress;
        } catch (Exception e) {
            e.printStackTrace();
            L.e("获取MAC报错：" + e.getMessage());
            return macAddress;
        }
    }
}
