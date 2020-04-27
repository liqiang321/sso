package com.example.demo.test;

import com.example.demo.util.ConverVideoUtils;
import org.springframework.beans.factory.annotation.Autowired;

public class testTrans {
    @Autowired
    private ConverVideoUtils converVideoUtils;
    public static void main(String[] args) {
        ConverVideoUtils converVideoUtils = new ConverVideoUtils();
        converVideoUtils.beginConver("E:\\百度云下载文件\\电影\\百度云下载\\视频1\\10、尚硅谷_SpringBoot_配置-yaml语法.avi");

    }
}
