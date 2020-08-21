package com.qrcode.analysis.master;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

//@SpringBootApplication
@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
public class QrcodeAnalysisMasterApplication {

    public static void main(String[] args) {
        SpringApplication.run(QrcodeAnalysisMasterApplication.class, args);
    }

}
