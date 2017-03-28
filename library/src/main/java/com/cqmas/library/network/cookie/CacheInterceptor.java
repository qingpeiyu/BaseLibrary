package com.cqmas.library.network.cookie;


import com.cqmas.library.BaseApplication;
import com.cqmas.library.utils.AppUtil;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * get缓存方式拦截器(缓存策略是：无网络读取缓存,有网获取最新数据)
 */

public class CacheInterceptor implements Interceptor {
    //这是设置在多长时间范围内获取缓存里面
    public static final CacheControl FORCE_CACHE1 = new CacheControl.Builder()
            .onlyIfCached()
            .maxStale(60 * 60 * 6, TimeUnit.SECONDS)//这里是时间，CacheControl.FORCE_CACHE--是int型最大值
            .build();
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();


        Response responseLatest;
        if (AppUtil.isNetworkAvailable(BaseApplication.getInstance())) {
            int maxAge = 0; // 有网络时 设置缓存超时时间0个小时
            Response response = chain.proceed(request);
            responseLatest = response.newBuilder()
                    .removeHeader("Pragma")
                    .removeHeader("Cache-Control")
                    .header("Cache-Control", "public, max-age=" + maxAge)
                    .build();
        } else {
            int maxStale = 60 * 60 * 6; // 没网失效6小时
            request = request.newBuilder()
                    .cacheControl(FORCE_CACHE1)
                    .build();
            Response response = chain.proceed(request);
            responseLatest = response.newBuilder()
             //下面注释的部分设置也没有效果，因为在上面已经设置了
//                    .removeHeader("Pragma")
//                    .removeHeader("Cache-Control")
//                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                    .build();
        }
        return responseLatest;
    }

}
