package com.jdry.noticemanagers.mvp.presenter;

import com.jdry.noticemanagers.bean.CommonBean;
import com.jdry.noticemanagers.global.JDRYApplication;
import com.jdry.noticemanagers.global.JDRYConstant;
import com.jdry.noticemanagers.mvp.model.LoginModel;
import com.jdry.noticemanagers.mvp.view.activity.JDRYBaseActivity;
import com.jdry.noticemanagers.mvp.view.activity.MainActivity;

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
        loginModel.getLogin(username, pwd);
        mobilPhone = username;
    }

    @Override
    public <T> void httpRequestSuccess(T t, int order) {
        loginActivity.hideProgress();
        CommonBean commonBean = (CommonBean) t;
        if (invokeIndex == JDRYConstant.LOGIN_PAGE_LOGIN) {
            switch (order) {
                case JDRYConstant.HTTP_COMMON_STATUS_FAIL:
                    loginActivity.toast(commonBean.getMessage());
                    break;
                case JDRYConstant.HTTP_COMMON_DATA_EMPTY:
                    loginActivity.toast(commonBean.getMessage());
                    break;
                default:
                    loginActivity.toast(commonBean.getMessage());
                    JDRYApplication.getInstance().setJPushTag(mobilPhone);
                    loginActivity.openNewActivity(MainActivity.class);
                    break;
            }
        } else {
            loginActivity.openNewActivity(MainActivity.class);
        }
    }

    @Override
    public void httpRequestFailure(String t, int order) {
        loginActivity.hideProgress();
        switch (invokeIndex) {
            case JDRYConstant.LOGIN_PAGE_LOGIN:
                loginActivity.toast(t);
                break;
            case JDRYConstant.LAUNCHER_PAGE_LOGIN:
                loginActivity.openNewActivity(MainActivity.class);//表示从启动页面获取个人信息失败
                break;
        }
    }
}
