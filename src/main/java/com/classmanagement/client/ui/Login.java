package com.classmanagement.client.ui;

import com.classmanagement.client.bean.FrameManager;
import com.classmanagement.client.dao.GetData;
import com.classmanagement.client.dao.IsFirstLogin;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.awt.event.*;
import java.util.LinkedHashMap;


import static com.classmanagement.client.utils.LoginVerification.simulateLogin;

/**
 * @program: client
 * @description: 登陆界面
 * @author: Mr.Zhang
 * @create: 2019-03-29 22:08
 **/
public class Login extends JFrame implements ActionListener {
    private JButton confirm;
    private String userName;
    private String pwd;
    private String cookie;
    private JTextField stuNoField;
    private JPasswordField passwordField;
    private JPanel back;

    public Login() {
        super("登录微校");

        back = (JPanel) this.getContentPane();
        back.setLayout(null);

        confirm = new JButton("Sign up >");
        confirm.setBounds(489, 332, 80, 25);
        confirm.setForeground(Color.WHITE);
        confirm.setBorder(null);
        confirm.setBackground(new Color(136, 202, 201));
        confirm.setOpaque(true);

        ImageIcon images = new ImageIcon("images\\background.png");
        JLabel logoLabel = new JLabel(images);
        this.setIconImage(new ImageIcon("images\\win.jpg").getImage());
        logoLabel.setBounds(0, 0, images.getIconWidth(), images.getIconHeight());

        stuNoField = new JTextField("", 20);
        passwordField = new JPasswordField("", 20);
        stuNoField.setBounds(440, 175, 190, 30);
        passwordField.setBounds(440, 242, 190, 30);
        stuNoField.setBorder(null);
        passwordField.setBorder(null);

        Font fm = new Font("黑体", Font.BOLD, 20);
        Font mf = new Font("黑体", 0, 10);
        stuNoField.setFont(fm);
        confirm.setFont(mf);
        back.add(confirm);
        back.add(stuNoField);
        back.add(passwordField);
        back.add(logoLabel);


        this.getLayeredPane().setLayout(null);
        this.setLayout(null);
        this.setSize(714, 525);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
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
                            this.dispose();
                            new BasicData(cookie);
                        } else {
                            FrameManager.self = GetData.getUser(userName);
                            this.dispose();
                            FrameManager.whisperFrameManager = new LinkedHashMap<String, ChatFrame>();
                            FrameManager.forumFrameManager = new LinkedHashMap<Integer, ChatFrame>();
                            FrameManager.drawFrameManager = new LinkedHashMap<String, GetDrawFrame>();
                            FrameManager.mainFrame = new MainFrame();
                        }
                    }
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }
}



