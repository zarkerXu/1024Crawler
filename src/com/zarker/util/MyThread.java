package com.zarker.util;

import resources.ResoucesUtil;

/**
 * @author xuzhi
 * @createTime 2018-03-02 23:21:43
 * @description
 */
public class MyThread extends Thread {
    private String url;
    private String videoName;
    public MyThread(String url, String videoName){
        this.url=url;
        this.videoName=videoName;
    }

    public void run(){
        FileDownLoad.downloadFile(url, ResoucesUtil.getResouces("filePath"), videoName);
    }
}
