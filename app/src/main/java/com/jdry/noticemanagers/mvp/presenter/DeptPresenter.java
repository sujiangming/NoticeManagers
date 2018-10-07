package com.jdry.noticemanagers.mvp.presenter;

import com.jdry.noticemanagers.mvp.model.DeptModel;
import com.jdry.noticemanagers.mvp.view.activity.RegisterActivity;

/**
 * Created by JDRY-SJM on 2018/2/4.
 */

public class DeptPresenter implements IPresenterCallback {

    private DeptModel deptModel;
    private RegisterActivity registerActivity;

    public DeptPresenter(RegisterActivity activity) {
        this.registerActivity = activity;
        this.deptModel = new DeptModel(this);
    }

    public void getDept() {
        registerActivity.showProgress();
        deptModel.getDeptInfo();
    }

    @Override
    public <T> void httpRequestSuccess(T t, int order) {
        registerActivity.hideProgress();
        registerActivity.httpSuccess(t, 1);
    }

    @Override
    public void httpRequestFailure(String t, int order) {
        registerActivity.hideProgress();
        registerActivity.httpFailure(t, 1);
    }
}
