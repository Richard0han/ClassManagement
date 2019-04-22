package com.classmanagement.client.thread;

import com.classmanagement.client.bean.ChatInfo;

import java.io.*;
import java.net.Socket;

/**
 * ClassManagement
 *
 * @author Richard-J
 * @description 服务器处理线程
 * @date 2019.04
 */

public class ServerHandleThread implements Runnable {
    Socket socket = null;

    public ServerHandleThread(Socket socket) {
        super();
        this.socket = socket;
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        OutputStream os = null;
        PrintStream ps = null;
        try {
            while (true) {
                InputStream is = socket.getInputStream();
                ObjectInputStream ois = new ObjectInputStream(is);
                //readObject()方法必须保证服务端和客户端的User包名一致，要不然会出现找不到类的错误
                ChatInfo chatInfo = (ChatInfo) ois.readObject();
                System.out.println("客户端发送的对象：" + chatInfo);
                socket.shutdownInput();// 禁用套接字的输入流
                os = socket.getOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(os);
                oos.writeObject(chatInfo);
                ps = new PrintStream(oos);
                ps.flush();
            }
        } catch (IOException | ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (os != null) {
                    os.close();
                }
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
    }

}
