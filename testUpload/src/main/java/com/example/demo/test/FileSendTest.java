package com.example.demo.test;

import com.example.demo.controller.SendFileThread;

public class FileSendTest{//发送方

    public static void main(String[] args) {
        SendFileThread sf=new SendFileThread();
        sf.start();
    }

}

