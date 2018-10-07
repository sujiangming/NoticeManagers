package com.jdry.noticemanagers.mvp.view.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.jdry.noticemanagers.R;
import com.jdry.noticemanagers.bean.LoginInfoBean;
import com.jdry.noticemanagers.global.JDRYApplication;

/**
 * Created by jdry on 2016/11/25.
 */
public class LauncherActivity extends JDRYBaseActivity {

    @Override
    public int getResouceId() {
        return R.layout.activity_launcher;
    }

    @Override
    protected void onCreateByMe(Bundle savedInstanceState) {
        initTimeHandler();
    }

    @Override
    protected void onStart() {
        super.onStart();
        requestCodeQRCodePermissions();
    }

    private void initTimeHandler() {
        Message message1 = handlerTime.obtainMessage(1);     // Message
        handlerTime.sendMessageDelayed(message1, 1000);
    }

    private int recLen = 3;
    private Handler handlerTime = new Handler() {

        public void handleMessage(Message msg) {         // handle message
            switch (msg.what) {
                case 1:
                    --recLen;
                    if (recLen > 0) {
                        Message message = handlerTime.obtainMessage(1);
                        handlerTime.sendMessageDelayed(message, 1000);      // send message
                    } else {
                        autoLogin();
                    }
            }
            super.handleMessage(msg);
        }
    };

    //检查用户是否已经登录
    private void autoLogin() {
        LoginInfoBean loginInfoBean = JDRYApplication.getDaoSession().getLoginInfoBeanDao().queryBuilder().unique();
        if (loginInfoBean == null || TextUtils.isEmpty(loginInfoBean.getMobilePhone())) {
            openNewActivity(LoginActivity.class);
            return;
        }
        openNewActivity(MainActivity.class);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handlerTime.removeCallbacksAndMessages(null);
        handlerTime = null;
    }

}
