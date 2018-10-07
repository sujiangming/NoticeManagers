package com.jdry.noticemanagers.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import cn.jpush.android.service.PushService;

public class BootBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //MainActivity就是开机显示的界面
        //Intent mBootIntent = new Intent(context, MainActivity.class);
        //下面这句话必须加上才能开机自动运行app的界面
        //mBootIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //context.startActivity(mBootIntent);
        Intent pushintent = new Intent(context, PushService.class);//启动极光推送的服务
        context.startService(pushintent);
    }
}
