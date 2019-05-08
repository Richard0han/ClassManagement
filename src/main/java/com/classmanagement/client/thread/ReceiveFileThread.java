package com.classmanagement.client.thread;

import com.classmanagement.client.bean.File;
import com.classmanagement.client.utils.JsonParser;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * ClassManagement
 *
 * @author Richard-J
 * @description 接收文件线程
 * @date 2019.05
 */

public class ReceiveFileThread extends Thread {
    private Socket socket;
    private String fileJson;
    private com.classmanagement.client.bean.File file;

    public ReceiveFileThread(Socket socket, com.classmanagement.client.bean.File file) {
        this.socket = socket;
        this.file = file;
        fileJson = JsonParser.FileToJson(file);
    }

    @Override
    public void run() {
        try {
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            out.writeUTF(fileJson);
            out.flush();
            DataInputStream is = new DataInputStream(socket.getInputStream());
            // 循环一直接收当前socket发来的消息
            int length = 0;
            byte[] b = new byte[1024];
            long fileSize = is.readLong();
            String url = "D:\\download\\"+file.getName();
            DataOutputStream dos = new DataOutputStream(new FileOutputStream(url));
            int hasDownloaded = 0;
            // 2、把socket输入流写到文件输出流中去
            while ((length = is.read(b)) != -1) {
                dos.write(b, 0, length);
                dos.flush();
                hasDownloaded += length;
                System.out.println("当前大小：" + hasDownloaded);
                System.out.println("总大小：" + fileSize);
                //这里通过先前传递过来的文件大小作为参照，因为该文件流不能自主停止，所以通过判断文件大小来跳出循环
                if (length < 1024) {
                    System.out.println("结束下载");
                    break;
                }
            }
            dos.close();
            System.out.println("文件:保存成功");
            FontUIResource font = new FontUIResource("微软雅黑", 0, 20);
            UIManager.put("OptionPane.buttonFont", font);
            UIManager.put("OptionPane.messageFont", font);
            JOptionPane.showMessageDialog(null, "下载成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
