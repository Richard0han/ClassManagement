package com.classmanagement.client.ui;

import com.classmanagement.client.bean.User;

import javax.swing.*;
import java.awt.*;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * ClassManagement
 *
 * @author Richard-J
 * @description 获取共享画板
 * @date 2019.05
 */

public class GetDrawFrame extends JFrame{
    Graphics g;
    int x1 = 0, y1 = 0;
    DataOutputStream os;
    Graphics2D g0;

    public GetDrawFrame(User user) throws IOException {
        this.setIconImage(new ImageIcon("images\\win.jpg").getImage());
        this.setTitle("即时画板(正在共享" + user.getNickname() + "的画板)");
        this.setSize(900, 900);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        g = this.getGraphics();
    }

    public void draw(int x, int y) {
        Graphics2D g0 = (Graphics2D) g;
        g0.setStroke(new BasicStroke(10));
        g0.setColor(Color.BLACK);
        g0.drawLine(x1, y1, x1, y1);
        x1 = x;
        y1 = y;
    }
}
