package com.jdry.noticemanagers.mvp.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jdry.noticemanagers.bean.CommonBean;
import com.jdry.noticemanagers.bean.LoginInfoBean;
import com.jdry.noticemanagers.global.JDRYApplication;
import com.jdry.noticemanagers.global.JDRYConstant;
import com.jdry.noticemanagers.http.IService;
import com.jdry.noticemanagers.http.RetrofitUtil;
import com.jdry.noticemanagers.mvp.presenter.IPresenterCallback;
import com.jdry.noticemanagers.utils.MD5Util;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by JDRY-SJM on 2018/2/4.
 */

public class LoginModel {

    private IPresenterCallback presenter;

    public LoginModel(IPresenterCallback iPresenterCallback) {
        this.presenter = iPresenterCallback;
    }

    public void getLogin(String userName, final String password) {
        String tt = MD5Util.encrypt(password);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("mobilePhone", userName);
        jsonObject.put("password", tt);
        RetrofitUtil.getInstance()
                .createReq(IService.class)
                .login(jsonObject.toJSONString())
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
                            presenter.httpRequestSuccess(commonBean, JDRYConstant.HTTP_COMMON_STATUS_FAIL);
                            return;
                        }

                        if (null == commonBean.getData()) {
                            presenter.httpRequestSuccess(commonBean, JDRYConstant.HTTP_COMMON_DATA_EMPTY);
                            return;
                        }

                        LoginInfoBean loginInfoBean = JSON.parseObject(commonBean.getData().toString(), LoginInfoBean.class);
                        loginInfoBean.setId(1l);
                        loginInfoBean.setPassword(password);
                        JDRYApplication.getDaoSession().getLoginInfoBeanDao().insertOrReplace(loginInfoBean);

                        presenter.httpRequestSuccess(commonBean, 0);
                    }

                    @Override
                    public void onFailure(Call<CommonBean> call, Throwable t) {
                        presenter.httpRequestFailure(t.getMessage(), JDRYConstant.INVOKE_API_DEFAULT_TIME);
                    }
                });
    }

}
