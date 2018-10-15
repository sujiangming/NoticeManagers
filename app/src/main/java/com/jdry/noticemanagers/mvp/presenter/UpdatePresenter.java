package com.jdry.noticemanagers.mvp.presenter;

import com.jdry.noticemanagers.mvp.model.UpdatePwdModel;
import com.jdry.noticemanagers.mvp.view.activity.UpdatePwdActivity;

/**
 * Created by JDRY-SJM on 2018/2/4.
 */

public class UpdatePresenter implements IPresenterCallback {

    private UpdatePwdModel registerModel;
    private UpdatePwdActivity updatePwdActivity;

    public UpdatePresenter(UpdatePwdActivity activity) {
        this.updatePwdActivity = activity;
        this.registerModel = new UpdatePwdModel(this);
    }

    public void register(String data) {
        updatePwdActivity.showProgress();
        registerModel.register(data);
    }

    @Override
    public <T> void httpRequestSuccess(T t, int order) {
        updatePwdActivity.hideProgress();
        updatePwdActivity.httpSuccess(t,2);
    }

    @Override
    public void httpRequestFailure(String t, int order) {
        updatePwdActivity.hideProgress();
        updatePwdActivity.httpFailure(t,2);
    }
}
