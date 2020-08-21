package com.qrcode.analysis.master.core.protocol.response.analysis;

import com.qrcode.analysis.master.core.model.Analysis;

import java.io.Serializable;

/**
 * @Description TODO
 * @Author yoko
 * @Date 2020/8/20 17:29
 * @Version 1.0
 */
public class ResponseAnalysis implements Serializable {
    private static final long   serialVersionUID = 1233023331143L;
    public Analysis dataModel;
    public ResponseAnalysis(){

    }

    public Analysis getDataModel() {
        return dataModel;
    }

    public void setDataModel(Analysis dataModel) {
        this.dataModel = dataModel;
    }
}
