package com.jdry.noticemanagers.mvp.presenter;

import com.jdry.noticemanagers.http.IService;
import com.jdry.noticemanagers.http.RetrofitUtil;
import com.jdry.noticemanagers.mvp.view.IView;

import java.io.File;
import java.io.IOException;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by JDRY-SJM on 2018/2/25.
 */

public class UploadFilePresenter {

    private IView mIView;
    private String uploadFailureInfo = "上传失败~";

    public UploadFilePresenter(IView view) {
        this.mIView = view;
    }

    /**
     * 这种上传方式支持单张图片和多张图片，同时也支持上传除了图片类的其他文件
     *
     * @param files
     * @return
     */
    public void upload(List<File> files) {
        mIView.showProgress();
        MultipartBody multipartBody = filesToMultipartBody(files);
        String urlPrefix = "http://lwgj.gzjdry.com:8080/PMS/fileUpload.app?method=upload&folder=forum";
        // 执行请求
        Call<ResponseBody> call = RetrofitUtil.getInstance().createReq(IService.class).upload(urlPrefix, multipartBody);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                mIView.hideProgress();
                try {
                    if (!response.isSuccessful()) {
                        mIView.uploadFailure(uploadFailureInfo);
                        return;
                    }
                    if (null == response.body()) {
                        mIView.uploadFailure(uploadFailureInfo);
                        return;
                    }
                    mIView.uploadSuccess(response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                mIView.hideProgress();
                mIView.uploadFailure(uploadFailureInfo);
            }
        });
    }

    public MultipartBody filesToMultipartBody(List<File> files) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        for (File file : files) {
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            builder.addFormDataPart("img", file.getName(), requestBody);
        }

        builder.setType(MultipartBody.FORM);
        MultipartBody multipartBody = builder.build();
        return multipartBody;
    }
}
