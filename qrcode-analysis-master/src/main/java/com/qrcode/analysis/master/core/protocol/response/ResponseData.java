package com.qrcode.analysis.master.core.protocol.response;


import com.qrcode.analysis.master.core.protocol.base.BaseResponse;

import java.io.Serializable;

/**
 * @Description 公告的返回结果数据给客户端是bean
 * @Author yoko
 * @Date 2020/5/14 16:46
 * @Version 1.0
 */
public class ResponseData<T> implements Serializable {
    private static final long   serialVersionUID = 1233023331141L;

    public String resultCode;
    public String message;
    //	private String content;
    public T data;

    public ResponseData(){

    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
