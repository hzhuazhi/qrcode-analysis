package com.qrcode.analysis.master.core.protocol.request;


import com.qrcode.analysis.master.core.protocol.base.BaseRequest;

import java.io.Serializable;

/**
 * @Description 协议：解析
 * @Author yoko
 * @Date 2020/8/19 18:41
 * @Version 1.0
 */
public class RequestData implements Serializable {
    private static final long   serialVersionUID = 1233283333313L;

    /**
     * 具体请求的数据
     */
    public String jsonData;



    public RequestData(){

    }

    public String getJsonData() {
        return jsonData;
    }

    public void setJsonData(String jsonData) {
        this.jsonData = jsonData;
    }
}
