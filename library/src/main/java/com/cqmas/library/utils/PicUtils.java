package com.cqmas.library.utils;

import android.content.Context;
import android.widget.ImageView;

import com.cqmas.library.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

/**
 * 图片加载通用设置类
 * Created by modeless mr on 2017/3/9.
 */

public class PicUtils {

    static int PLACE_HOLDER = R.mipmap.img_default;
    static int ERROR = R.mipmap.img_default;

    private static Context context;

    /**
     * 基础url，使用于相对路径
     **/
    private static String BaseUrl = null;


    /**
     * 需要 网络权限和读写权限
     *
     * @param context
     */
    public static void init(Context context) {
        PicUtils.context = context;
    }

    public static void init(Context context, String baseUrl) {
        PicUtils.context = context;
        setBaseUrl(baseUrl);
    }

    public static void setBaseUrl(String url) {
        PicUtils.BaseUrl = url;
    }

    public static Picasso Get() {
        synchronized (PicUtils.class) {
            if (context == null) {
                throw new NullPointerException();
            }
            return Picasso.with(context);
        }
    }

    public static RequestCreator Load(String url) {
        return Get().load(url);
    }


    /**
     * @param url  全路径
     * @param view
     */
    public static void Show(String url, ImageView view) {
        Load(url)
                .placeholder(PLACE_HOLDER)
                .error(ERROR)
                .into(view);
    }

    /**
     * @param url
     * @param view
     */
    public static void ShowL(String url, ImageView view) {
        Show(makeUrl(url), view);
    }

    /**
     * 组装Url,将baseUrl和相对路径，拼装成全路径
     *
     * @param url 相对路径
     * @return
     */
    public static String makeUrl(String url) {
        return BaseUrl + url;
    }


    /**
     * 显示图片
     *
     * @param tag
     * @param url
     * @param view
     */
    public static void Show(Object tag, String url, ImageView view) {
        Load(url)
                .tag(tag)
                .placeholder(PLACE_HOLDER)
                .error(ERROR)
                .into(view);
    }

    public static void ShowL(Object tag, String url, ImageView view) {
        Show(tag, makeUrl(url), view);
    }


    public static int GetPlaceHolderResId() {
        return PLACE_HOLDER;
    }

    public static int GetErrorResId() {
        return ERROR;
    }

}
