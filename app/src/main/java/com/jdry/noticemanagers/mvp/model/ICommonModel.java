package com.jdry.noticemanagers.mvp.model;

/**
 * Created by JDRY-SJM on 2018/1/8.
 */

public interface ICommonModel<T> {
    /**
     * 请求成功回调接口
     *
     * @param t
     * @param order
     */
    void httpRequestSuccess(T t, int order);

    /**
     * 请求失败回调接口
     *
     * @param t
     * @param order
     */
    void httpRequestFailure(String t, int order);
}
