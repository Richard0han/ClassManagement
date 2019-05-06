package com.classmanagement.client.ui;

import com.classmanagement.client.bean.ChatInfo;
import com.classmanagement.client.bean.Forum;
import com.classmanagement.client.bean.User;
import com.classmanagement.client.dao.GetData;
import com.classmanagement.client.thread.SendFileThread;
import com.classmanagement.client.utils.ChatTextPane;
import jdk.nashorn.internal.objects.annotations.Function;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import java.util.List;

/**
 * ClassManagement
 *
 * @author Richard-J
 * @description 聊天界面
 * @date 2019.04
 */

public class ChatFrame extends JFrame implements ActionListener {
    private int type;
    private ChatInfo chatInfo;
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
    private JButton sendFile = new JButton();
    private JButton shakeWin = new JButton("震动");
    private JButton addPic = new JButton("图片");
    private ByteArrayOutputStream bos;
    private ObjectOutputStream oos;
    private JLabel toHomeLabel = new JLabel("  资料");
    private List<com.classmanagement.client.bean.File> fileList;
    private JPanel functionPanel = new JPanel();
    private JPanel filePanel = new JPanel();
    private CardLayout functionCard = new CardLayout();

    public ChatFrame(ChatInfo chatInfo) {
        this.chatInfo = chatInfo;
        functionPanel.setBounds(649, 30, 329, 565);
        functionPanel.setBackground(Color.WHITE);
        functionPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        functionPanel.setLayout(functionCard);
        setInfoPanel(chatInfo);
        setFilePanel();
        backPanel.add(functionPanel);

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
        sendFile.setBounds(40, 355, 35, 35);
        backPanel.add(sendFile);
        shakeWin.setBounds(75, 355, 35, 35);
        addPic.setBounds(110, 355, 35, 35);
        Color qqStyleOrange = new Color(190, 132, 99);
        toHomeLabel.setBounds(655, 0, 200, 30);
        toHomeLabel.setForeground(qqStyleOrange);
        toHomeLabel.setFont(new Font("幼圆", 1, 25));
        backPanel.add(toHomeLabel);

        backPanel.add(receivePanel);
        backPanel.add(sendPanel);
        backPanel.add(sendButton);
        backPanel.add(cancelButton);
        backPanel.add(shakeWin);
        backPanel.add(addPic);

        backPanel.setBounds(0, 0, 1000, 650);

        this.setIconImage(new ImageIcon("images\\win.jpg").getImage());
        this.setLayout(null);
        this.setSize(1000, 650);
        this.setLocationRelativeTo(null);
        this.getLayeredPane().setLayout(null);
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.setVisible(true);

        sendButton.addActionListener(this);
        cancelButton.addActionListener(this);
        sendFile.addActionListener(this);
        shakeWin.addActionListener(this);
        addPic.addActionListener(this);
    }

    private void setInfoPanel(ChatInfo chatInfo) {
        type = chatInfo.getType();
        self = chatInfo.getSelf();
        infoPanel.setBounds(0, 0, 329, 565);
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setLayout(null);
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
            this.setTitle("正在和" + chatInfo.getClassmate().getNickname() + "(" + chatInfo.getClassmate().getName() + ")聊天");
        } else {
            forum = chatInfo.getForum();
            imageIcon = new ImageIcon("images\\portrait\\forum" + forum.getIsClass() + ".jpg");
            nicknameLabel = new JLabel(forum.getName(), JLabel.CENTER);
            this.setTitle(chatInfo.getForum().getName() + "(群聊)");
        }
        nicknameLabel.setFont(new Font("宋体", 1, 25));
        nicknameLabel.setBounds(70, 150, 200, 50);
        infoPanel.add(nicknameLabel);
        imageIcon.setImage(imageIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
        portraitLabel = new JLabel(imageIcon);
        portraitLabel.setBounds(120, 40, 100, 100);
        infoPanel.add(portraitLabel);
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

        infoPanel.add(download);
        infoPanel.add(draw);
        infoPanel.add(history);
        infoPanel.add(downloadLabel);
        infoPanel.add(drawLabel);
        infoPanel.add(historyLabel);
        functionPanel.add(infoPanel, "资料");
    }

    private void setFilePanel() {
        Font font = new Font("黑体", 0, 20);
        fileList = GetData.getFile(chatInfo);
        int fileListSize = fileList.size();
        filePanel.setBounds(0, 0, 329, 565);
        filePanel.setBackground(Color.WHITE);
        filePanel.setLayout(null);
        if (fileListSize != 0) {
            JPanel filePanelAccessory = new JPanel(new GridLayout(fileListSize, 1));
            filePanelAccessory.setBackground(Color.WHITE);
            JLabel[] fileLabels = new JLabel[fileListSize];
            com.classmanagement.client.bean.File file;
            for (int i = 0; i < fileListSize; i++) {
                file = fileList.get(i);
                ImageIcon imageIcon = getFileImage(file.getName());
                fileLabels[i] = new JLabel(file.getName(), imageIcon, JLabel.LEFT);
                fileLabels[i].setBounds(0, 50 * i, 325, 50);
                fileLabels[i].setFont(font);
                fileLabels[i].setToolTipText(file.getName());
                filePanelAccessory.add(fileLabels[i]);
            }
            JScrollPane jScrollPane = new JScrollPane(filePanelAccessory);
            jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            jScrollPane.setBackground(Color.WHITE);
            jScrollPane.setBounds(0, 0, 329, 565);


            filePanel.add(jScrollPane);
        } else {
            JLabel notFind = new JLabel(new ImageIcon("images\\noFile.png"));
            notFind.setBounds(30, 120, 280, 285);
            filePanel.add(notFind);
        }
        functionPanel.add(filePanel, "共享文件");
        functionCard.show(functionPanel, "共享文件");
    }

