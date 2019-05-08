package com.classmanagement.client.ui;


import com.classmanagement.client.bean.AdministratorLog;
import com.classmanagement.client.bean.ChatInfo;
import com.classmanagement.client.bean.User;
import com.classmanagement.client.bean.Vote;
import com.classmanagement.client.dao.AddData;


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
 * @create: 2019-05-06 21:20
 **/


public class VoteFrame extends JFrame implements ActionListener {
    private static final int WIDTH = 382;
    private static final int HEIGHT = 523;
    private JLabel label;
    private JLabel label1;
    private JTextField textField;
    private JTextArea textArea;
    private JButton agree;
    private JButton disagree;
    private Vote vote;
    private User user;

    public VoteFrame(User user) {
        super("投票编辑页面");
        this.user = user;
        vote = new Vote();
        vote.setForumId(user.getClassId());
        vote.setSender(user.getStuNo());

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
        textField.setDocument(new JTextFieldLimit(15));

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
            vote.setContent(textArea.getText());
            vote.setTitle(textField.getText());
            if (AddData.addVote(vote) == true) {
                try {
                    AdministratorLog al = new AdministratorLog();
                    al.setVote(vote);
                    MainFrame.sendLog(al);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                this.dispose();
            }
        }
        if (e.getSource() == disagree) {
            this.dispose();
        }
    }

    public class JTextFieldLimit extends PlainDocument {

        private int limit;
        //限制的长度

        public JTextFieldLimit(int limit) {
            super();
            //调用父类构造
            this.limit = limit;
        }

        @Override
        public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
            if (str == null) {
                return;
            }

            //下面的判断条件改为自己想要限制的条件即可，这里为限制输入的长度
            if ((getLength() + str.length()) <= limit) {
                super.insertString(offset, str, attr);
                //调用父类方法
            }
        }

    }
}
