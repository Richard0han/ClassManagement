package com.classmanagement.client.thread;

import com.alibaba.fastjson.JSON;
import com.classmanagement.client.bean.ChatInfo;
import com.classmanagement.client.bean.FrameManager;
import com.classmanagement.client.bean.User;
import com.classmanagement.client.ui.ChatFrame;
import com.classmanagement.client.ui.GetDrawFrame;

import javax.swing.text.*;
import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;
import java.util.LinkedHashMap;

/**
 * ClassManagement
 *
 * @author Richard-J
 * @description 消息接受线程
 * @date 2019.04
 */

public class ReceiveThread extends Thread {

    public ReceiveThread() throws IOException {
    }

    @Override
    public void run() {

        try {
            DatagramSocket serverSocket = new DatagramSocket(FrameManager.self.getPort());
            while (true) {
                byte[] b = new byte[1024 * 64];
                //数据包
                DatagramPacket pack = new DatagramPacket(b, b.length);
                serverSocket.receive(pack);

                //把字节数组转换成SendMsg对象
                //pack.getLength()接收到的字节数
                // 字节数组输入流
                ByteArrayInputStream bis = new ByteArrayInputStream(b, 0, pack.getLength());
                ObjectInputStream ois = new ObjectInputStream(bis);
                ChatInfo chatInfo = (ChatInfo) ois.readObject();
                if (!chatInfo.isDraw()) {
                    if (chatInfo.getType() == 0) {
                        if (chatInfo.getClassmate().getStuNo().equals(FrameManager.self.getStuNo())) {
                            User temp = chatInfo.getSelf();
                            chatInfo.setSelf(chatInfo.getClassmate());
                            chatInfo.setClassmate(temp);
                            findWin(chatInfo);
                        }
                    } else {
                        if ((chatInfo.getForum().getId() == FrameManager.self.getClassId()) && (!(chatInfo.getSelf().getStuNo().equals(FrameManager.self.getStuNo())))) {
                            chatInfo.setClassmate(chatInfo.getSelf());
                            chatInfo.setSelf(FrameManager.self);
                            findWin(chatInfo);
                        }
                    }
                } else {
                    GetDrawFrame getDrawFrame = findDrawFrame(chatInfo);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static ChatFrame findWin(ChatInfo chatInfo) {
        ChatFrame chat = null;
//        查找窗口是否存在
        if (chatInfo.getType() == 0) {
            chat = FrameManager.whisperFrameManager.get(chatInfo.getClassmate().getStuNo());
            if (chat == null) {
                chat = new ChatFrame(chatInfo);
//            窗口加入哈希表
                FrameManager.whisperFrameManager.put(chatInfo.getClassmate().getStuNo(), chat);
            }
        } else {
            chat = FrameManager.forumFrameManager.get(chatInfo.getForum().getId());
            if (chat == null) {
                chat = new ChatFrame(chatInfo);
//            窗口加入哈希表
                FrameManager.forumFrameManager.put(chatInfo.getForum().getId(), chat);
            }
        }

        StyledDocument content = chatInfo.getContent();
        if (content != null) {
            int end = 0;
            Element e0 = content.getCharacterElement(end);
            try {
                String text = e0.getDocument().getText(end, e0.getEndOffset() - end);
                String shake = "[窗口抖动]\n";
                if (text.equals(shake)) {
                    chat.shake(1);
                }
                chat.append(content, chatInfo.getClassmate());
            } catch (BadLocationException e) {
                e.printStackTrace();
            }


        }
        if (!chat.isVisible()) {
            chat.setVisible(true);
        }
        return chat;
    }

    public static GetDrawFrame findDrawFrame(ChatInfo chatInfo) {
        GetDrawFrame getDrawFrame = null;
        getDrawFrame = FrameManager.drawFrameManager.get(chatInfo.getSelf().getStuNo());
        if (getDrawFrame == null) {
            try {
                getDrawFrame = new GetDrawFrame(chatInfo.getSelf());
            } catch (IOException e) {
                e.printStackTrace();
            }
//            窗口加入哈希表
            FrameManager.drawFrameManager.put(chatInfo.getSelf().getStuNo(), getDrawFrame);
        }
        getDrawFrame.draw(chatInfo.getX(), chatInfo.getY());
        if (!getDrawFrame.isVisible()) {
            getDrawFrame.setVisible(true);
        }

        return getDrawFrame;
    }
}