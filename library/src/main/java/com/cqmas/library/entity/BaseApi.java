package com.cqmas.library.entity;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import com.cqmas.library.network.HttpOnNextListener;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.trello.rxlifecycle2.components.support.RxFragment;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import okhttp3.Interceptor;
import retrofit2.Retrofit;

/**
 * 请求数据统一封装类
 */
public abstract class BaseApi<T> {
    public static int FROM_ACTIVITY = 1;
    public static int FROM_FRAGMENT = 2;
    //rx生命周期管理
    private int isFrom = FROM_ACTIVITY;
    private SoftReference<RxAppCompatActivity> rxAppCompatActivity;
    private SoftReference<RxFragment> rxFragment;
    /*回调*/
    private SoftReference<HttpOnNextListener> listener;
    /*是否能取消加载框*/
    private boolean cancel = false;
    /*是否显示加载框*/
    private boolean showProgress;
    /*是否需要缓存处理，注意:OkHttp缓存机制只对get请求方式保存*/
    private boolean cache = false;
    /*基础url*/
    private String baseUrl;
    /*方法-如果需要缓存必须设置这个参数；不需要不用設置*/
    private String mothed;
    /*超时时间-默认30秒*/
    private int connectionTime = 30;
    /*有网情况下的本地缓存时间默认60秒*/
    private int cookieNetWorkTime = 60;
    /*无网络的情况下本地缓存时间默认30天*/
    private int cookieNoNetWorkTime = 24 * 60 * 60 * 30;

    private List<Interceptor> interceptors = new ArrayList<>();

    /**
     * Activity 中的网络请求初始化
     */
    public BaseApi(HttpOnNextListener listener, RxAppCompatActivity rxAppCompatActivity) {
        isFrom = FROM_ACTIVITY;
        setListener(listener);
        setRxAppCompatActivity(rxAppCompatActivity);
        setBaseUrl(getBaseUrl(rxAppCompatActivity.getApplicationContext()));
    }

    /**
     * Fragmen 中的网络请求初始化
     */
    public BaseApi(HttpOnNextListener listener, RxFragment rxFragment) {
        isFrom = FROM_FRAGMENT;
        setListener(listener);
        setRxFragment(rxFragment);
        setBaseUrl(getBaseUrl(rxFragment.getActivity().getApplicationContext()));
    }

    public  void addOkHttpInterceptor(Interceptor interceptor){
        interceptors.add(interceptor);
    }

    public List<Interceptor> getOkHttpInterceptors(){
        return  interceptors;
    }

    /**
     * 从配置文件中获取baseUrl
     *
     * @param context
     * @return
     */
    public String getBaseUrl(Context context) {
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


    /**
     * 设置参数
     *
     * @param retrofit
     * @return
     */
    public abstract Observable getObservable(Retrofit retrofit);


    public int getCookieNoNetWorkTime() {
        return cookieNoNetWorkTime;
    }

    public void setCookieNoNetWorkTime(int cookieNoNetWorkTime) {
        this.cookieNoNetWorkTime = cookieNoNetWorkTime;
    }

    public int getCookieNetWorkTime() {
        return cookieNetWorkTime;
    }

    public void setCookieNetWorkTime(int cookieNetWorkTime) {
        this.cookieNetWorkTime = cookieNetWorkTime;
    }

    public String getMothed() {
        return mothed;
    }

    public int getConnectionTime() {
        return connectionTime;
    }

    public void setConnectionTime(int connectionTime) {
        this.connectionTime = connectionTime;
    }

    public void setMothed(String mothed) {
        this.mothed = mothed;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getUrl() {
        return baseUrl + mothed;
    }

    public int getIsFrom() {
        return isFrom;
    }

    public void setRxAppCompatActivity(RxAppCompatActivity rxAppCompatActivity) {
        this.rxAppCompatActivity = new SoftReference(rxAppCompatActivity);
    }

    public void setRxFragment(RxFragment rxFragment) {
        this.rxFragment = new SoftReference(rxFragment);
    }

    public boolean isCache() {
        return cache;
    }

    public void setCache(boolean cache) {
        this.cache = cache;
    }

    public boolean isShowProgress() {
        return showProgress;
    }

    public void setShowProgress(boolean showProgress) {
        this.showProgress = showProgress;
    }

    public boolean isCancel() {
        return cancel;
    }

    public void setCancel(boolean cancel) {
        this.cancel = cancel;
    }

    public SoftReference<HttpOnNextListener> getListener() {
        return listener;
    }

    public void setListener(HttpOnNextListener listener) {
        this.listener = new SoftReference(listener);
    }

    /*
     * 获取当前rx生命周期
     * @return
     */
    public RxAppCompatActivity getRxAppCompatActivity() {
        return rxAppCompatActivity.get();
    }
    public RxFragment getRxFragment() {
        return rxFragment.get();
    }
}
