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
   ServerSocket serverSocket;

    public FileHandleThread(ServerSocket serverSocket) {
        super();
        this.serverSocket = serverSocket;
    }

    @Override
    public void run() {
        OutputStream os = null;
        PrintStream ps = null;
        FileOutputStream fileOutputStream = null;
        try {
            while (true) {
                byte[] b = new byte[1024];
                //数据包
                pack = new DatagramPacket(b, b.length);
                serverSocket.receive(pack);

                //把字节数组转换成SendMsg对象
                //pack.getLength()接收到的字节数
                // 字节数组输入流
                ByteArrayInputStream bis = new ByteArrayInputStream(b, 0, b.length);
                ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(bis));
                File file = (File) ois.readObject();
                System.out.println(file.getSender() + "正在发送发送文件");

                String url = "D:\\chatFile\\" + file.getSender();
                java.io.File file1 = new java.io.File(url);
                if (!file1.exists()) {
                    file1.mkdir();
                }
                url += "\\" + file.getName();
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(b, 0, b.length);
                DataInputStream in = new DataInputStream(byteArrayInputStream);
                fileOutputStream = new FileOutputStream(url);
                long fileSize = file.getFileSize();
                int read = 0;
                int length = 0;
                serverSocket.receive(pack);
                while ((length = in.read(b)) != 0) {
                    read += length;
                    fileOutputStream.write(b,0,length);
                    System.out.println("progress" + (double) read / fileSize);
                    if (read >= fileSize) {
                        break;
                    }
                }
                fileOutputStream.flush();
                System.out.print("上传完成！");
            }
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}