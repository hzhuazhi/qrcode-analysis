package com.qrcode.analysis.master.core.common.utils.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Description 加载的配置文件
 * @Author yoko
 * @Date 2019/12/30 12:37
 * @Version 1.0
 */
@Component
public class LoadConstant {

    /**
     * 小微ID文件存放路径
     */
    @Value("${analysis.wx.data.path}")
    public String AnalysisWxDataPath;

    /**
     * 美好的接口文件存放路径
     */
    @Value("${fine.interface.path}")
    public String fineInterfacePath;

}
