package com.qrcode.analysis.master.core.util;

import com.alibaba.fastjson.JSON;
import com.qrcode.analysis.master.core.common.utils.BeanUtils;
import com.qrcode.analysis.master.core.common.utils.StringUtil;
import com.qrcode.analysis.master.core.model.Analysis;
import com.qrcode.analysis.master.core.protocol.request.RequestData;
import com.qrcode.analysis.master.core.protocol.request.analysis.RequestAnalysis;
import com.qrcode.analysis.master.core.protocol.response.ResponseData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 公共方法类
 * @Author yoko
 * @Date 2020/1/7 20:25
 * @Version 1.0
 */
public class HodgepodgeMethod {
    private static Logger log = LoggerFactory.getLogger(HodgepodgeMethod.class);



    /**
     * @Description: 获取小微数据集合
     * @param str
     * @return
     * @author yoko
     * @date 2020/8/20 14:51
    */
    public static List<Long> getWxIdList(String str){
        List<Long> resList = new ArrayList<>();
        String [] strArr = str.split(",");
        if (strArr.length > 0){
            for (String str1 : strArr){
                resList.add(Long.parseLong(str1));
            }
        }
        return resList;
    }

    /**
     * @Description: 获取小微数据集合
     * @param str
     * @return
     * @author yoko
     * @date 2020/8/20 14:51
     */
    public static List<String> getToWxIdList(String str){
        List<String> resList = new ArrayList<>();
        String [] strArr = str.split(",");
        if (strArr.length > 0){
            for (String str1 : strArr){
                resList.add(str1);
            }
        }
        return resList;
    }

    /**
     * @Description: 组装获取要解析的任务的调取接口的参数
     * @param wxId - 微信ID
     * @return
     * @author yoko
     * @date 2020/8/20 15:57
    */
    public static String assembleRequestAnalysisJsonData(long wxId) throws Exception{
        RequestData requestData = new RequestData();
        RequestAnalysis requestAnalysis = new RequestAnalysis();
        requestAnalysis.wxId = wxId;
        String jsonData = JSON.toJSONString(requestAnalysis);
        requestData.jsonData = StringUtil.mergeCodeBase64(jsonData);
        return JSON.toJSONString(requestData);
    }

    /**
     * @Description: 组装获取要解析的任务的调取接口的参数
     * @param toWxid - 微信ID
     * @return
     * @author yoko
     * @date 2020/8/20 15:57
     */
    public static String assembleStrRequestAnalysisJsonData(String toWxid) throws Exception{
        RequestData requestData = new RequestData();
        RequestAnalysis requestAnalysis = new RequestAnalysis();
        requestAnalysis.toWxid = toWxid;
        String jsonData = JSON.toJSONString(requestAnalysis);
        requestData.jsonData = StringUtil.mergeCodeBase64(jsonData);
        return JSON.toJSONString(requestData);
    }

    public static String getResponseData(String str) throws Exception{
        ResponseData responseData = JSON.parseObject(str, ResponseData.class);
        if (responseData == null){
            return null;
        }
        if (!responseData.resultCode.equals("0")){
            return null;
        }else {
            RequestData requestData = JSON.parseObject(responseData.data.toString(), RequestData.class);
            return StringUtil.decoderBase64(requestData.jsonData);
        }
    }

    /**
     * @Description: 组装返回给服务端的解析成功数据
     * @param analysis - 服务端回传的数据，要解析的数据
     * @param isOk - 数据是否可正常解析：1正常解析，2解析有误（不是二维码图片）
     * @param qrcodeAds - 二维码解析之后的地址
     * @return java.lang.String
     * @author yoko
     * @date 2020/8/20 16:55
     */
    public static String assembleAnalysisSucJsonData(Analysis analysis, int isOk, String qrcodeAds) throws Exception{
        RequestData requestData = new RequestData();
        RequestAnalysis requestAnalysis = BeanUtils.copy(analysis, RequestAnalysis.class);
        requestAnalysis.isOk = isOk;
        requestAnalysis.qrcodeAds = qrcodeAds;
        String jsonData = JSON.toJSONString(requestAnalysis);
        requestData.jsonData = StringUtil.mergeCodeBase64(jsonData);
        return JSON.toJSONString(requestData);
    }

    

}
