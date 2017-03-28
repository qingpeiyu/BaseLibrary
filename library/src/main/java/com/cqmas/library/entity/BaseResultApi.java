package com.cqmas.library.entity;

import android.util.Log;

import com.cqmas.library.network.BaseResultData;
import com.cqmas.library.network.HttpOnNextListener;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import io.reactivex.functions.Function;


/**
 * 请求数据统一封装类
 *
 * 返回的数据接口符合
 * {
 *     "Code":  ,
 *     "Desc":  ,
 *     "Data":{
 *
 *     }
 * }
 */
public abstract class BaseResultApi<T> extends BaseApi implements Function<BaseResultData<T>, T> {


    public BaseResultApi(HttpOnNextListener<T> listener, RxAppCompatActivity rxAppCompatActivity) {
        super(listener, rxAppCompatActivity);
    }


    @Override
    public T apply(BaseResultData<T> tData) throws Exception {
        Log.d("BaseResultApi", "tData.ok():::::::" + tData.ok());
        if (!tData.ok()) {
            //Todo 实现一个业务错误类，便于判断
            throw new Exception(tData.getMsg());
        }
        return tData.getData();
    }
}
