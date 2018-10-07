package com.jdry.noticemanagers.mvp.presenter;

import com.jdry.noticemanagers.bean.CommonBean;
import com.jdry.noticemanagers.global.JDRYApplication;
import com.jdry.noticemanagers.global.JDRYConstant;
import com.jdry.noticemanagers.mvp.model.LoginModel;
import com.jdry.noticemanagers.mvp.view.activity.JDRYBaseActivity;
import com.jdry.noticemanagers.mvp.view.activity.MainActivity;
import com.jdry.noticemanagers.utils.MD5Util;

/**
 * Created by JDRY-SJM on 2018/2/4.
 */

public class LoginPresenter implements IPresenterCallback {

    private LoginModel loginModel;
    private JDRYBaseActivity loginActivity;
    private int invokeIndex;
    private String mobilPhone;

    public LoginPresenter(JDRYBaseActivity activity, int invokeIndex) {
        this.loginActivity = activity;
        this.invokeIndex = invokeIndex;
        this.loginModel = new LoginModel(this);
    }

    public void login(String username, String pwd) {
        loginActivity.showProgress();
        String tt = MD5Util.encrypt(pwd);
        loginModel.getLogin(username, tt);
        mobilPhone = username;
    }

    @Override
    public <T> void httpRequestSuccess(T t, int order) {
        loginActivity.hideProgress();
        CommonBean commonBean = (CommonBean) t;
        switch (invokeIndex) {
            case JDRYConstant.LOGIN_PAGE_LOGIN:
                JDRYApplication.getInstance().setJPushTag(mobilPhone);
                loginActivity.toast(commonBean.getMessage());
                loginActivity.openNewActivity(MainActivity.class);
                break;
            case JDRYConstant.LAUNCHER_PAGE_LOGIN:
                loginActivity.toast("欢迎回来~");
                loginActivity.openNewActivity(MainActivity.class);
                break;
        }
    }

    @Override
    public void httpRequestFailure(String t, int order) {
        loginActivity.hideProgress();
        switch (invokeIndex) {
            case JDRYConstant.LOGIN_PAGE_LOGIN:
                loginActivity.toast("登录失败~");
                break;
            case JDRYConstant.LAUNCHER_PAGE_LOGIN:
                loginActivity.openNewActivity(MainActivity.class);//表示从启动页面获取个人信息失败
                break;
        }
    }
}
