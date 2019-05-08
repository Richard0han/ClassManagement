package com.classmanagement.client.ui;

import com.classmanagement.client.bean.AdministratorLog;
import com.classmanagement.client.bean.Announcement;
import com.classmanagement.client.bean.ChatInfo;
import com.classmanagement.client.bean.User;
import com.classmanagement.client.dao.AddData;
import com.classmanagement.client.utils.TextFieldLimit;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * @program: client
 * @description:
 * @author: Mr.Zhang
 * @create: 2019-05-06 19:45
 **/


public class AnnFrame extends JFrame implements ActionListener {
    private static final int WIDTH = 382;
    private static final int HEIGHT = 523;
    private JLabel label;
    private JLabel label1;
    private JTextField textField;
    private JTextArea textArea;
    private JButton agree;
    private JButton disagree;
    private Announcement announcement;
    private int num;
    private String stuNo;

    public AnnFrame(User user) {
        super("公告编辑页面");
        num = user.getClassId();
        stuNo = user.getStuNo();
        ImageIcon win = new ImageIcon("images\\win.jpg");
        this.setIconImage(win.getImage());
        this.getContentPane().setLayout(null);
        this.setSize(WIDTH, HEIGHT);
        ImageIcon images = new ImageIcon("images\\dataPanel.png");
        JLabel voteLabel = new JLabel(images);
        voteLabel.setBounds(0, 0, images.getIconWidth(), images.getIconHeight());

        label = new JLabel("标题");
        label1 = new JLabel("正文");
        textField = new JTextField();
        textArea = new JTextArea();
        agree = new JButton("确认");
        disagree = new JButton("取消");
        agree.setBackground(Color.WHITE);
        disagree.setBackground(Color.WHITE);
        textField.setDocument(new TextFieldLimit(15));

        label.setBounds(39, 0, 276, 52);
        label1.setBounds(39, 85, 276, 47);
        textField.setBounds(39, 49, 276, 39);
        agree.setBounds(27, 400, 113, 52);
        disagree.setBounds(221, 400, 113, 52);


        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(39, 132, 276, 250);
        scrollPane.setViewportView(textArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        Font mf = new Font("宋体", 0, 20);
        Font fm = new Font("黑体", 0, 20);
        label.setFont(mf);
        textArea.setFont(mf);
        label1.setFont(mf);
        textField.setFont(mf);
        agree.setFont(fm);
        disagree.setFont(fm);

        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;
        int windowsWidth = this.getWidth();
        int windowsHeight = this.getHeight();
        this.setBounds((width - windowsWidth) / 2, (height - windowsHeight) / 2, windowsWidth, windowsHeight);


        this.add(label1);
        this.add(textField);
        this.add(label);
        this.add(scrollPane);
        this.add(agree);
        this.add(disagree);
        this.add(voteLabel);
        this.setVisible(true);


        agree.addActionListener(this);
        disagree.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == agree) {
            announcement = new Announcement();
            String s = null;
            String t = null;
            announcement.setForumId(num);
            announcement.setContent(textArea.getText());
            announcement.setTitle(textField.getText());
            announcement.setSender(stuNo);
            if (AddData.addAnnouncement(announcement) == true) {
                try {
                    AdministratorLog al = new AdministratorLog();
                    al.setAnnouncement(announcement);
                    MainFrame.sendLog(al);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                this.dispose();
            }
        } else if (e.getSource() == disagree) {
            this.dispose();
        }
    }
}
