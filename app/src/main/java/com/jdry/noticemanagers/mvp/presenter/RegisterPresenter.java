package com.jdry.noticemanagers.mvp.presenter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jdry.noticemanagers.global.JDRYApplication;
import com.jdry.noticemanagers.mvp.model.RegisterModel;
import com.jdry.noticemanagers.mvp.view.activity.RegisterActivity;

/**
 * Created by JDRY-SJM on 2018/2/4.
 */

public class RegisterPresenter implements IPresenterCallback {

    private RegisterModel registerModel;
    private RegisterActivity registerActivity;
    private String userData;

    public RegisterPresenter(RegisterActivity activity) {
        this.registerActivity = activity;
        this.registerModel = new RegisterModel(this);
    }

    public void register(String data) {
        registerActivity.showProgress();
        registerModel.register(data);
        userData = data;
    }

    @Override
    public <T> void httpRequestSuccess(T t, int order) {
        registerActivity.hideProgress();
        registerActivity.httpSuccess(t,2);
        JSONObject jsonObject = JSON.parseObject(userData);
        String mobilePhone = jsonObject.getString("mobilePhone");
        JDRYApplication.getInstance().setJPushTag(mobilePhone);

    }

    @Override
    public void httpRequestFailure(String t, int order) {
        registerActivity.hideProgress();
        registerActivity.httpFailure("注册失败",2);
    }
}
