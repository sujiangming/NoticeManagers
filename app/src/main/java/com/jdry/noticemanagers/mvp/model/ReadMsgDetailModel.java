package com.jdry.noticemanagers.mvp.model;

import com.alibaba.fastjson.JSONObject;
import com.jdry.noticemanagers.bean.CommonBean;
import com.jdry.noticemanagers.global.JDRYConstant;
import com.jdry.noticemanagers.http.IService;
import com.jdry.noticemanagers.http.RetrofitUtil;
import com.jdry.noticemanagers.mvp.presenter.IPresenterCallback;
import com.jdry.noticemanagers.utils.JDRYUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by JDRY-SJM on 2018/2/4.
 */

public class ReadMsgDetailModel {

    private IPresenterCallback presenter;

    public ReadMsgDetailModel(IPresenterCallback iPresenterCallback) {
        this.presenter = iPresenterCallback;
    }

    public void setUnReadMsgStatus(String userName) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", userName);
        RetrofitUtil.getInstance()
                .createReq(IService.class)
                .setUnReadMsgStatus(jsonObject.toJSONString(), JDRYUtils.getToken())
                .enqueue(new Callback<CommonBean>() {
                    @Override
                    public void onResponse(Call<CommonBean> call, Response<CommonBean> response) {
                        if (!response.isSuccessful()) {
                            presenter.httpRequestFailure(response.message(), JDRYConstant.INVOKE_API_DEFAULT_TIME);
                            return;
                        }
                        if (response.body() == null) {
                            presenter.httpRequestFailure(response.message(), JDRYConstant.INVOKE_API_DEFAULT_TIME);
                            return;
                        }
                        CommonBean commonBean = response.body();
                        if (commonBean.getStatus() != 1) {
                            presenter.httpRequestFailure(response.message(), JDRYConstant.INVOKE_API_DEFAULT_TIME);
                            return;
                        }

                        presenter.httpRequestSuccess(commonBean,1);
                    }

                    @Override
                    public void onFailure(Call<CommonBean> call, Throwable t) {
                        presenter.httpRequestFailure(t.getMessage(), JDRYConstant.INVOKE_API_DEFAULT_TIME);
                    }
                });
    }
}
