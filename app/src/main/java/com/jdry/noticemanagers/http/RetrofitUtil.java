package com.jdry.noticemanagers.http;

import com.jdry.noticemanagers.BuildConfig;
import com.jdry.noticemanagers.global.JDRYConstant;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

/**
 * Created by JDRY_SJM on 2017/4/17.
 */

public class RetrofitUtil {
    public static final int DEFAULT_TIMEOUT = 15;
    public Retrofit mRetrofit;

    private static RetrofitUtil mInstance;

    private String url;

    /**
     * 私有构造方法
     */
    private RetrofitUtil() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();

        if (BuildConfig.DEBUG) {
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        }

        //可以利用okhttp实现缓存
        OkHttpClient client = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)//连接失败后是否重新连接
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(logging)
                .build();

        mRetrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(JDRYConstant.HOST)
                .addConverterFactory(FastJsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    public static RetrofitUtil getInstance() {
        if (mInstance == null) {
            synchronized (RetrofitUtil.class) {
                mInstance = new RetrofitUtil();
            }
        }
        return mInstance;
    }

    public <T> T createReq(Class<T> reqServer) {
        return mRetrofit.create(reqServer);
    }

    public RetrofitUtil setLoginUrl(String cls, String method, String param) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("access.app?")
                .append("clsname=" + cls + "&")
                .append("methodname=" + method + "&data=" + param);
        url = stringBuffer.toString();
        return mInstance;
    }

    public RetrofitUtil setUrl(String cls, String method, String token, String param) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("access.app?")
                .append("clsname=" + cls + "&")
                .append("methodname=" + method)
                .append("&token=" + token);
        if (null != param && !"".equals(param)) {
            stringBuffer.append("&data=" + param);
        }
        url = stringBuffer.toString();
        return mInstance;
    }

    public String getUrl() {
        return url;
    }
}
