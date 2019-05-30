package cn.vacuumflask.commonlib;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.DisplayMetrics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.vacuumflask.commonlib.entity.AppInfo;
import cn.vacuumflask.commonlib.entity.ScreenInfo;

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


    /**
     * 获取包名，主界面包名 类名 和 app名称
     *
     * @param context 上下文
     * @return 所以已安装的APP信息
     */
    public static List<AppInfo> getAppInfos(Context context) {
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> appList = pm.queryIntentActivities(intent, 0);
        Collections.sort(appList, new ResolveInfo.DisplayNameComparator(pm));

        ArrayList<AppInfo> list = new ArrayList<>();
        for (int i = 0; i < appList.size(); i++) {
            String pkg = appList.get(i).activityInfo.packageName;
            String cls = appList.get(i).activityInfo.name;
            String title = "";

            try {
                ApplicationInfo applicationInfo = pm.getPackageInfo(pkg, i).applicationInfo;
                title = applicationInfo.loadLabel(pm).toString();
            } catch (Exception e) {
                e.printStackTrace();
            }

            list.add(new AppInfo(title, pkg, cls));

        }

        return list;
    }

    /**
     * 获取屏幕信息
     *
     * @param context 上下文
     * @return 屏幕信息 返回值 可能为空
     */
    public static ScreenInfo getScreenInfo(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        if (dm == null) {
            return null;
        }

        ScreenInfo info = new ScreenInfo();
        info.setWidth(dm.widthPixels);
        info.setHeight(dm.heightPixels);
        info.setDensityDpi(dm.densityDpi);
        return info;
    }

}
