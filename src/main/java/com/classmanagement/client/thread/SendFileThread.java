package com.classmanagement.client.thread;

import com.classmanagement.client.bean.File;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * ClassManagement
 *
 * @author Richard-J
 * @description 传送文件线程
 * @date 2019.05
 */

public class SendFileThread extends Thread{
    private Socket socket;
    private String fileJson;
    private java.io.File f;

    public SendFileThread(){

    }

    @Override
    public void run(){
        try {
            OutputStream out= socket.getOutputStream();
            out.write(fileJson.getBytes("utf-8"));
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
