package com.classmanagement.client.thread;

import com.classmanagement.client.bean.File;

import java.io.*;
import java.net.*;

/**
 * ClassManagement
 *
 * @author Richard-J
 * @description 服务器文件处理线程
 * @date 2019.05
 */

public class FileHandleThread implements Runnable {
   Socket socket;

    public FileHandleThread(Socket socket) {
        super();
        this.socket = socket;
    }

    @Override
    public void run() {

    }
}