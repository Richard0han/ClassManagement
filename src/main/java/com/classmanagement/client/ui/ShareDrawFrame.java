package com.classmanagement.client.ui;

import com.alibaba.fastjson.JSON;
import com.classmanagement.client.bean.ChatInfo;
import com.classmanagement.client.bean.File;
import com.classmanagement.client.bean.FrameManager;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;

import javax.swing.*;

/**
 * ClassManagement
 *
 * @author Richard-J
 * @description 共享画板
 * @date 2019.05
 */


public class ShareDrawFrame extends JFrame implements MouseMotionListener {

    Graphics g;
    int x1 = 0, y1 = 0;
    Graphics2D g0;
    final ChatInfo chatInfo;
    ByteArrayOutputStream bos;
    ObjectOutputStream oos;
    DatagramSocket socket;
    InetAddress add;

    public ShareDrawFrame(final ChatInfo chatInfo) throws IOException {
        this.chatInfo = chatInfo;
        this.setIconImage(new ImageIcon("images\\win.jpg").getImage());
        this.setTitle("即时画板(正在共享画板)");
        this.setSize(900, 900);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        Graphics g = this.getGraphics();
        g0 = (Graphics2D) g;
        g0.setStroke(new BasicStroke(10));
        g0.setColor(Color.BLACK);
        //设置鼠标监听器
        this.addMouseMotionListener(this);
//        bos = new ByteArrayOutputStream();
//        oos = new ObjectOutputStream(bos);

        //把要发送的信息转换为字节数组
        //网络上发送任何东西都是发送字节数组

        socket = new DatagramSocket();
        //UDP通信
        socket.setSendBufferSize(1024 * 1024);
        add = InetAddress.getByName(chatInfo.getClassmate().getNetAddress());
    }

    public void draw(int x, int y) {
        g0.drawLine(x1, y1, x1, y1);
        x1 = x;
        y1 = y;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        draw(x, y);
        chatInfo.setX(x1);
        chatInfo.setY(y1);
        try {
            bos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(bos);
            oos.writeObject(chatInfo);
            //最终

            byte[] b = bos.toByteArray();
            DatagramPacket p = new DatagramPacket(b, 0, b.length, add, chatInfo.getClassmate().getPort());
            //发送
            socket.send(p);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
