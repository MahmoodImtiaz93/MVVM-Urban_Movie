package com.mahmood_imtiaz.urbanmovies;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class AppExecutors {
    //Singleton Patterns

    private static AppExecutors instance;
    public static AppExecutors getInstance(){
        if (instance==null){
            instance=new AppExecutors();
        }
        return instance;
    }

    private final ScheduledExecutorService mNetworkIO = Executors.newScheduledThreadPool(3);

    public ScheduledExecutorService mNetworkIO(){
        return mNetworkIO;
    }
}
