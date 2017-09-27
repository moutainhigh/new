package com.huayu.common;

/**
 * Created by DengPeng on 2017/5/19.
 */
public class ThreadLocalContext<T> {

    private final ThreadLocal<T> contextHolder = new ThreadLocal<>();

    public void clearContext() {
        contextHolder.remove();
    }

    public T getContext() {
        return contextHolder.get();
    }

    public void setContext(T context) {
        contextHolder.set(context);
    }

}
