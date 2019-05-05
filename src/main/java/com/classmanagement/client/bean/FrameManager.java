package com.classmanagement.client.bean;

import com.classmanagement.client.ui.ChatFrame;

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
    public static User self;
}
