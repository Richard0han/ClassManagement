package com.classmanagement.client.ui;

import com.classmanagement.client.bean.User;
import com.classmanagement.client.dao.AddData;
import com.classmanagement.client.dao.GetData;
import com.classmanagement.client.utils.TextFieldLimit;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;


/**
 * @program: client
 * @description:
 * @author: Mr.Zhang
 * @create: 2019-05-07 22:34
 **/


public class AddForumFrame extends JFrame implements ActionListener {
    private static final int WIDTH = 466;
    private static final int HEIGHT = 663;
    private JLabel label;
    private JLabel label2;
    private JTextField textField;
    private JButton sure;
    private JButton out;
    private JPanel studentPanel;
    private Font fm;
    private String className;
    public static List<User> students = new ArrayList<User>();

    public AddForumFrame(User user) {
        super("新建群聊");
        students.add(user);
        this.getContentPane().setLayout(null);
        this.setSize(WIDTH, HEIGHT);
        this.setIconImage(new ImageIcon("images\\win.jpg").getImage());
        this.getContentPane().setBackground(Color.WHITE);
        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;
        int windowsWidth = this.getWidth();
        int windowsHeight = this.getHeight();
        this.setBounds((width - windowsWidth) / 2, (height - windowsHeight) / 2, windowsWidth, windowsHeight);

        label = new JLabel("输入群聊名称");
        textField = new JTextField();
        sure = new JButton("确定");
        out = new JButton("退出");
        label2 = new JLabel("可选成员列表");

        List<User> users = GetData.getClassmate(user.getClassId(), user);
        JLabel[] userLabels = new JLabel[users.size()];
        int mm = 0;
        mm = users.size();
        studentPanel = new JPanel();
        studentPanel.setLayout(new GridLayout(mm, 1));
        studentPanel.setPreferredSize(new Dimension(233, 60 * mm));
        studentPanel.setFont(fm);
        studentPanel.setOpaque(true);
        int size = users.size();
        for (int i = 0; i < size; i++) {
            ImageIcon userPortrait = new ImageIcon("images\\portrait\\" + users.get(i).getPortrait() + ".jpg");
            userPortrait.setImage(userPortrait.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH));
            userLabels[i] = new JLabel(users.get(i).getName(), userPortrait, JLabel.LEFT);
            userLabels[i].setBounds(0, i * 60, 233, 432);
            userLabels[i].setOpaque(true);
            userLabels[i].setBackground(Color.WHITE);
            userLabels[i].setFont(new Font("黑体",1,20));
            userLabels[i].addMouseListener(labelList(users.get(i).getStuNo(), users.get(i).getName()));
            studentPanel.add(userLabels[i]);
        }
        fm = new Font("黑体", 0, 20);
        label.setFont(fm);
        textField.setFont(fm);
        sure.setFont(fm);
        out.setFont(fm);
        label2.setFont(fm);
        studentPanel.setFont(fm);

        textField.setDocument(new TextFieldLimit(15));

        JScrollPane scrollPane = new JScrollPane(studentPanel);
        scrollPane.setBackground(Color.WHITE);
        scrollPane.setOpaque(true);
        scrollPane.setBounds(49, 203, 327, 311);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        label.setBounds(49, 51, 327, 49);
        label2.setBounds(49, 157, 327, 44);
        textField.setBounds(49, 99, 327, 57);

        sure.setBounds(14, 554, 125, 49);
        sure.setBackground(Color.WHITE);
        out.setBounds(309, 554, 125, 49);
        out.setBackground(Color.WHITE);

        this.add(label);
        this.add(label2);
        this.add(textField);
        this.add(scrollPane);
        this.add(sure);
        this.add(out);
        this.setVisible(true);

        sure.addActionListener(this);
        out.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == sure) {
            int mm;
            className = textField.getText();
            mm = AddData.addChat(className);
            AddData.addStudent(students, mm, className);
            FontUIResource font = new FontUIResource("微软雅黑", 0, 20);
            UIManager.put("OptionPane.buttonFont", font);
            UIManager.put("OptionPane.messageFont", font);
            JOptionPane.showMessageDialog(null, "添加成功", "提示", JOptionPane.INFORMATION_MESSAGE);
            this.dispose();
        }
        if (e.getSource() == out) {
            this.dispose();
        }
    }

    public MouseListener labelList(final String stuNo, final String name) {
        return new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new VerificationFrame(stuNo, name);
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }

        };
    }
}
