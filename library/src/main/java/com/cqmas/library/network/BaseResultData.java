package com.cqmas.library.network;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by modeless mr on 2017/3/7.
 */

public class BaseResultData<T> {

    @JSONField(name = "Code")
    private int code;
    @JSONField(name = "Data")
    private T data;
    @JSONField(name = "Desc")
    private String msg;

    public boolean ok(){
        return getCode()==100;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
