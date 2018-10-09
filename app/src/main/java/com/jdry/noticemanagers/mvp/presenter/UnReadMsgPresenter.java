package com.jdry.noticemanagers.mvp.presenter;

import com.alibaba.fastjson.JSON;
import com.jdry.noticemanagers.bean.CommonBean;
import com.jdry.noticemanagers.bean.ReadMsgBean;
import com.jdry.noticemanagers.mvp.model.UnReadMsgModel;
import com.jdry.noticemanagers.mvp.view.fragment.FragmentUnRead;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JDRY-SJM on 2018/2/4.
 */

public class UnReadMsgPresenter implements IPresenterCallback {

    private UnReadMsgModel unReadMsgModel;
    private FragmentUnRead fragmentUnRead;

    public UnReadMsgPresenter(FragmentUnRead fragmentUnRead) {
        this.fragmentUnRead = fragmentUnRead;
        this.unReadMsgModel = new UnReadMsgModel(this);
    }

    public void getUnReadMsg(String msgParam) {
        fragmentUnRead.showProgress();
        unReadMsgModel.getUnReadMsg(msgParam);
    }

    @Override
    public <T> void httpRequestSuccess(T t, int order) {
        fragmentUnRead.hideProgress();
        CommonBean commonBean = (CommonBean) t;
        if (commonBean == null) {
            fragmentUnRead.toast("没有最新的信息");
            fragmentUnRead.httpSuccess(new ArrayList<ReadMsgBean>(), order);
            return;
        }
        List<ReadMsgBean> list = JSON.parseArray(commonBean.getData().toString(), ReadMsgBean.class);
        fragmentUnRead.httpSuccess(list, order);
    }

    @Override
    public void httpRequestFailure(String t, int order) {
        fragmentUnRead.hideProgress();
        fragmentUnRead.toast(t);
        fragmentUnRead.httpFailure(t,order);
    }
}