    private ImageIcon getFileImage(String fileName) {
        ImageIcon imageIcon;
        if (fileName.contains(".zip") || fileName.contains(".rar") || fileName.contains(".7z")) {
            imageIcon = new ImageIcon("images\\file\\folder.png");
        } else if (fileName.contains(".jpg") || fileName.contains(".png") || fileName.contains(".gif") || fileName.contains(".jpeg") || fileName.contains(".bmp")) {
            imageIcon = new ImageIcon("images\\file\\picture.png");
        } else if (fileName.contains(".ppt")) {
            imageIcon = new ImageIcon("images\\file\\p.png");
        } else if (fileName.contains(".doc") || fileName.contains(".txt")) {
            imageIcon = new ImageIcon("images\\file\\w.png");
        } else if (fileName.contains(".xls")) {
            imageIcon = new ImageIcon("images\\file\\s.png");
        } else {
            imageIcon = new ImageIcon("images\\file\\none.png");
        }
        return imageIcon;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == sendButton) {
            StyledDocument doc = sendPane.getStyledDocument();
            chatInfo.setContent(doc);
            if (send(chatInfo)) {
                append(doc, self);
            } else {
                FontUIResource font = new FontUIResource("微软雅黑", 0, 20);
                UIManager.put("OptionPane.buttonFont", font);
                UIManager.put("OptionPane.messageFont", font);
                JOptionPane.showMessageDialog(this, "服务器连接失败了，请重新发送", "提示", JOptionPane.ERROR_MESSAGE);
            }
            sendPane.setText("");
        } else if (e.getSource() == cancelButton) {
            this.dispose();
        } else if (e.getSource() == sendFile) {
            FileDialog fileDialog = new FileDialog(this, "发送文件", FileDialog.LOAD);
            fileDialog.setIconImage(new ImageIcon("images\\win.jpg").getImage());
            fileDialog.show();
            if (fileDialog.getFile() != null) {
                String fileName = fileDialog.getFile();
                String sendFileAddress = fileDialog.getDirectory() + "\\" + fileName;
                final File f = new File(sendFileAddress);
                if (f.exists()) {
                    final com.classmanagement.client.bean.File file = new com.classmanagement.client.bean.File();
                    file.setName(fileName);
                    file.setSender(self.getStuNo());
                    file.setFileSize(f.length());
                    if (type == 0) {
                        file.setRecipient(classmate.getStuNo());
                    } else {
                        file.setForumId(forum.getId());
                    }
                    try {
                        Socket socket = new Socket("localhost", 8083);
                        Thread thread = new SendFileThread(socket, file, f);
                        thread.run();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        } else if (e.getSource() == shakeWin) {
            shake();
        } else if (e.getSource() == addPic) {
            FileDialog f = new FileDialog(this, "添加图片");
            f.setIconImage(new ImageIcon("images\\win.jpg").getImage());
            f.show();
            sendPane.insertIcon(new ImageIcon(f.getDirectory() + "\\" + f.getFile()));
        }
    }

    private boolean send(ChatInfo chatInfo) {
        try {
            //字节数组输出流
            bos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(bos);

            oos.writeObject(chatInfo);

            //最终
            byte[] b = bos.toByteArray();
            //把要发送的信息转换为字节数组
            //网络上发送任何东西都是发送字节数组

            DatagramSocket socket = new DatagramSocket();
            //UDP通信
            socket.setSendBufferSize(1024 * 1024);
            InetAddress add = InetAddress.getByName("localhost");
            DatagramPacket p = new DatagramPacket(b, 0, b.length, add, 9999);

            //发送
            socket.send(p);
            socket.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void append(StyledDocument content, User user) {
        ImageIcon imageIcon = new ImageIcon("images\\portrait\\" + user.getPortrait() + ".jpg");
        imageIcon.setImage(imageIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH));
        Document receiveMeg = receivePane.getStyledDocument();
        receivePane.setCaretPosition(receiveMeg.getLength());
        receivePane.insertIcon(imageIcon);
        SimpleAttributeSet as = new SimpleAttributeSet();
        StyleConstants.setFontSize(as, 24);
        try {
            receiveMeg.insertString(receiveMeg.getLength(), user.getNickname() + "(" + user.getName() + ")" + "\n", as);
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
            receivePane.setCaretPosition(receiveMeg.getLength());
        } catch (BadLocationException ex) {
            ex.printStackTrace();
        }
    }

    public void shake() {
        Point p = this.getLocationOnScreen();
        int times = 10;
        shakeWin.setEnabled(false);
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
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
        shakeWin.setEnabled(true);
    }
}
