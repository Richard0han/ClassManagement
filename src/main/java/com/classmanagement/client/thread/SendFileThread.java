package com.classmanagement.client.thread;

import com.classmanagement.client.utils.JsonParser;

import java.io.*;
import java.net.Socket;

/**
 * ClassManagement
 *
 * @author Richard-J
 * @description 传送文件线程
 * @date 2019.05
 */

public class SendFileThread extends Thread {
    private Socket socket;
    private String fileJson;
    private com.classmanagement.client.bean.File file;
    private File f;

    public SendFileThread(Socket socket, com.classmanagement.client.bean.File file, File f) {
        this.socket = socket;
        this.file = file;
        this.f = f;
        fileJson = JsonParser.FileToJson(file) + "\n";
    }

    @Override
    public void run() {
        try {
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            out.writeUTF(fileJson);
            out.flush();
            DataInputStream fis = new DataInputStream(new FileInputStream(f));
            //每次上传1M的内容
            byte[] b = new byte[1024];
            int length;
            int fileSize = 0;
            //实时监测上传进度
            while ((length = fis.read(b)) != -1) {
                fileSize += length;
                System.out.println("文件上传进度：" + ((double) fileSize / file.getFileSize() * 100) + "%");
                //2、把文件写入socket输出流
                out.write(b, 0, length);
                out.flush();
            }
            //关闭文件流
            fis.close();
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
