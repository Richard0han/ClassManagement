package com.classmanagement.client.ui;

import com.classmanagement.client.bean.User;
import com.classmanagement.client.dao.AddData;
import com.classmanagement.client.dao.GetData;
import com.classmanagement.client.utils.JsonParser;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * ClassManager
 *
 * @author Richard-J
 * @description 窗体demo
 * @date 2019.03
 */

public class BasicData extends JFrame implements ActionListener {

    private User user;
    private ImageIcon background = new ImageIcon("images\\dataPanel.png");
    private JLabel backPic = new JLabel(background);
    private JLabel nicknameLabel = new JLabel("*昵 称");
    private JTextField nicknameText = new JTextField(15);
    private JLabel signatureLabel = new JLabel(" 签 名");
    private JTextArea signatureText = new JTextArea();
    private JLabel dividingLine = new JLabel("__________________");
    private JLabel basicData = new JLabel(" 基本资料");
    private JLabel dataLabel;
    private JLabel portraitLabel = new JLabel(" 头 像");
    private JComboBox setPortrait;
    private JButton confirmButton = new JButton("确认");
    private JButton cancelButton = new JButton("取消");
    private JPanel basicDataPanel;


    public BasicData(String cookie) throws Exception {

        user = JsonParser.getUser(cookie);

        dataLabel = new JLabel("<html><body><p> 姓 名 &nbsp&nbsp&nbsp" + user.getName() + "</p>" +
                "<p></p>" +
                "<p> 性 别 &nbsp&nbsp&nbsp" + user.getSex() + "</p>" +
                "<p></p>" +
                "<p> 班 级 &nbsp&nbsp&nbsp" + user.getClassName() + "</p>" +
                "<p></p>" +
                "<p> 学 号 &nbsp " + user.getStuNo() + "</p>");
        backPic.setBounds(0, 0, background.getIconWidth(), background.getIconHeight());
        basicDataPanel = (JPanel) this.getContentPane();
        basicDataPanel.setOpaque(false);
        basicDataPanel.setLayout(null);
        Font textFont = new Font("宋体", 0, 20);
        Font buttonFont = new Font("黑体", 0, 20);

        //设置格式
        nicknameLabel = setJLabelFormat(nicknameLabel);
        signatureLabel = setJLabelFormat(signatureLabel);
        portraitLabel = setJLabelFormat(portraitLabel);
        dividingLine = setJLabelFormat(dividingLine);
        dataLabel = setJLabelFormat(dataLabel);
        portraitLabel = setJLabelFormat(portraitLabel);
        basicData.setFont(buttonFont);
        nicknameLabel.setForeground(Color.LIGHT_GRAY);
        nicknameText.setFont(textFont);
        signatureLabel.setForeground(Color.LIGHT_GRAY);
        signatureText.setFont(textFont);
        portraitLabel.setForeground(Color.LIGHT_GRAY);
        confirmButton.setFont(buttonFont);
        cancelButton.setFont(buttonFont);

        //设置TextArea
        signatureText.setLineWrap(true);
        signatureText.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
        //设置JComboBox
        setPortrait = new JComboBox();
        for (int i = 0; i <= 9; i++) {
            setPortrait.addItem(new ImageIcon("images\\portrait\\" + i + ".jpg"));
        }
        //设置大小位置
        nicknameLabel.setBounds(15, 0, 170, 50);
        nicknameText.setBounds(100, 8, 280, 35);
        signatureLabel.setBounds(15, 50, 170, 50);
        signatureText.setBounds(100, 58, 280, 75);
        dividingLine.setBounds(15, 120, 500, 50);
        basicData.setBounds(15, 157, 170, 50);
        dataLabel.setBounds(25, 200, 400, 170);
        portraitLabel.setBounds(15, 380, 170, 50);
        setPortrait.setBounds(170, 410, 140, 120);
        confirmButton.setBounds(205, 563, 80, 30);
        cancelButton.setBounds(305, 563, 80, 30);
        confirmButton.setBackground(Color.WHITE);
        cancelButton.setBackground(Color.WHITE);

        //添加控件
        basicDataPanel.add(nicknameLabel);
        basicDataPanel.add(nicknameText);
        basicDataPanel.add(signatureLabel);
        basicDataPanel.add(signatureText);
        basicDataPanel.add(dividingLine);
        basicDataPanel.add(basicData);
        basicDataPanel.add(dataLabel);
        basicDataPanel.add(portraitLabel);
        basicDataPanel.add(setPortrait);
        basicDataPanel.add(confirmButton);
        basicDataPanel.add(cancelButton);

        this.getLayeredPane().setLayout(null);
        this.setResizable(false);
        this.getLayeredPane().add(backPic, new Integer(Integer.MIN_VALUE));
        this.setTitle("设置基础资料");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) screenSize.getWidth() / 3;
        int y = (int) screenSize.getHeight() / 5;
        this.setSize(413, 637);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ImageIcon win = new ImageIcon("images\\win.jpg");
        this.setIconImage(win.getImage());
        this.setVisible(true);

        confirmButton.addActionListener(this);
        cancelButton.addActionListener(this);
    }

    private JLabel setJLabelFormat(JLabel jLabel) {
        Font labelFont = new Font("黑体", 1, 20);
        jLabel.setFont(labelFont);
        jLabel.setForeground(Color.LIGHT_GRAY);
        return jLabel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        FontUIResource font = new FontUIResource("微软雅黑", 0, 20);
        UIManager.put("OptionPane.buttonFont", font);
        UIManager.put("OptionPane.messageFont", font);
        if (e.getSource() == confirmButton) {
            String nickname = nicknameText.getText();

            if (nickname.equals("")) {
                JOptionPane.showMessageDialog(this, "昵称不能为空", "提示", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int submit = JOptionPane.showConfirmDialog(this, "确认提交吗", "提交", JOptionPane.YES_NO_OPTION);
            if (submit == JOptionPane.YES_OPTION) {
                user.setNickname(nicknameText.getText());
                if (signatureText.getText().equals("")) {
                    user.setSignature("这个人很懒还什么都没说呢~");
                } else {
                    user.setSignature(signatureText.getText());
                }
                user.setIsManager(0);
                user.setPortrait(setPortrait.getSelectedIndex());
                if (AddData.addUser(user)) {
                    JOptionPane.showMessageDialog(this, "注册成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
                    user = GetData.getUser(user.getStuNo());
                    MainFrame mainFrame = new MainFrame(user);
                    this.dispose();
                }
            }
        } else if (e.getSource() == cancelButton) {
            System.exit(0);
        }
    }
}
