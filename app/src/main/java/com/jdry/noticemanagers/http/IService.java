package com.jdry.noticemanagers.http;

import com.jdry.noticemanagers.bean.CommonBean;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 * Created by JDRY_SJM on 2017/4/17.
 */

public interface IService {

    @FormUrlEncoded
    @POST("app/login")
    Call<CommonBean> login(@Field("data") String data);

    @GET("app/initDept")
    Call<CommonBean> getDeptInfo();

    @FormUrlEncoded
    @POST("app/register")
    Call<CommonBean> register(@Field("data") String data);

    @FormUrlEncoded
    @POST("app/api/notice/getUnReadNoticeByPhone")
    Call<CommonBean> getUnReadInfo(@Field("data") String data,@Field("token") String token);

    @FormUrlEncoded
    @POST("app/api/notice/getReadNoticeByPhone")
    Call<CommonBean> getReadInfo(@Field("data") String data,@Field("token") String token);

    @FormUrlEncoded
    @POST("app/api/notice/readNotice")
    Call<CommonBean> setUnReadMsgStatus(@Field("data") String data,@Field("token") String token);

    @FormUrlEncoded
    @POST("app/api/changePassword")
    Call<CommonBean> updatePwd(@Field("data") String data,@Field("token") String token);

    /**
     * 上传图片
     *
     * @param multipartBody
     * @return
     */
    @POST
    Call<ResponseBody> upload(@Url() String url, @Body MultipartBody multipartBody);

    @FormUrlEncoded
    @POST("http://192.168.1.29/jdryweb/app/api/version/checkVersion")
    Call<CommonBean> checkVersion(@Field("data") String data);
}
