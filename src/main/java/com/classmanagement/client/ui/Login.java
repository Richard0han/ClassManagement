package com.classmanagement.client.ui;

import com.classmanagement.client.bean.User;
import com.classmanagement.client.dao.GetData;
import com.classmanagement.client.dao.IsFirstLogin;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.awt.event.*;


import static com.classmanagement.client.utils.LoginVerification.simulateLogin;

/**
 * @program: client
 * @description: 登陆界面
 * @author: Mr.Zhang
 * @create: 2019-03-29 22:08
 **/
public class Login extends JFrame implements ActionListener {
    private JButton confirm;
    private JButton cancel;
    private String userName;
    private String pwd;
    private String cookie;
    private JTextField stuNoField;
    private JPasswordField passwordField;

    public Login() {
        super("登录页面");

        this.getContentPane().setLayout(null);

        JLabel stuNoLabel = new JLabel("学号");
        JLabel pwdLabel = new JLabel("密码");

        confirm = new JButton("登录");
        cancel = new JButton("退出");
        stuNoLabel.setBounds(120, 140, 200, 50);
        pwdLabel.setBounds(120, 190, 200, 50);
        confirm.setBounds(80, 280, 150, 50);
        cancel.setBounds(330, 280, 150, 50);
        stuNoLabel.setForeground(Color.gray);
        pwdLabel.setForeground(Color.gray);
        confirm.setForeground(Color.gray);
        cancel.setForeground(Color.gray);
        confirm.setBackground(Color.white);
        cancel.setBackground(Color.white);

        ImageIcon headPicture = new ImageIcon("images\\portrait\\0.jpg");
        headPicture.setImage(headPicture.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
        JLabel headLabel = new JLabel();
        headLabel.setIcon(headPicture);
        headLabel.setBounds(250, 40,100, 100);
        ImageIcon images = new ImageIcon("images\\background.png");
        JLabel logoLabel = new JLabel(images);
        this.setIconImage(new ImageIcon("images\\win.jpg").getImage());
        logoLabel.setBounds(0, 0, images.getIconWidth(), images.getIconHeight());

        stuNoField = new JTextField("", 20);
        passwordField = new JPasswordField("", 20);
        stuNoField.setBounds(190, 150, 250, 40);
        passwordField.setBounds(190, 200, 250, 40);

        Font fm = new Font(" 黑体", Font.BOLD, 20);
        Font mf = new Font(" 黑体", Font.PLAIN, 20);
        stuNoLabel.setFont(fm);
        pwdLabel.setFont(fm);
        stuNoField.setFont(fm);
        confirm.setFont(mf);
        cancel.setFont(mf);

        this.add(stuNoLabel);
        this.add(pwdLabel);
        this.add(confirm);
        this.add(stuNoField);
        this.add(passwordField);
        this.add(cancel);
        this.add(headLabel);
        this.add(logoLabel);

        this.setSize(images.getIconWidth(),images.getIconHeight());
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        cancel.addActionListener(this);
        confirm.addActionListener(this);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == confirm) {
            try {
                FontUIResource font = new FontUIResource("微软雅黑", 0, 20);
                UIManager.put("OptionPane.buttonFont", font);
                UIManager.put("OptionPane.messageFont", font);
                userName = stuNoField.getText();
                pwd = String.valueOf(passwordField.getPassword());
                if (userName.equals("")) {
                    JOptionPane.showMessageDialog(null, "账号为空！", "提示", JOptionPane.ERROR_MESSAGE);
                } else {
                    try {
                        cookie = simulateLogin(userName, pwd);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }

                    if (cookie == null) {
                        JOptionPane.showMessageDialog(null, "账号或密码输入错误！", "提示", JOptionPane.ERROR_MESSAGE);
                    } else {
                        if (IsFirstLogin.isFirstLogin(userName)) {
                            new BasicData(cookie);
                            this.dispose();
                        } else {
                            User user = GetData.getUser(userName);
                            new MainFrame(user);
                            this.dispose();
                        }
                    }
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        } else if (e.getSource() == cancel) {
            System.exit(0);
        }
    }
}



