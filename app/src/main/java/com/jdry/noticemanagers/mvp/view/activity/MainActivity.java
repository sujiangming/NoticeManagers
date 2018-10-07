package com.jdry.noticemanagers.mvp.view.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jdry.noticemanagers.R;
import com.jdry.noticemanagers.mvp.view.fragment.FragmentRead;
import com.jdry.noticemanagers.mvp.view.fragment.FragmentUnRead;
import com.jdry.noticemanagers.mvp.view.fragment.FragmentUser;
import com.jdry.noticemanagers.utils.ExampleUtil;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;

public class MainActivity extends JDRYBaseActivity {

    @BindView(R.id.iv_read)
    ImageView ivRead;
    @BindView(R.id.tv_read)
    TextView tvRead;
    @BindView(R.id.iv_unread)
    ImageView ivUnRead;
    @BindView(R.id.tv_unread)
    TextView tvUnRead;
    @BindView(R.id.iv_user)
    ImageView ivUser;
    @BindView(R.id.tv_user)
    TextView tvUser;

    @OnClick({R.id.ll_unread, R.id.ll_read, R.id.ll_user})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_unread:
                changeStyle(view, 0);
                break;
            case R.id.ll_read:
                changeStyle(view, 1);
                break;
            case R.id.ll_user:
                changeStyle(view, 2);
                break;
        }
    }

    private FragmentManager fragmentManager;
    private FragmentRead fragmentRead;
    private FragmentUnRead fragmentUnRead;
    private FragmentUser fragmentUser;
    private ImageView[] imageViews = new ImageView[3];
    private TextView[] textViews = new TextView[3];

    private int[] imageViewNormalRes = {R.drawable.weidu, R.drawable.yidu, R.drawable.user};
    private int[] imageViewChangeRes = {R.drawable.weidu_press, R.drawable.yidu_press, R.drawable.user_press};

    private int index = 0;

    @Override
    public int getResouceId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreateByMe(Bundle savedInstanceState) {
        fragmentManager = getSupportFragmentManager();
        initFragments();
        registerMessageReceiver();  // used for receive msg
    }

    private void initFragments() {
        initImageViews(); //初始化imageview数组
        initTextViews();
        changeFragment(index);//显示主页
    }

    private void initImageViews() {
        imageViews[0] = ivUnRead;
        imageViews[1] = ivRead;
        imageViews[2] = ivUser;
    }

    private void initTextViews() {
        textViews[0] = tvUnRead;
        textViews[1] = tvRead;
        textViews[2] = tvUser;
    }

    private void changeFragment(int indexTmp) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragments(transaction);
        switch (indexTmp) {
            case 0:
                if (null == fragmentUnRead) {
                    fragmentUnRead = new FragmentUnRead();
                    transaction.add(R.id.ll_fragment_container, fragmentUnRead);
                } else {
                    transaction.show(fragmentUnRead);
                }
                break;
            case 1:
                if (null == fragmentRead) {
                    fragmentRead = new FragmentRead();
                    transaction.add(R.id.ll_fragment_container, fragmentRead);
                } else {
                    transaction.show(fragmentRead);
                }
                break;
            case 2:
                if (null == fragmentUser) {
                    fragmentUser = new FragmentUser();
                    transaction.add(R.id.ll_fragment_container, fragmentUser);
                } else {
                    transaction.show(fragmentUser);
                }
                break;
        }
        transaction.commit();
    }

    private void hideFragments(FragmentTransaction transaction) {
        if (null != fragmentUnRead) {
            transaction.hide(fragmentUnRead);
        }
        if (null != fragmentRead) {
            transaction.hide(fragmentRead);
        }
        if (null != fragmentUser) {
            transaction.hide(fragmentUser);
        }
    }

    private void changeStyle(View view, int tag) {
        changeNavStyle(view);
        changeIndexTextViewColor(tag);
    }

    private void changeNavStyle(View view) {
        String tag = (String) view.getTag();
        index = Integer.parseInt(tag);
        changeFragment(index);
        changeImageViewRes(index);
    }

    private void changeIndexTextViewColor(int index) {
        textViews[index].setTextColor(0xFF209E85);
        for (int i = 0; i < textViews.length; ++i) {
            if (i != index) {
                textViews[i].setTextColor(0xFF878787);
            }
        }
    }

    private void changeImageViewRes(int index) {
        imageViews[index].setImageResource(imageViewChangeRes[index]);
        int len = imageViews.length;
        for (int i = 0; i < len; ++i) {
            if (i != index) {
                imageViews[i].setImageResource(imageViewNormalRes[i]);
            }
        }
    }

    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            //两秒之内按返回键就会退出
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                toast("再按一次退出程序");
                exitTime = System.currentTimeMillis();
            } else {
                appManager.finishAllActivity();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public static boolean isForeground = false;

    // 初始化 JPush。如果已经初始化，但没有登录成功，则执行重新登录。
    private void init() {
        JPushInterface.init(getApplicationContext());
    }

    @Override
    protected void onResume() {
        isForeground = true;
        super.onResume();
    }

    @Override
    protected void onPause() {
        isForeground = false;
        super.onPause();
    }


    @Override
    protected void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        super.onDestroy();
    }


    //for receive customer msg from jpush server
    private MessageReceiver mMessageReceiver;
    public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";

    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, filter);
    }

    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                    String messge = intent.getStringExtra(KEY_MESSAGE);
                    String extras = intent.getStringExtra(KEY_EXTRAS);
                    StringBuilder showMsg = new StringBuilder();
                    showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
                    if (!ExampleUtil.isEmpty(extras)) {
                        showMsg.append(KEY_EXTRAS + " : " + extras + "\n");
                    }
                    setCostomMsg(showMsg.toString());
                }
            } catch (Exception e) {
            }
        }
    }

    private void setCostomMsg(String msg) {
//        if (null != msgText) {
//            msgText.setText(msg);
//            msgText.setVisibility(android.view.View.VISIBLE);
//        }
        toast(msg);
    }
}
