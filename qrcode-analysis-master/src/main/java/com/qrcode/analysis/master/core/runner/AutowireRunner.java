package com.qrcode.analysis.master.core.runner;

import com.alibaba.fastjson.JSON;
import com.qrcode.analysis.master.core.common.utils.*;
import com.qrcode.analysis.master.core.common.utils.constant.LoadConstant;
import com.qrcode.analysis.master.core.model.Analysis;
import com.qrcode.analysis.master.core.protocol.request.RequestData;
import com.qrcode.analysis.master.core.protocol.request.analysis.RequestAnalysis;
import com.qrcode.analysis.master.core.protocol.response.analysis.ResponseAnalysis;
import com.qrcode.analysis.master.core.util.ComponentUtil;
import com.qrcode.analysis.master.core.util.HodgepodgeMethod;
import com.qrcode.analysis.master.core.util.Singleton;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


@Component
@Order(0)
public class AutowireRunner implements ApplicationRunner {
    private final static Logger log = LoggerFactory.getLogger(AutowireRunner.class);

    @Resource
    private LoadConstant loadConstant;

    /**
     * 5分钟.
     */
    public long FIVE_MIN = 300;

    /**
     * 主体：调取美好接口实现业务
     */
    Thread runThread = null;

    /**
     * 加载文件获取微信集合ID
     */
    RunThreadWx runThreadWx = null;

    /**
     * 加载文件获取请求服务端的接口地址/URL
     */
    RunThreadUrl runThreadUrl = null;










    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("AutowireRunner ...");
        ComponentUtil.loadConstant = loadConstant;

        runThread = new RunThread();
        runThread.start();

        runThreadWx = new RunThreadWx();
        runThreadWx.start();

        runThreadUrl = new RunThreadUrl();
        runThreadUrl.start();






    }

    /**
     * @author df
     * @Description: TODO(请求服务器接口)
     * <p>
     *     1.获取小微数据集合
     *
     * </p>
     * @create 20:21 2019/1/29
     **/
    class RunThread extends Thread{
        @Override
        public void run(){
            log.info("启动啦............");
            String url = "";

            while (true){
                try {
                    log.info("-----------进来啦,时间:" + DateUtil.getNowLongTime());
                }catch (Exception e){
                    e.printStackTrace();
                }
                url = Singleton.getInstance().fineUrl;
                List<String> dataList = Singleton.getInstance().getToWxIdList();// 小微的数据集合
                if (dataList != null && dataList.size() > 0){
                    try{
                        for (String toWxid: dataList){
                            String str = "";
                            try {
                                str = HodgepodgeMethod.assembleStrRequestAnalysisJsonData(toWxid);
                            }catch (Exception e){
                                e.printStackTrace();
                            }
//                            String resData = HttpSendUtils.sendPostAppJson(url + "/" + "/fine/analysis/getData", str);
                            log.info("start:" + DateUtil.getNowLongTime() + ", 微信ID:" + toWxid);
                            String resData = HttpUtil.doPostJson(url + "/" + "/fine/analysis/getData", str);
                            log.info("end:" + DateUtil.getNowLongTime() + ", 微信ID:" + toWxid);
                            if (!StringUtils.isBlank(resData)){
                                String resPostData = "";
                                try {
                                    resPostData = HodgepodgeMethod.getResponseData(resData);
                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                                if (!StringUtils.isBlank(resPostData)){
                                    String qrCodeAds = "";
                                    int isOk = 2;
                                    // 获取要解析的数据
                                    ResponseAnalysis responseAnalysis = JSON.parseObject(resPostData, ResponseAnalysis.class);
                                    Analysis analysis = responseAnalysis.dataModel;
                                    if (analysis != null && !StringUtils.isBlank(analysis.picturePath)){
                                        // 解析图片二维码
                                        try {
                                            qrCodeAds = QrCodeUtil.decode(analysis.picturePath);
                                            if (!StringUtils.isBlank(qrCodeAds)){
                                                isOk = 1;
                                            }
                                        }catch (Exception e){
                                            e.printStackTrace();
                                        }
                                    }

                                    // 组装要发送的数据
                                    String jsonData = "";
                                    try {
                                        log.info("qrCodeAds:" + qrCodeAds);
                                        jsonData = HodgepodgeMethod.assembleAnalysisSucJsonData(analysis, isOk, qrCodeAds);
                                    }catch (Exception e){
                                        e.printStackTrace();
                                    }
                                    if (!StringUtils.isBlank(jsonData)){
//                                        HttpSendUtils.sendPostAppJson(url + "/" + "/fine/analysis/update", jsonData);
                                        log.info("start-two:" + DateUtil.getNowLongTime());
                                        HttpUtil.doPostJson(url + "/" + "/fine/analysis/update", jsonData);
                                        log.info("ent-two:" + DateUtil.getNowLongTime());
                                    }
                                }
                            }
                            // 每次请求一次睡眠1秒
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }


                // 每次运行之后随机4秒以内的睡眠
                int sleepNum = new Random().nextInt(5);
                if (sleepNum != 0){
                    try {
                        Thread.sleep(sleepNum * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }


        }
    }


    /**
     * @author df
     * @Description: TODO(读取本地文件获取微信ID集合)
     * <p>
     *     读取本地配置文件
     * </p>
     * @create 20:21 2019/1/29
     **/
    class RunThreadWx extends Thread{
        @Override
        public void run() {
            List<String> dataList = new ArrayList<>();// 小微的数据集合
            while (true){
                // 读取本地小微ID集合
                File file = new File(ComponentUtil.loadConstant.AnalysisWxDataPath);
                String str = StringUtil.txt2String(file);
                dataList = HodgepodgeMethod.getToWxIdList(str);
                Singleton.getInstance().setToWxIdList(dataList);
                try {
                    Thread.sleep(2 * 60 * 1000);//2分钟 Thread.currentThread().sleep(2 * 60 * 1000);也行
                } catch (InterruptedException e) {
                    //可捕获interrupt，来中断睡眠
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * @author df
     * @Description: TODO(读取本地文件获取服务端的接口地址)
     * <p>
     *     读取本地配置文件
     * </p>
     * @create 20:21 2019/1/29
     **/
    class RunThreadUrl extends Thread{
        @Override
        public void run() {
            while (true){
                // 读取本地小微ID集合
                File file = new File(ComponentUtil.loadConstant.fineInterfacePath);
                String str = StringUtil.txt2String(file);
                if (!StringUtils.isBlank(str)){
                    Singleton.getInstance().setFineUrl(str);
                }
                try {
                    Thread.sleep(2 * 60 * 1000);//2分钟 Thread.currentThread().sleep(2 * 60 * 1000);也行
                } catch (InterruptedException e) {
                    //可捕获interrupt，来中断睡眠
                    e.printStackTrace();
                }
            }
        }
    }

}
