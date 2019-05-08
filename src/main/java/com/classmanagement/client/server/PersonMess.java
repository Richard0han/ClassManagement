package com.classmanagement.client.server;

import com.classmanagement.client.bean.User;
import com.classmanagement.client.utils.DbHelper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.spec.PSSParameterSpec;
import java.sql.*;

/**
 * @program: client
 * @description:
 * @author: Mr.Zhang
 * @create: 2019-04-20 13:56
 **/


public class PersonMess extends JFrame implements ActionListener {
    private JFrame myWindow;
    private static final int WIDTH = 418;
    private static final int HEIGHT = 626;
    private JButton setManger;
    private JButton outside;
    private JLabel name;
    private JLabel isManage;
    private JLabel classId;
    private JLabel stuNo;
    private String stuNob;
    private JButton kickManader;

    public PersonMess(String studentNo) {
        stuNob = studentNo;
        myWindow = new JFrame("学生信息");
        myWindow.getContentPane().setLayout(null);
        myWindow.setSize(WIDTH, HEIGHT);
        myWindow.setIconImage(new ImageIcon("images\\smallIcon.jpg").getImage());

        setManger = new JButton("选为管理员");
        outside = new JButton("退出");
        kickManader = new JButton("删除管理员");

        setManger.setBounds(31, 416, 113, 42);
        outside.setBounds(244, 457, 113, 42);
        kickManader.setBounds(31, 502, 113, 42);

        Font fm = new Font(" 黑体", Font.BOLD, 20);


        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;
        int windowsWidth = myWindow.getWidth();
        int windowsHeight = myWindow.getHeight();
        myWindow.setBounds((width - windowsWidth) / 2, (height - windowsHeight) / 2, windowsWidth, windowsHeight);


        User user;
        user = new User();
        try {
            Connection connection = DbHelper.getConnection();
            ResultSet resultSet = null;
            PreparedStatement ps = null;

            String sql = "SELECT * FROM user WHERE stu_no=" + studentNo;
            ps = connection.prepareStatement(sql);
            resultSet = ps.executeQuery();
            while (resultSet.next()) {
                user.setName(resultSet.getString("name"));
                user.setIsManager(resultSet.getInt("is_manager"));
                user.setClassId(resultSet.getInt("class_id"));
                user.setStuNo(resultSet.getString("stu_no"));

            }
            DbHelper.close(ps, connection, resultSet);
        } catch (Exception e) {
            e.printStackTrace();
        }

        name = new JLabel("姓名" + user.getName());
        if (user.getIsManager() == 1) {
            isManage = new JLabel("是管理员");
        } else {
            isManage = new JLabel("非管理员");
        }
        classId = new JLabel("" + user.getClassId() + "班");
        stuNo = new JLabel("学号" + user.getStuNo());

        name.setBounds(67, 34, 243, 53);
        isManage.setBounds(67, 137, 243, 53);
        classId.setBounds(67, 243, 243, 53);
        stuNo.setBounds(67, 351, 243, 53);

        name.setFont(fm);
        isManage.setFont(fm);
        classId.setFont(fm);
        stuNo.setFont(fm);
        name.setOpaque(true);
        isManage.setOpaque(true);
        classId.setOpaque(true);
        stuNo.setOpaque(true);


        myWindow.add(setManger);
        myWindow.add(outside);
        myWindow.add(name);
        myWindow.add(isManage);
        myWindow.add(classId);
        myWindow.add(stuNo);
        myWindow.add(kickManader);
        myWindow.setVisible(true);

        setManger.addActionListener(this);
        outside.addActionListener(this);
        kickManader.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == setManger) {
            try {
                Connection connection = DbHelper.getConnection();
                PreparedStatement ps = null;
                String sql = "UPDATE user SET is_manager = 1 WHERE stu_no=" + stuNob;
                ps = connection.prepareStatement(sql);
                ps.executeUpdate();
                ps.close();
                connection.close();
            } catch (Exception e1) {
                e1.printStackTrace();
            }

            myWindow.dispose();

        }
        if (e.getSource() == outside) {
            myWindow.dispose();
        }
        if (e.getSource() == kickManader) {
            try {
                Connection connection = DbHelper.getConnection();
                PreparedStatement ps = null;
                String sql = "UPDATE user SET is_manager = 0 WHERE stu_no=" + stuNob;

                ps = connection.prepareStatement(sql);
                ps.executeUpdate();
                ps.close();
                connection.close();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            myWindow.dispose();

        }
    }
}
