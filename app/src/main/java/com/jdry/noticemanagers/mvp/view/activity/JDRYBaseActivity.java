package com.jdry.noticemanagers.mvp.view.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.jdry.noticemanagers.mvp.view.IView;
import com.jdry.noticemanagers.mvp.view.custom.SjmProgressBar;
import com.jdry.noticemanagers.mvp.view.custom.TopBarView;
import com.jdry.noticemanagers.utils.JDRYActivityManager;
import com.jdry.noticemanagers.utils.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by JDRY_SJM on 2017/4/17.
 */

public abstract class JDRYBaseActivity extends AppCompatActivity implements IView,EasyPermissions.PermissionCallbacks{
    public abstract int getResouceId();

    protected abstract void onCreateByMe(Bundle savedInstanceState);

    private Unbinder unbinder;

    public JDRYActivityManager appManager = JDRYActivityManager.getAppManager();

    private SjmProgressBar jdryProgressBar;

    /**
     * 设置状态栏透明
     */
    public void setStatusBarTransparent() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    /**
     * 设置状态栏透明
     */
    public void setStatusBarColor(int colorRes) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(colorRes);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置topbar共同的方法
     *
     * @param topBar
     * @param title
     * @param color
     */
    public void setTopBar(TopBarView topBar, String title, int color) {
        topBar.getTitleView().setText(title);
        if (color != 0) {
            topBar.getTitleView().setTextColor(color);
        }
        topBar.getBackView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeActivity(JDRYBaseActivity.this);
            }
        });
    }

    public void setTextViewValue(TextView textView, String value) {
        if (!TextUtils.isEmpty(value)) {
            textView.setText(value);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getResouceId());
        unbinder = ButterKnife.bind(this);
        onCreateByMe(savedInstanceState);
        appManager.addActivity(this);
    }

    public void openNewActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    public void openNewActivityByIntent(Class<?> cls, Intent intent) {
        if (null == intent) {
            return;
        }
        intent.setClass(this, cls);
        startActivity(intent);
    }

    public void openNewActivity(Class<?> cls) {
        openNewActivity(cls, null);
    }

    public void closeActivity() {
        appManager.finishActivity();
    }

    public void closeActivity(Activity activity) {
        appManager.finishActivity(activity);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        closeActivity();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void showProgress() {
        if (null == this.jdryProgressBar) {
            jdryProgressBar = SjmProgressBar.show(this);
        } else {
            jdryProgressBar.show();
        }
    }

    @Override
    public void hideProgress() {
        if (null != jdryProgressBar && jdryProgressBar.isShowing()) {
            jdryProgressBar.dismiss();
        }
    }

    @Override
    public <T> void httpSuccess(T t, int order) {

    }

    @Override
    public <T> void httpFailure(T t, int order) {

    }

    @Override
    public void uploadSuccess(String filePath) {

    }

    @Override
    public void uploadFailure(String msg) {

    }

    public void toast(String desc) {
        ToastUtils.toast(this, desc);
    }

    public void refresh() {
        stopRefreshLayout();
    }

    public void loadMore() {
        stopRefreshLayoutLoadMore();
    }

    private RefreshLayout mRefreshLayout;

    public void initSmartRefreshLayout(SmartRefreshLayout smartRefreshLayout, boolean isLoadMore) {
        smartRefreshLayout.setRefreshHeader(new ClassicsHeader(this));
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                setmRefreshLayout(refreshlayout);
                refresh();
            }
        });
        if (isLoadMore) {
            smartRefreshLayout.setRefreshFooter(new ClassicsFooter(this));
            smartRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
                @Override
                public void onLoadmore(RefreshLayout refreshlayout) {
                    setmRefreshLayout(refreshlayout);
                    loadMore();
                }
            });
        }
    }

    public void stopLayoutRefreshByTag(boolean isLoadMore) {
        if (isLoadMore) {
            stopRefreshLayoutLoadMore();
        } else {
            stopRefreshLayout();
        }
    }

    public void stopRefreshLayout() {
        if (mRefreshLayout != null) {
            mRefreshLayout.finishRefresh();
        }
    }

    public void stopRefreshLayoutLoadMore() {
        if (mRefreshLayout != null) {
            mRefreshLayout.finishLoadmore();
        }
    }

    public RefreshLayout getmRefreshLayout() {
        return mRefreshLayout;
    }

    public void setmRefreshLayout(RefreshLayout mRefreshLayout) {
        if (this.mRefreshLayout == null) {
            this.mRefreshLayout = mRefreshLayout;
        }
    }

    private static final int REQUEST_CODE_QRCODE_PERMISSIONS = 1;

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
    }

    @AfterPermissionGranted(REQUEST_CODE_QRCODE_PERMISSIONS)
    public void requestCodeQRCodePermissions() {
        String[] perms = {
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.RECEIVE_BOOT_COMPLETED,
                Manifest.permission.BROADCAST_STICKY,
                Manifest.permission.CALL_PHONE
        };
        if (!EasyPermissions.hasPermissions(this, perms)) {
            EasyPermissions.requestPermissions(this, "需要打开相机和打电话的权限", REQUEST_CODE_QRCODE_PERMISSIONS, perms);
        }
    }
}
