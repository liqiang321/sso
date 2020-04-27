package com.example.demo.test;

import com.example.demo.controller.ReceiveFileThread;

public class FileReceiveTest{//接收方

    public static void main(String[] args) {
        ReceiveFileThread rf=new ReceiveFileThread();
        rf.start();
    }

}
