package com.cqmas.library.utils;


import android.util.Log;

import com.cqmas.library.BaseApplication;
import com.cqmas.library.entity.BaseApi;
import com.cqmas.library.network.cookie.CacheInterceptor;
import com.cqmas.library.network.exception.RetryWhenNetworkException;
import com.cqmas.library.subscribers.ProgressSubscriber;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.fastjson.FastJsonConverterFactory;

/**
 * http交互处理类
 */
public class HttpManager {
    private volatile static HttpManager INSTANCE;

    //构造方法私有
    private HttpManager() {
    }

    //获取单例
    public static HttpManager getInstance() {
        if (INSTANCE == null) {
            synchronized (HttpManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new HttpManager();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * 处理http请求
     *
     * @param basePar 封装的请求数据
     */
    public Observable doHttp(BaseApi basePar) {
        //手动创建一个OkHttpClient并设置超时时间缓存等设置
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(basePar.getConnectionTime(), TimeUnit.SECONDS);
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(httpLoggingInterceptor);//日志拦截器
        if (basePar.getOkHttpInterceptors() != null) {
            List okHttpInterceptors = basePar.getOkHttpInterceptors();
            for (int i = 0; i < okHttpInterceptors.size(); i++) {
                builder.addInterceptor((Interceptor) okHttpInterceptors.get(i));
            }
        }
        if (basePar.isCache()) {
//            builder.addInterceptor(new CookieInterceptor(basePar.isCache(),basePar.getUrl()));
            //设置缓存路径
            File httpCacheDirectory = new File(BaseApplication.getInstance().getCacheDir(), "responses");
            //设置缓存 10M
            Cache cache = null;
            try {
                cache = new Cache(httpCacheDirectory, 10 * 1024 * 1024);
            } catch (Exception e) {
                Log.e("OKHttp", "Could not create http cache", e);
            }
            if (null != cache) {
                //必须使用addInterceptor和addNetworkInterceptor,否则无法缓存
                builder.addInterceptor(new CacheInterceptor());
                builder.addNetworkInterceptor(new CacheInterceptor());
                builder.cache(cache);
            }
        }
        //cookie持久化（https://github.com/franmontiel/PersistentCookieJar）
        //ClearableCookieJar cookieJar =new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(context));
        //builder.cookieJar(cookieJar)

        /*创建retrofit对象*/
        Retrofit retrofit = new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(FastJsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(basePar.getBaseUrl())
                .build();


        /*rx处理*/
        ProgressSubscriber subscriber = new ProgressSubscriber(basePar);
        Observable observable = basePar.getObservable(retrofit)
                /*失败后的retry配置*/
                .retryWhen(new RetryWhenNetworkException())
                /*生命周期管理*/
//                .compose(basePar.getRxAppCompatActivity().bindToLifecycle())
                .compose(basePar.getRxAppCompatActivity().bindUntilEvent(ActivityEvent.DESTROY))
                /*http请求线程*/
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                /*回调线程*/
                .observeOn(AndroidSchedulers.mainThread());
                /*结果判断*/
        if (basePar instanceof Function) {
            observable.map((Function) basePar);
        }

        /*数据回调*/
        observable.subscribe(subscriber);

        return  observable;
    }
}
