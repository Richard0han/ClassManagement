package com.classmanagement.client.server;

import com.classmanagement.client.thread.ChatHandleThread;
import com.classmanagement.client.thread.FileHandleThread;

import java.io.*;
import java.net.*;

/**
 * ClassManagement
 *
 * @author Richard-J
 * @description 服务器demo
 * @date 2019.04
 */

public class EchoServer {
    private ServerSocket serverSocket;
    private static final int RECE_PORT = 9999;
    private static final int FILE_PORT = 8083;
    private static final String IP = "localhost";

    private int count = 0;

    public EchoServer() throws IOException {
////        创建服务器端套接字
//        serverSocket = new ServerSocket(RECE_PORT);
        System.out.println("服务器启动。");
    }


    public static void main(String[] args) throws IOException {
//        启动服务
//        final com.classmanagement.client.server.Server e1 = new Server();
//        e1.fileService();
        final EchoServer e = new EchoServer();
        e.chatService();
        e.fileService();
    }

    public void chatService() {
        try {
            DatagramSocket megSocket = new DatagramSocket(RECE_PORT);
           new Thread(new ChatHandleThread(megSocket)).start();
        } catch (SocketException e) {
            e.printStackTrace();

        }
    }

    public void fileService() {
        try {
            ServerSocket fileSocket = new ServerSocket(FILE_PORT);
            new Thread(new FileHandleThread(fileSocket)).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

