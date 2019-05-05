package com.classmanagement.client.server;

import com.classmanagement.client.dao.Server;
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
    private static final int FILE_PORT = 10005;
    private static final String IP = "localhost";

    private int count = 0;

    public EchoServer() throws IOException {
//        创建服务器端套接字
        serverSocket = new ServerSocket(RECE_PORT);
        System.out.println("服务器启动。");
    }


    public static void main(String[] args) throws IOException {
//        启动服务
        new EchoServer().service();
    }

    public void service() {
        try {
            DatagramSocket megSocket = new DatagramSocket(RECE_PORT);
            ChatHandleThread chatHandleThread = new ChatHandleThread(megSocket);
            chatHandleThread.run();
            ServerSocket fileSocket = new ServerSocket(FILE_PORT);
            while (true) {
                Socket socket = fileSocket.accept();
                FileHandleThread fileHandleThread = new FileHandleThread(socket);
                fileHandleThread.run();
            }
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

