package com.classmanagement.client.bean;

import com.classmanagement.client.server.Server;
import com.classmanagement.client.ui.ChatFrame;
import com.classmanagement.client.ui.GetDrawFrame;
import com.classmanagement.client.ui.MainFrame;

import java.net.ServerSocket;
import java.util.LinkedHashMap;

/**
 * ClassManagement
 *
 * @author Richard-J
 * @description
 * @date 2019.04
 */

public class FrameManager {
    public static LinkedHashMap<String, ChatFrame> whisperFrameManager;
    public static LinkedHashMap<Integer,ChatFrame> forumFrameManager;
    public static LinkedHashMap<String, GetDrawFrame> drawFrameManager;
    public static User self;
    public static MainFrame mainFrame;
    public static Server server;
}
