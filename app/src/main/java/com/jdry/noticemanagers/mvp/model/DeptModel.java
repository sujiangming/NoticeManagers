package com.jdry.noticemanagers.mvp.model;

import com.alibaba.fastjson.JSON;
import com.jdry.noticemanagers.bean.CommonBean;
import com.jdry.noticemanagers.bean.DeptBean;
import com.jdry.noticemanagers.global.JDRYConstant;
import com.jdry.noticemanagers.http.IService;
import com.jdry.noticemanagers.http.RetrofitUtil;
import com.jdry.noticemanagers.mvp.presenter.IPresenterCallback;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by JDRY-SJM on 2018/2/4.
 */

public class DeptModel {

    private IPresenterCallback presenter;

    public DeptModel(IPresenterCallback iPresenterCallback) {
        this.presenter = iPresenterCallback;
    }

    public void getDeptInfo() {
        RetrofitUtil.getInstance()
                .createReq(IService.class)
                .getDeptInfo()
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

                        if (null == commonBean.getData()) {
                            presenter.httpRequestFailure(response.message(), JDRYConstant.INVOKE_API_DEFAULT_TIME);
                            return;
                        }

                        List<DeptBean> deptBean = JSON.parseArray(commonBean.getData().toString(), DeptBean.class);
                        presenter.httpRequestSuccess(deptBean, 1);
                    }

                    @Override
                    public void onFailure(Call<CommonBean> call, Throwable t) {
                        presenter.httpRequestFailure(t.getMessage(), JDRYConstant.INVOKE_API_DEFAULT_TIME);
                    }
                });
    }

}
