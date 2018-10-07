package com.jdry.noticemanagers.mvp.presenter;

import com.jdry.noticemanagers.bean.CommonBean;
import com.jdry.noticemanagers.mvp.model.ReadMsgDetailModel;
import com.jdry.noticemanagers.mvp.view.activity.ActivityDetail;

/**
 * Created by JDRY-SJM on 2018/2/4.
 */

public class ReadMsgDetailPresenter implements IPresenterCallback {

    private ReadMsgDetailModel readMsgModel;
    private ActivityDetail activityDetail;

    public ReadMsgDetailPresenter(ActivityDetail activityDetail) {
        this.activityDetail = activityDetail;
        this.readMsgModel = new ReadMsgDetailModel(this);
    }

    public void setUnReadMsgStatus(String msgParam) {
        activityDetail.showProgress();
        readMsgModel.setUnReadMsgStatus(msgParam);
    }

    @Override
    public <T> void httpRequestSuccess(T t, int order) {
        activityDetail.hideProgress();
        CommonBean commonBean = (CommonBean) t;
        if (commonBean.getData() == null) {
            activityDetail.httpFailure(commonBean.getMessage(), 0);
            return;
        }
        activityDetail.httpSuccess(t, 1);
    }

    @Override
    public void httpRequestFailure(String t, int order) {
        activityDetail.hideProgress();
        activityDetail.httpFailure("更新失败", 0);
    }
}
