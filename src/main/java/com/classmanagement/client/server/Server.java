package com.classmanagement.client.server;



import com.classmanagement.client.bean.FrameManager;
import com.classmanagement.client.bean.User;
import com.classmanagement.client.dao.GetData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.List;


/**
 * @program: client
 * @description:
 * @author: Mr.Zhang
 * @create: 2019-04-12 18:38
 **/


public class Server extends JFrame implements ActionListener{


    private JFrame myWindow;
    private static final int WIDTH = 885;
    private static final int HEIGHT = 626;
    private static JPanel studentList;
    public static JTextArea message;
    private JButton kickOf;
    private JButton search;
    private JButton ip;
    private JButton stopServe;
    private JButton out;

    private JLabel label2;
    private JLabel label3;

    public JScrollPane jScrollPane;
    private Font fm;
    private static int m=1;

    public Server() {
        myWindow = new JFrame("服务器界面");


        myWindow.getContentPane().setLayout(null);
        myWindow.setSize(WIDTH, HEIGHT);
        myWindow.setIconImage(new ImageIcon("images\\smallIcon.jpg").getImage());


        message = new JTextArea();
        kickOf = new JButton("踢出");
        search = new JButton("查找");
        ip = new JButton("ip地址");
        stopServe = new JButton("设置管理员");
        out = new JButton("退出");

        label2 = new JLabel("服务器日志");
        label3=new JLabel(""+m+"班级成员");





        fm = new Font(" 黑体", Font.BOLD, 20);

        message.setFont(fm);
        kickOf.setFont(fm);
        search.setFont(fm);
        ip.setFont(fm);
        stopServe.setFont(fm);

        label2.setFont(fm);
        label3.setFont(fm);


        message.setBounds(274, 55, 552, 420);
        kickOf.setBounds(134, 500, 113, 27);
        search.setBounds(14, 500, 113, 27);
        ip.setBounds(672, 476, 113, 27);
        out.setBounds(473, 476, 113, 27);
        stopServe.setBounds(291, 476, 113, 27);

        label2.setBounds(274, 34, 552, 18);
        label3.setBounds(27,35,233,20);



        message.setEditable(false);


        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;
        int windowsWidth = myWindow.getWidth();
        int windowsHeight = myWindow.getHeight();
        myWindow.setBounds((width - windowsWidth) / 2, (height - windowsHeight) / 2, windowsWidth, windowsHeight);


        JScrollPane scrollPane=new JScrollPane();
        scrollPane.setBounds(274, 55, 552, 420);
        scrollPane.setViewportView(message);
        scrollPane.setVerticalScrollBarPolicy( JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        message.setLineWrap(true);
        message.setWrapStyleWord(true);

        WindowDestroyer myListener = new WindowDestroyer();
        myWindow.addWindowListener((WindowListener) myListener);

        studentList = getpanel();
        setjScrollPane(studentList);

        myWindow.add(search);
        myWindow.add(stopServe);
        myWindow.getContentPane().add(jScrollPane);
        myWindow.add(ip);
        myWindow.add(kickOf);

        myWindow.add(label2);
        myWindow.add(scrollPane);
        myWindow.add(out);
        myWindow.add(label3);

        myWindow.setVisible(true);

        search.addActionListener(this);
        stopServe.addActionListener(this);
        ip.addActionListener(this);
        out.addActionListener(this);
        kickOf.addActionListener(this);


        try {
            final EchoServer e = new EchoServer();
            e.chatService();
            e.fileService();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    public static void main(String[] args) {
        FrameManager.server = new Server();
    }

    private MouseListener labelListe(final String studentNo) {
        return new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new PersonMess(studentNo);


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

    public class WindowDestroyer extends WindowAdapter {
        @Override
        public void windowClosing(WindowEvent e) {
            System.exit(0);
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == search) {
        new SerchPanel();
        }//添加
        if (e.getSource() == stopServe) {

        }//停止服务
        if (e.getSource() == ip) {

        }//查找IP
        if (e.getSource() == out) {
            myWindow.dispose();
        }//离开
        if (e.getSource() == kickOf) {

        }//删除


    }

    public JPanel getpanel() {

        List<User> users = GetData.getClassmate(m,null);
        JLabel[] userLabels = new JLabel[users.size()];
         int mm= 0;
         mm= users.size();
        studentList = new JPanel();
        studentList.setLayout(new GridLayout(mm, 1));
        studentList.setPreferredSize(new Dimension(233,60*mm));
        studentList.setFont(fm);
        studentList.setOpaque(true);

        for (int i = 0; i < users.size(); i++) {
            ImageIcon userPortrait = new ImageIcon("images\\portrait\\" + users.get(i).getPortrait() + ".jpg");
            userPortrait.setImage(userPortrait.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH));
            userLabels[i] = new JLabel(users.get(i).getName(), userPortrait, JLabel.LEFT);
            userLabels[i].setBounds(0, i * 60, 233, 432);
            userLabels[i].setOpaque(true);
            userLabels[i].setBackground(Color.WHITE);
            userLabels[i].addMouseListener(labelListe(users.get(i).getStuNo()));
            studentList.add(userLabels[i]);
        }
        return studentList;
    }



public void setjScrollPane(JPanel studentList){

    jScrollPane = new JScrollPane();
    jScrollPane.setViewportView(studentList);

    jScrollPane.setBounds(27, 55, 233, 432);
    jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
}
public static void setN(int aa){
        m=aa;
}
}