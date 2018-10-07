package com.jdry.noticemanagers.mvp.presenter;

import com.jdry.noticemanagers.bean.CommonBean;
import com.jdry.noticemanagers.mvp.model.ReadMsgModel;
import com.jdry.noticemanagers.mvp.view.fragment.FragmentRead;

/**
 * Created by JDRY-SJM on 2018/2/4.
 */

public class ReadMsgPresenter implements IPresenterCallback {

    private ReadMsgModel readMsgModel;
    private FragmentRead fragmentRead;

    public ReadMsgPresenter(FragmentRead fragmentRead) {
        this.fragmentRead = fragmentRead;
        this.readMsgModel = new ReadMsgModel(this);
    }

    public void getReadMsg(String msgParam) {
        fragmentRead.showProgress();
        readMsgModel.getReadMsg(msgParam);
    }

    @Override
    public <T> void httpRequestSuccess(T t, int order) {
        fragmentRead.hideProgress();
        CommonBean commonBean = (CommonBean) t;
        if (commonBean.getData() == null) {
            fragmentRead.httpFailure("没有最新数据", 0);
            return;
        }
        fragmentRead.httpSuccess(t, 1);
    }

    @Override
    public void httpRequestFailure(String t, int order) {
        fragmentRead.hideProgress();
        fragmentRead.httpFailure("没有最新数据", 0);
    }
}
