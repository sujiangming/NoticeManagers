package com.jdry.noticemanagers.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.provider.Settings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by tgvincent on 2018/6/25.
 * 国内手机厂商白名单跳转工具类
 *
 * @author tgvincent
 * @version 1.0
 */
public class OpenLauncherProcessUtils {

    public static void enterWhiteListSetting(Context context) {
        try {
            context.startActivity(getSettingIntent());
        } catch (Exception e) {
            context.startActivity(new Intent(Settings.ACTION_SETTINGS));
        }
    }

    private static Intent getSettingIntent() {
        ComponentName componentName = null;
        String brand = android.os.Build.BRAND;
        switch (brand.toLowerCase()) {
            case "samsung":
                componentName = new ComponentName("com.samsung.android.sm",
                        "com.samsung.android.sm.app.dashboard.SmartManagerDashBoardActivity");
                break;
            case "huawei":
                componentName = new ComponentName("com.huawei.systemmanager",
                        "com.huawei.systemmanager.startupmgr.ui.StartupNormalAppListActivity");
                break;
            case "xiaomi":
                componentName = new ComponentName("com.miui.securitycenter",
                        "com.miui.permcenter.autostart.AutoStartManagementActivity");
                break;
            case "vivo":
                componentName = new ComponentName("com.iqoo.secure",
                        "com.iqoo.secure.ui.phoneoptimize.AddWhiteListActivity");
                break;
            case "oppo":
                componentName = new ComponentName("com.coloros.oppoguardelf",
                        "com.coloros.powermanager.fuelgaue.PowerUsageModelActivity");
                break;
            case "360":
                componentName = new ComponentName("com.yulong.android.coolsafe",
                        "com.yulong.android.coolsafe.ui.activity.autorun.AutoRunListActivity");
                break;
            case "meizu":
                componentName = new ComponentName("com.meizu.safe",
                        "com.meizu.safe.permission.SmartBGActivity");
                break;
            case "oneplus":
                componentName = new ComponentName("com.oneplus.security",
                        "com.oneplus.security.chainlaunch.view.ChainLaunchAppListActivity");
                break;
            default:
                break;
        }
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (componentName != null) {
            intent.setComponent(componentName);
        } else {
            intent.setAction(Settings.ACTION_SETTINGS);
        }
        return intent;
    }

    private static final String permName = "android.permission.RECEIVE_BOOT_COMPLETED";

    public static List<String> listBootApps(Context context) {
        List<String> bootAppNames = new ArrayList<String>();
        PackageManager pm = context.getPackageManager();
        //获取所有安装的App的信息
        List<ApplicationInfo> appInfos = pm.getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES);
        for (ApplicationInfo appInfo : appInfos) {
            int iBoot = pm.checkPermission(permName, appInfo.packageName);
            if (iBoot == PackageManager.PERMISSION_GRANTED) {
                String appName = pm.getApplicationLabel(appInfo).toString();
                bootAppNames.add(appName);
                Logger.d("自启动APP名称：", appName + "\n ===flags====" + appInfo.flags);
            }
        }
        Logger.d("=============listBootApps自启动所有APP====================", bootAppNames.size() + "");
        return bootAppNames;
    }

    /**
     * 获取Android开机启动列表
     */
    public static List<Map<String, Object>> fetchInstalledApps(Context context) {
        PackageManager pm = context.getPackageManager();
        List<ApplicationInfo> appInfo = pm.getInstalledApplications(0);
        Iterator<ApplicationInfo> appInfoIterator = appInfo.iterator();
        List<Map<String, Object>> appList = new ArrayList<Map<String, Object>>(appInfo.size());

        while (appInfoIterator.hasNext()) {
            ApplicationInfo app = appInfoIterator.next();
            int flag = pm.checkPermission(permName, app.packageName);
            if (flag == PackageManager.PERMISSION_GRANTED) {
                Map<String, Object> appMap = new HashMap<String, Object>();
                String label = pm.getApplicationLabel(app).toString();
                Drawable icon = pm.getApplicationIcon(app);
                String desc = app.packageName;
                appMap.put("label", label);
                appMap.put("icon", icon);
                appMap.put("desc", desc);

                appList.add(appMap);
            }
        }
        Logger.d("=============fetchInstalledApps自启动所有APP====================", appList.size() + "");
        return appList;
    }
}