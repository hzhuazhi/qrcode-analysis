package com.qrcode.analysis.master.core.util;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description 单例
 * @Author yoko
 * @Date 2020/8/20 15:10
 * @Version 1.0
 */
public class Singleton {

    private static Singleton singleton;
    private static ReentrantLock lock = new ReentrantLock();

    public List<Long> list ;
    public List<String> toWxIdList;
    public String fineUrl;

    private Singleton() {

    }

    public static Singleton getInstance() {
        if (singleton == null) {
            lock.lock();
            if (singleton == null) {
                singleton = new Singleton();
            }
            lock.unlock();
        }
        return singleton;
    }

    public List<Long> getList() {
        return list;
    }

    public void setList(List<Long> list) {
        this.list = list;
    }

    public String getFineUrl() {
        return fineUrl;
    }

    public void setFineUrl(String fineUrl) {
        this.fineUrl = fineUrl;
    }

    public List<String> getToWxIdList() {
        return toWxIdList;
    }

    public void setToWxIdList(List<String> toWxIdList) {
        this.toWxIdList = toWxIdList;
    }
}
