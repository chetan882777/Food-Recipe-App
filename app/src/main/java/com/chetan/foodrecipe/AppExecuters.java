package com.chetan.foodrecipe;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import static com.chetan.foodrecipe.util.Constants.EXECUTOR_CORE_POOL_SIZE;

public class AppExecuters {
    private static AppExecuters instance;

    public static AppExecuters getInstance(){
        if(instance == null){
            instance = new AppExecuters();
        }
        return instance;
    }

    private final ScheduledExecutorService mNetworkIO = Executors.newScheduledThreadPool(EXECUTOR_CORE_POOL_SIZE);

    public ScheduledExecutorService NetworkIO() {return mNetworkIO;}
}
