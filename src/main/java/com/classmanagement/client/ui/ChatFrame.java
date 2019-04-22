package com.classmanagement.client.ui;

import com.classmanagement.client.server.EchoClient;
import com.classmanagement.client.bean.ChatInfo;
import com.classmanagement.client.bean.Forum;
import com.classmanagement.client.bean.User;
import com.classmanagement.client.utils.ChatTextPane;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

/**
 * ClassManagement
 *
 * @author Richard-J
 * @description 聊天界面
 * @date 2019.04
 */

public class ChatFrame extends JFrame implements ActionListener {
    private int type;
    private User self, classmate;
    private Forum forum;
    private JPanel backPanel = (JPanel) this.getContentPane();
    private JPanel infoPanel = new JPanel();
    private JLabel nicknameLabel, signatureLabel, portraitLabel;
    private ChatTextPane receivePane, sendPane;
    private JScrollPane receivePanel, sendPanel;
    private JLabel download = new JLabel(new ImageIcon("images\\chatFunction\\download.png")), draw = new JLabel(new ImageIcon("images\\chatFunction\\draw.png")), history = new JLabel(new ImageIcon("images\\chatFunction\\history.png"));
    private JLabel downloadLabel = new JLabel("共享文件"), drawLabel = new JLabel("即时画板"), historyLabel = new JLabel("历史消息");
    private JButton sendButton = new JButton("发送");
    private JButton cancelButton = new JButton("取消");
    EchoClient echoClient;

    public ChatFrame(ChatInfo chatInfo) {
        setInfoPanel(chatInfo);

        backPanel.setBackground(Color.WHITE);
        Font font = new Font("黑体", 0, 20);
        receivePane = new ChatTextPane();
        receivePane.setEditable(false);
        receivePane.setFont(font);
        receivePane.setBackground(Color.WHITE);
        sendPane = new ChatTextPane();
        sendPane.setLayout(new GridLayout(1, 1));
        sendPane.setBackground(Color.WHITE);
        sendPane.setFont(font);
        receivePanel = new JScrollPane(receivePane);
        receivePanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        receivePanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        receivePanel.setBounds(0, 0, 650, 350);
        sendPanel = new JScrollPane(sendPane);
        sendPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        sendPanel.setBounds(0, 390, 650, 170);
        Font buttonFont = new Font("黑体", 0, 20);
        sendButton.setFont(buttonFont);
        cancelButton.setFont(buttonFont);
        sendButton.setBounds(535, 563, 80, 30);
        cancelButton.setBounds(435, 563, 80, 30);
        sendButton.setBackground(new Color(217, 135, 89));
        sendButton.setForeground(Color.WHITE);
        cancelButton.setBackground(Color.WHITE);

        backPanel.add(infoPanel);
        backPanel.add(receivePanel);
        backPanel.add(sendPanel);
        backPanel.add(sendButton);
        backPanel.add(cancelButton);
        backPanel.setBounds(0, 0, 1000, 650);

        this.setTitle("正在和" + chatInfo.getClassmate().getNickname() + "(" + chatInfo.getClassmate().getName() + ")聊天");
        this.setIconImage(new ImageIcon("images\\win.jpg").getImage());
        this.setLayout(null);
        this.setSize(1000, 650);
        this.setLocationRelativeTo(null);
        this.getLayeredPane().setLayout(null);
        this.setVisible(true);

        sendButton.addActionListener(this);
        cancelButton.addActionListener(this);
    }

