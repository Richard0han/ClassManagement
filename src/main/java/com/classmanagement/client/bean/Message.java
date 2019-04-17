package com.classmanagement.client.bean;

import javax.swing.*;
import java.awt.*;

/**
 * ClassManagement
 *
 * @author Richard-J
 * @description 消息类
 * @date 2019.04
 */

public class Message {
    private int  portrait;
    private boolean self;

    public Image getPortrait() {
        ImageIcon imageIcon = new ImageIcon("images\\portrait\\"+portrait+".jpg");
        return imageIcon.getImage();
    }

    public void setPortrait(int portrait) {
        this.portrait = portrait;
    }

    public boolean isSelf() {
        return self;
    }

    public void setSelf(boolean self) {
        this.self = self;
    }
}
