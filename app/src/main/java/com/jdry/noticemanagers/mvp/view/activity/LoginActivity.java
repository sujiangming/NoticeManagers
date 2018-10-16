package com.jdry.noticemanagers.mvp.view.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jdry.noticemanagers.R;
import com.jdry.noticemanagers.global.JDRYConstant;
import com.jdry.noticemanagers.mvp.presenter.LoginPresenter;
import com.jdry.noticemanagers.utils.JDRYUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by JDRY-SJM on 2018/1/30.
 */

public class LoginActivity extends JDRYBaseActivity {
    @BindView(R.id.etUserName)
    EditText etUserName;
    @BindView(R.id.etPwd)
    EditText etPwd;
    @BindView(R.id.btnLogin)
    Button btnLogin;
    @BindView(R.id.tv_forget_pwd)
    TextView tvForgetPwd;

    @OnClick({R.id.btnLogin, R.id.tv_forget_pwd})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnLogin:
                login();
                break;
            case R.id.tv_forget_pwd:
                openNewActivity(RegisterActivity.class);
                break;
        }
    }

    private void login() {
        String userName = etUserName.getText().toString();
        if (null == userName || "".equals(userName)) {
            toast("请输入用户手机号");
            return;
        }

        if (!JDRYUtils.isMobile(userName)) {
            toast("请输入正确的手机号");
            return;
        }

        String pwd = etPwd.getText().toString();
        if (null == pwd || "".equals(pwd)) {
            toast("请输入密码");
            return;
        }
        new LoginPresenter(this, JDRYConstant.LOGIN_PAGE_LOGIN).login(userName, pwd);
    }

    @Override
    public int getResouceId() {
        return R.layout.activity_login;
    }

    @Override
    protected void onCreateByMe(Bundle savedInstanceState) {

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
}
