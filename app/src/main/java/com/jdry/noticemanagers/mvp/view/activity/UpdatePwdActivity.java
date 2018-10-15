package com.jdry.noticemanagers.mvp.view.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import com.alibaba.fastjson.JSONObject;
import com.jdry.noticemanagers.R;
import com.jdry.noticemanagers.mvp.presenter.UpdatePresenter;
import com.jdry.noticemanagers.mvp.view.custom.TopBarView;
import com.jdry.noticemanagers.utils.MD5Util;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by JDRY-SJM on 2018/1/30.
 */

public class UpdatePwdActivity extends JDRYBaseActivity {

    @BindView(R.id.top_bar)
    TopBarView topBar;
    @BindView(R.id.et_user_phone)
    EditText etUserPhone;
    @BindView(R.id.et_user_name)
    EditText etUserName;
    @BindView(R.id.et_user_pwd)
    EditText etUserPwd;
    @BindView(R.id.btnRegister)
    Button btnRegister;

    @OnClick(R.id.btnRegister)
    public void onViewClicked() {

        String phone = etUserPhone.getText().toString();

        if (TextUtils.isEmpty(phone)) {
            toast("请输入手机号");
            return;
        }

        String userName = etUserName.getText().toString();

        if (TextUtils.isEmpty(userName)) {
            toast("请输入原始密码");
            return;
        }

        String pwd = etUserPwd.getText().toString();

        if (TextUtils.isEmpty(pwd)) {
            toast("请输入新密码");
            return;
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("mobilePhone", phone);
        jsonObject.put("password", MD5Util.encrypt(userName));
        jsonObject.put("newPassword", MD5Util.encrypt(pwd));

        registerPresenter.register(jsonObject.toJSONString());
    }

    private UpdatePresenter registerPresenter;

    @Override
    public int getResouceId() {
        return R.layout.activity_update;
    }

    @Override
    protected void onCreateByMe(Bundle savedInstanceState) {
        setTopBar(topBar, "修改密码", 0);
        registerPresenter = new UpdatePresenter(this);
    }

    @Override
    public <T> void httpSuccess(T t, int order) {
        toast((String) t);
        openNewActivity(LoginActivity.class);
        closeActivity();
    }

    @Override
    public <T> void httpFailure(T t, int order) {
        toast((String) t);
    }
}
