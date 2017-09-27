package com.huayu.common;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * Created by DengPeng on 2017/5/23.
 */
public class ThreadManager {

    private static volatile ThreadManager instance;

    private  ExecutorService executorService;

    private ThreadManager(){
    }

    public static ThreadManager getInstance(){
        if ( null ==instance ){
            synchronized (ThreadManager.class){
                if (null == instance){
                    instance = new ThreadManager();
                }
            }
        }
        return instance;
    }

    public ExecutorService getExecutorService(Integer threadSize,ThreadFactory threadFactory){
        if (null==executorService||executorService.isShutdown()){
            if (null != threadFactory){
                return executorService = Executors.newFixedThreadPool(threadSize,threadFactory);
            }
            return executorService = Executors.newFixedThreadPool(threadSize);
        }
        return executorService;
    }

    public ExecutorService getExecutorService(Integer threadSize){

        return getExecutorService(threadSize,null);
    }

}
