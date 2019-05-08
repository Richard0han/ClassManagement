package com.classmanagement.client.server;

import com.classmanagement.client.bean.FrameManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @program: client
 * @description:
 * @author: Mr.Zhang
 * @create: 2019-05-04 15:29
 **/


public class SerchPanel extends JFrame implements ActionListener {
    private JFrame myWindow;
    private static final int WIDTH = 382;
    private static final int HEIGHT =523;
    private JLabel Label1;
    private JButton serch;
    private JButton out;
    private JTextField tex;
    public SerchPanel(){
        myWindow = new JFrame("查询");
        myWindow.getContentPane().setLayout(null);
        myWindow.setSize(WIDTH, HEIGHT);
        myWindow.setIconImage(new ImageIcon("images\\win.jpg").getImage());

        Label1=new JLabel("输入班级");
        serch=new JButton("查询");
        out=new JButton("退出");
        tex=new JTextField();

        Label1.setBounds(28, 120, 276, 52);
        serch.setBounds(14, 382, 113, 52);
        out.setBounds(209, 382, 113, 52);
        tex.setBounds(28, 171, 276, 52);

        Font fm = new Font(" 黑体", Font.BOLD, 20);
        Label1.setFont(fm);
        tex.setFont(fm);

        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;
        int windowsWidth = myWindow.getWidth();
        int windowsHeight = myWindow.getHeight();
        myWindow.setBounds((width - windowsWidth) / 2, (height - windowsHeight) / 2, windowsWidth, windowsHeight);

        myWindow.add(Label1);
        myWindow.add(serch);
        myWindow.add(out);
        myWindow.add(tex);

        myWindow.setVisible(true);
        serch.addActionListener(this);
        out.addActionListener(this);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==serch){
            int a;
            String aa = tex.getText();
            a= Integer.parseInt(aa);
            Server.setN(a);
            FrameManager.server.jScrollPane.setViewportView(FrameManager.server.getpanel());
            myWindow.dispose();
        }
        if (e.getSource()==out){
            myWindow.dispose();
        }
    }
    public int getClassNum() {
        int num;
        String aa = tex.getText();
        num = Integer.parseInt(aa);
        return num;
    }
}
