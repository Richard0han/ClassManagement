package com.classmanagement.client.ui;

import com.classmanagement.client.bean.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * ClassManager
 *
 * @author Richard-J
 * @description 聊天主面板
 * @date 2019.03
 */

public class MainPanel extends JFrame implements ActionListener {
    private User user;
    private ImageIcon background = new ImageIcon("images\\mainPanel.png");
    private JLabel backPic = new JLabel(background);
    private JLabel portraitLabel;
    private JLabel nicknameLabel;
    private JLabel signatureLabel;
//    private JLabel weatherLabel;
    private JPanel mainPanel;

    public MainPanel(User user){
        super("微校");

        backPic.setBounds(0, 0, 450, 920);
        mainPanel = (JPanel)this.getContentPane();
        mainPanel.setOpaque(false);
        mainPanel.setLayout(null);

        this.user = user;
        ImageIcon portrait = new ImageIcon("images\\portrait\\"+user.getPortrait()+".jpg");
        portrait.setImage(portrait.getImage().getScaledInstance(100, 100,
                Image.SCALE_DEFAULT));
        portraitLabel =new JLabel(portrait);
        nicknameLabel = new JLabel(user.getNickname());
        signatureLabel = new JLabel(user.getSignature());
        nicknameLabel.setFont(new Font("微软雅黑",1,30));
        nicknameLabel.setForeground(Color.GRAY);

        signatureLabel.setFont(new Font("黑体",1,20));
        nicknameLabel.setForeground(Color.LIGHT_GRAY);

        portraitLabel.setBounds(10,10,100,100);
        nicknameLabel.setBounds(130,10,150,50);
        signatureLabel.setBounds(130,75,190,20);

        mainPanel.add(portraitLabel);
        mainPanel.add(nicknameLabel);
        mainPanel.add(signatureLabel);
//        mainPanel.add(weatherLabel);

        this.getLayeredPane().setLayout(null);
        this.setResizable(false);
        this.getLayeredPane().add(backPic, new Integer(Integer.MIN_VALUE));
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) screenSize.getWidth()/2;
        int y = (int) screenSize.getHeight() /20;
        this.setBounds(x, y, 450, 920);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ImageIcon win = new ImageIcon("images\\win.jpg");
        this.setIconImage(win.getImage());
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
