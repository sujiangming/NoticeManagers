package com.jdry.noticemanagers.global;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;

import com.jdry.noticemanagers.bean.DaoMaster;
import com.jdry.noticemanagers.bean.DaoSession;
import com.jdry.noticemanagers.utils.JDRYActivityManager;
import com.jdry.noticemanagers.utils.Logger;

import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * Created by JDRY-SJM on 2018/1/29.
 */

public class JDRYApplication extends Application {
    private static JDRYApplication instance;
    private static DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        CrashHandler.instance().init();
        //JPushInterface.setDebugMode(true);    // 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);            // 初始化 JPush
        initAppManager();
        setupGreenDao();
    }

    public static JDRYApplication getInstance() {
        return instance;
    }

    /**
     * 初始化管理Activity工具类
     */
    private void initAppManager() {
        JDRYActivityManager.getAppManager();
    }

    /**
     * 配置数据库GreenDao
     */
    private void setupGreenDao() {
        //创建数据库yxx.db （创建SQLite数据库的SQLiteOpenHelper的具体实现）
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "jy.db", null);
        //获取可写数据库
        SQLiteDatabase db = helper.getWritableDatabase();
        //获取数据库对象（GreenDao的顶级对象，作为数据库对象、用于创建表和删除表）
        DaoMaster daoMaster = new DaoMaster(db);
        //获取dao对象管理者（管理所有的Dao对象，Dao对象中存在着增删改查等API）
        daoSession = daoMaster.newSession();
    }

    public static DaoSession getDaoSession() {
        return daoSession;
    }

    public void setJPushTag(String tag) {
//        Set<String> tagSet = new LinkedHashSet<String>();
//        tagSet.add(tag);
//        JPushInterface.setTags(this, tagSet, mTagsCallback);
        // 调用 Handler 来异步设置别名
        mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS, tag));
    }

    private static final int MSG_SET_ALIAS = 1001;
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_SET_ALIAS:
                    Logger.d(JDRYApplication.class.getName(), "Set alias in handler.");
                    // 调用 JPush 接口来设置别名。
                    JPushInterface.setAliasAndTags(getApplicationContext(),
                            (String) msg.obj,
                            null,
                            mTagsCallback);
                    break;
                default:
                    Logger.d(JDRYApplication.class.getName(), "Unhandled msg - " + msg.what);
            }
        }
    };

    private final TagAliasCallback mTagsCallback = new TagAliasCallback() {
        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs;
            switch (code) {
                case 0:
                    logs = "Set tag and alias success";
                    Logger.d(JDRYApplication.class.getName(), logs);
                    // 建议这里往 SharePreference 里写一个成功设置的状态。成功设置一次后，以后不必再次设置了。
                    setSharedPreferences(getApplicationContext(),"alias",alias,1);
                    break;
                case 6002:
                    logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
                    Logger.d(JDRYApplication.class.getName(), logs);
                    // 延迟 60 秒来调用 Handler 设置别名
                    mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_ALIAS, alias), 1000 * 60);
                    break;
                default:
                    logs = "Failed with errorCode = " + code;
                    Logger.d(JDRYApplication.class.getName(), logs);
            }
        }
    };

    public static void setSharedPreferences(Context context, String objName,  String strObject,int flag) {
        SharedPreferences sp = context.getSharedPreferences(objName, flag);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(objName, strObject);
        edit.commit();
    }

    public static String getSharedPreferences(Context context, String objName, int flag) {
        SharedPreferences sp = context.getSharedPreferences(objName, flag);
        return sp.getString(objName, null);
    }
}
