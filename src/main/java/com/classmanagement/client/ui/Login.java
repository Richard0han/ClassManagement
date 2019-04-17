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
    private static final int WIDTH = 360;
    private static final int HEIGHT = 275;
    private JButton confirm;
    private JButton cancel;
    String userName;
    String pwd;
    String cookie;
    private JTextField stuNoField;
    private JPasswordField passwordField;
    private static String dbstu_no;

    public Login() {
        super("登录页面");

        this.getContentPane().setLayout(null);
        this.setSize(WIDTH, HEIGHT);

        JLabel stuNoLabel = new JLabel("学号");
        JLabel pwdLabel = new JLabel("密码");

        confirm = new JButton("登录");
        cancel = new JButton("退出");
        stuNoLabel.setBounds(35, 101, 72, 18);
        pwdLabel.setBounds(35, 134, 72, 18);
        confirm.setBounds(60, 178, 93, 37);
        cancel.setBounds(191, 178, 93, 37);
        stuNoLabel.setForeground(Color.gray);
        pwdLabel.setForeground(Color.gray);
        confirm.setForeground(Color.gray);
        cancel.setForeground(Color.gray);
        confirm.setBackground(Color.white);
        cancel.setBackground(Color.white);

        ImageIcon headPicture = new ImageIcon("images\\portrait\\0.jpg");
        headPicture.setImage(headPicture.getImage().getScaledInstance(75, 75,
                Image.SCALE_SMOOTH));
        JLabel headLabel = new JLabel();
        headLabel.setIcon(headPicture);
        headLabel.setBounds(125, 13, 75, 75);
        ImageIcon images = new ImageIcon("images\\background.png");
        JLabel logoLabel = new JLabel(images);
        this.setIconImage(new ImageIcon("images\\win.jpg").getImage());
        logoLabel.setBounds(0, 0, images.getIconWidth(), images.getIconHeight());


        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;
        int windowsWidth = this.getWidth();
        int windowsHeight = this.getHeight();
        this.setBounds((width - windowsWidth) / 2, (height - windowsHeight) / 2, windowsWidth, windowsHeight);

        stuNoField = new JTextField("", 20);
        passwordField = new JPasswordField("", 20);
        stuNoField.setBounds(90, 97, 197, 27);
        passwordField.setBounds(90, 132, 197, 27);

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
                            new MainUI(user);
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



