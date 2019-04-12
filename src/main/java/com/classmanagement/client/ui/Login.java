package com.classmanagement.client.ui;

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
    private JFrame myWindow;
    private JTextField stuNoField;
    private JPasswordField passwordField;
    private static String dbstu_no;

    public Login() {
        myWindow = new JFrame("登录页面");

        myWindow.getContentPane().setLayout(null);
        myWindow.setSize(WIDTH, HEIGHT);

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

        ImageIcon headPicture = new ImageIcon("images\\headPicture.jpg");
        headPicture.setImage(headPicture.getImage().getScaledInstance(75, 75,
                Image.SCALE_DEFAULT));
        JLabel headLabel = new JLabel();
        headLabel.setIcon(headPicture);
        headLabel.setBounds(125, 13, headPicture.getIconWidth(), headPicture.getIconHeight());
        ImageIcon images = new ImageIcon("images\\background.png");
        JLabel logoLabel = new JLabel(images);
        myWindow.setIconImage(new ImageIcon("images\\win.jpg").getImage());
        logoLabel.setBounds(0, 0, images.getIconWidth(), images.getIconHeight());


        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;
        int windowsWidth = myWindow.getWidth();
        int windowsHeight = myWindow.getHeight();
        myWindow.setBounds((width - windowsWidth) / 2, (height - windowsHeight) / 2, windowsWidth, windowsHeight);

        stuNoField = new JTextField("", 20);
        passwordField = new JPasswordField("", 20);
        stuNoField.setBounds(90, 97, 197, 27);
        passwordField.setBounds(90, 132, 197, 27);

        WindowDestroyer myListener = new WindowDestroyer();
        myWindow.addWindowListener((WindowListener) myListener);

        Font fm = new Font(" 黑体", Font.BOLD, 20);
        Font mf = new Font(" 黑体", Font.PLAIN, 20);
        stuNoLabel.setFont(fm);
        pwdLabel.setFont(fm);
        stuNoField.setFont(fm);
        confirm.setFont(mf);
        cancel.setFont(mf);

        myWindow.add(stuNoLabel);
        myWindow.add(pwdLabel);
        myWindow.add(confirm);
        myWindow.add(stuNoField);
        myWindow.add(passwordField);
        myWindow.add(cancel);
        myWindow.add(headLabel);
        myWindow.add(logoLabel);


        myWindow.setVisible(true);
        cancel.addActionListener(this);
        confirm.addActionListener(this);
    }

    public class WindowDestroyer extends WindowAdapter {
        @Override
        public void windowClosing(WindowEvent e) {
            System.exit(0);
        }

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
//交给jiarenxu

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



