package com.cqmas.library.network;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.fastjson.FastJsonConverterFactory;

/**
 * Created by modeless mr on 2017/3/8.
 */

public class Factory {

    protected static Retrofit sRetrofit = null;

    protected static Context context;


    /**
     * 初始化配置，默认baseUrl配置到manifests中baseUrl中
     * @param context
     */
    public static void init(Context context) {
        Factory.context = context;
    }


    public static Retrofit getInstance() {
        if (sRetrofit == null) {
            sRetrofit = new Retrofit.Builder()
                    .addConverterFactory(FastJsonConverterFactory.create())
//                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .baseUrl(getBaseUrl(context))
                    .build();
        }
        return sRetrofit;
    }


    /**
     * 从配置文件中获取baseUrl
     * @param context
     * @return
     */
    public static String getBaseUrl(Context context) {
        try {
            ApplicationInfo appInfo = context.getPackageManager()
                    .getApplicationInfo(context.getPackageName(),
                            PackageManager.GET_META_DATA);
            return appInfo.metaData.getString("baseUrl");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getBaseUrl() {
        return getBaseUrl(context);
    }


    public static <T> T create(Class<T> cls) {
        return getInstance().create(cls);
    }
}
