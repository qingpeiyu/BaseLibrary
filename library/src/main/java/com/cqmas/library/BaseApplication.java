package com.cqmas.library;

import android.app.Application;

import com.cqmas.library.network.Factory;
import com.cqmas.library.utils.PicUtils;


/**
 * Created by Qing peiyu on 2017/2/24 0024.
 */

public class BaseApplication extends Application {
    private static BaseApplication instance;

    public static BaseApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Factory.init(this);//初始化Retrofit
        PicUtils.init(this);
    }
}
