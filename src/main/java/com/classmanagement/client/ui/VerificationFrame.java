package com.classmanagement.client.ui;

import com.classmanagement.client.bean.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static com.classmanagement.client.ui.AddForumFrame.students;

/**
 * @program: client
 * @description:
 * @author: Mr.Zhang
 * @create: 2019-05-07 23:26
 **/


public class VerificationFrame extends JFrame implements ActionListener {
    private static final int WIDTH = 385;
    private static final int HEIGHT = 225;
    private JLabel label;
    private JButton sure;
    private JButton out;
    private String num;
    User user;

    public VerificationFrame(String stuNo, String name) {
        super("验证面板");
        user = new User();
        num = stuNo;
        this.getContentPane().setLayout(null);
        this.setSize(WIDTH, HEIGHT);
        this.setIconImage(new ImageIcon("images\\win.jpg").getImage());
        this.getContentPane().setBackground(Color.WHITE);

        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;
        int windowsWidth = this.getWidth();
        int windowsHeight = this.getHeight();
        this.setBounds((width - windowsWidth) / 2, (height - windowsHeight) / 2, windowsWidth, windowsHeight);

        label = new JLabel("是否添加" + name + "进入群聊");
        sure = new JButton("确定");
        out = new JButton("退出");

        label.setBounds(49, 13, 258, 81);
        sure.setBounds(14, 107, 125, 50);
        sure.setBackground(Color.WHITE);
        out.setBounds(228, 107, 125, 50);
        out.setBackground(Color.WHITE);

        Font fm = new Font("黑体", 0, 20);
        label.setFont(fm);
        sure.setFont(fm);
        out.setFont(fm);

        this.add(label);
        this.add(sure);
        this.add(out);
        this.setVisible(true);
        sure.addActionListener(this);
        out.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == sure) {
            user.setStuNo(num);
            students.add(user);
            this.dispose();
        }
        if (e.getSource() == out) {
            this.dispose();
        }
    }
}