    private void setInfoPanel(ChatInfo chatInfo) {
        type = chatInfo.getType();
        self = chatInfo.getSelf();
        infoPanel.setBounds(649, 30, 329, 565);
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setLayout(null);
        infoPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        ImageIcon imageIcon;
        if (type == 0) {
            classmate = chatInfo.getClassmate();
            imageIcon = new ImageIcon("images\\portrait\\" + classmate.getPortrait() + ".jpg");
            nicknameLabel = new JLabel(classmate.getNickname() + "(" + classmate.getName() + ")", JLabel.CENTER);
            signatureLabel = new JLabel(classmate.getSignature(), JLabel.CENTER);
            signatureLabel.setFont(new Font("黑体", 0, 15));
            signatureLabel.setBounds(15, 180, 308, 50);
            signatureLabel.setForeground(Color.LIGHT_GRAY);
            infoPanel.add(signatureLabel);
        } else {
            forum = chatInfo.getForum();
            imageIcon = new ImageIcon("images\\portrait\\forum" + forum.getIsClass() + ".jpg");
            nicknameLabel = new JLabel(forum.getName(), JLabel.CENTER);
        }
        nicknameLabel.setFont(new Font("宋体", 1, 25));
        nicknameLabel.setBounds(70, 150, 200, 50);
        infoPanel.add(nicknameLabel);
        imageIcon.setImage(imageIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
        portraitLabel = new JLabel(imageIcon);
        portraitLabel.setBounds(120, 40, 100, 100);
        download.setBounds(40, 280, 48, 48);
        draw.setBounds(40, 340, 56, 47);
        history.setBounds(40, 400, 54, 48);
        Font describe = new Font("隶书", 0, 24);
        downloadLabel.setFont(describe);
        drawLabel.setFont(describe);
        historyLabel.setFont(describe);
        downloadLabel.setBounds(150, 280, 100, 40);
        drawLabel.setBounds(150, 340, 100, 40);
        historyLabel.setBounds(150, 400, 100, 40);
        infoPanel.add(portraitLabel);
        infoPanel.add(download);
        infoPanel.add(draw);
        infoPanel.add(history);
        infoPanel.add(downloadLabel);
        infoPanel.add(drawLabel);
        infoPanel.add(historyLabel);

        try {
            echoClient = new EchoClient();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == sendButton) {
//            连接服务器发送，应该是Boolean型数据
            StyledDocument doc = sendPane.getStyledDocument();
            append(doc, self);
            sendPane.setText("");
        } else if (e.getSource() == cancelButton) {
            this.dispose();
        }

    }

    public void append(StyledDocument content, User user) {
        ImageIcon imageIcon = new ImageIcon("images\\portrait\\" + user.getPortrait() + ".jpg");
        imageIcon.setImage(imageIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH));
        receivePane.insertIcon(imageIcon);
        Document receiveMeg = receivePane.getStyledDocument();
        SimpleAttributeSet as = new SimpleAttributeSet();
        StyleConstants.setFontSize(as, 24);
        try {
            receiveMeg.insertString(receiveMeg.getLength(), self.getNickname() + "\n\n", as);
            int end = 0;
            while (end < content.getLength()) {
                Element e0 = content.getCharacterElement(end);

                SimpleAttributeSet asl = new SimpleAttributeSet();
                StyleConstants.setForeground(asl, StyleConstants.getForeground(e0.getAttributes()));
                StyleConstants.setFontSize(asl, StyleConstants.getFontSize(e0.getAttributes()));
                StyleConstants.setFontFamily(asl, StyleConstants.getFontFamily(e0.getAttributes()));
                String text = e0.getDocument().getText(end, e0.getEndOffset() - end);

                if ("icon".equals(e0.getName())) {
                    receiveMeg.insertString(receiveMeg.getLength(), text, e0.getAttributes());
                } else {
                    receiveMeg.insertString(receiveMeg.getLength(), text, asl);
                }

                end = e0.getEndOffset();
            }
            receiveMeg.insertString(receiveMeg.getLength(), "\n\n", as);

        } catch (BadLocationException ex) {
            ex.printStackTrace();
        }
    }

    public void shake() {

        Point p = this.getLocationOnScreen();
        int times = 10;
        for (int i = 0; i < times; i++) {
            if (i % 2 == 0) {
                setLocation(p.x + 5, p.y + 5);
            } else {
                setLocation(p.x - 5, p.y - 5);
            }
            try {
                Thread.sleep(50);
            } catch (InterruptedException e1) {

                e1.printStackTrace();
            }
        }
    }
}
