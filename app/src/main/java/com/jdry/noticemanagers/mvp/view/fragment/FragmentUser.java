package com.jdry.noticemanagers.mvp.view.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.jdry.noticemanagers.R;
import com.jdry.noticemanagers.bean.LoginInfoBean;
import com.jdry.noticemanagers.global.JDRYApplication;
import com.jdry.noticemanagers.mvp.view.activity.AboutUsActivity;
import com.jdry.noticemanagers.mvp.view.activity.LoginActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class FragmentUser extends JDRYBaseFragment {


    @BindView(R.id.tv_account_name)
    TextView tvAccountName;
    @BindView(R.id.tv_account_pos)
    TextView tvAccountPos;

    @OnClick({R.id.rl_info, R.id.ll_help_center, R.id.ll_set})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_info:
                break;
            case R.id.ll_help_center:
                openNewActivity(AboutUsActivity.class, null);
                break;
            case R.id.ll_set:
                openNewActivity(LoginActivity.class, null);
                break;
        }
    }

    @Override
    public int getResourceId() {
        return R.layout.fragment_user;
    }

    @Override
    protected void onCreateViewByMe(Bundle savedInstanceState) {
        initData();
    }

    private void initData() {
        LoginInfoBean loginInfoBean = JDRYApplication.getDaoSession().getLoginInfoBeanDao().queryBuilder().unique();
        if (loginInfoBean == null) {
            return;
        }
        if (!TextUtils.isEmpty(loginInfoBean.getName())) {
            tvAccountName.setText("姓名：" + loginInfoBean.getName());
        }

        if (!TextUtils.isEmpty(loginInfoBean.getDeptName())) {
            tvAccountPos.setText("部门：" + loginInfoBean.getDeptName());
        }
    }
}
