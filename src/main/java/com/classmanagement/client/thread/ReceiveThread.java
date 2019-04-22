package com.classmanagement.client.thread;

import com.classmanagement.client.bean.FrameManager;
import com.classmanagement.client.ui.ChatFrame;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedHashMap;

/**
 * ClassManagement
 *
 * @author Richard-J
 * @description 消息接受线程
 * @date 2019.04
 */

public class ReceiveThread extends Thread {
    public ReceiveThread() {
        FrameManager.frameManager = new LinkedHashMap<Integer, ChatFrame>();
    }

    @Override
    public void run() {
        try {
            Socket socket = new Socket(FrameManager.self.getNetAddress(), FrameManager.self.getPort());
            while (true) {
                InputStream is = socket.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));


            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
