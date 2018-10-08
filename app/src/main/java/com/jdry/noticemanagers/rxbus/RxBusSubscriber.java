package com.jdry.noticemanagers.rxbus;

import rx.Subscriber;

/**
 * Created by JDRY-SJM on 2018/3/30.
 */

public abstract class RxBusSubscriber<T> extends Subscriber<T> {
    @Override
    public void onNext(T t) {
        try {
            onEvent(t);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCompleted() {
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
    }

    protected abstract void onEvent(T t);
}
