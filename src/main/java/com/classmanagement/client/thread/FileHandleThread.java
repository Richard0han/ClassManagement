package com.classmanagement.client.thread;

import com.classmanagement.client.bean.File;
import com.classmanagement.client.dao.AddData;
import com.classmanagement.client.utils.JsonParser;

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
    ServerSocket serverSocket;

    public FileHandleThread(ServerSocket serverSocket) {
        super();
        this.serverSocket = serverSocket;
    }

    @Override
    public void run() {
        try {
            Socket socket = serverSocket.accept();
            System.out.println(socket.getInetAddress());
            DataInputStream is = new DataInputStream(socket.getInputStream());
            // 循环一直接收当前socket发来的消息
            Thread.sleep(500);
            int length = 0;
            byte[] b = new byte[1024];
            String json = null;
            // 1、首先先得到实体类
            json = is.readUTF();
            System.out.println(json);
            File file = JsonParser.getFile(json);
            if (file.getUrl() == null) {
                System.out.println("接受到的文件名为：" + file.getName());
                file.setUrl("D:\\chatFile\\" + file.getName());
                DataOutputStream dos = new DataOutputStream(new FileOutputStream(file.getUrl()));
                int fileSize = 0;
                // 2、把socket输入流写到文件输出流中去
                while ((length = is.read(b)) != -1) {
                    dos.write(b, 0, length);
                    dos.flush();
                    fileSize += length;
                    System.out.println("当前大小：" + fileSize);
                    System.out.println("总大小：" + file.getFileSize());
                    System.out.println(length);
                    //这里通过先前传递过来的文件大小作为参照，因为该文件流不能自主停止，所以通过判断文件大小来跳出循环
                    if (length < 1024) {
                        System.out.println("结束下载");
                        break;
                    }
                }
                dos.close();
                System.out.println("文件:保存成功");
                System.out.println("用户 : " + file.getSender());
                AddData.addFile(file);
            }else {
                System.out.println("正在请求下载"+file.getName()+"！");
                java.io.File f1 = new java.io.File(file.getUrl());
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                out.writeLong(f1.length());
                System.out.println(f1.length());
                out.flush();
                DataInputStream fis = new DataInputStream(new FileInputStream(f1));
                //每次上传1M的内容
                int fileSize = 0;
                //实时监测上传进度
                while ((length = fis.read(b)) != -1) {
                    fileSize += length;
                    System.out.println("文件下载进度：" + ((double) fileSize / f1.length() * 100) + "%");
                    //2、把文件写入socket输出流
                    out.write(b, 0, length);
                    out.flush();
                }
                //关闭文件流
                fis.close();
                out.flush();
            }
            socket.close();
            new FileHandleThread(serverSocket).run();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}