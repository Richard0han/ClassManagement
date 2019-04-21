package com.classmanagement.client.ui;

import com.classmanagement.client.bean.Forum;
import com.classmanagement.client.bean.User;
import com.classmanagement.client.bean.Vote;
import com.classmanagement.client.dao.GetData;
import com.classmanagement.client.dao.UpdateData;
import com.classmanagement.client.utils.GetWeather;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Calendar;
import java.util.List;

/**
 * ClassManager
 *
 * @author Richard-J
 * @description 聊天主面板
 * @date 2019.03
 */

public class MainFrame extends JFrame implements ActionListener {
    private User user;
    private ImageIcon background = new ImageIcon("images\\mainPanel.png");
    private JLabel backPic = new JLabel(background);
    private JLabel portraitLabel;
    private JLabel nicknameLabel;
    private JLabel signatureLabel;
    private JLabel weatherPicLabel;
    private JPanel mainPanel;
    private JPanel functionPanel = new JPanel();
    private CardLayout functionCard = new CardLayout();
    private Panel chatPanel = new Panel();
    private CardLayout chatCard = new CardLayout();
    private JPanel announcementPanel = new JPanel();
    private JPanel votePanel = new JPanel();
    private List<Forum> forumList;
    private List<User> classmateList;
    private List<Vote> voteList;
    private JButton supportButton;
    private JButton opposeButton;
    JTextArea suggestionText = new JTextArea();
    private int votePage = 0;
    private int annPage = 0;
    private Button testButton = new Button("测试");
    private String functionPage = "聊天";
    private String chatPage = "0";

    public MainFrame(User user) {
        super("微校");

        backPic.setBounds(0, 0, 450, 920);
        mainPanel = (JPanel) this.getContentPane();
        mainPanel.setOpaque(false);
        mainPanel.setLayout(null);

        this.user = user;
        ImageIcon portrait = new ImageIcon("images\\portrait\\" + user.getPortrait() + ".jpg");
        portrait.setImage(portrait.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
        portraitLabel = new JLabel(portrait);
        nicknameLabel = new JLabel(user.getNickname());
        signatureLabel = new JLabel(user.getSignature());
        nicknameLabel.setFont(new Font("微软雅黑", 1, 30));
        nicknameLabel.setForeground(Color.GRAY);
        String[] weather = GetWeather.getWeather(0);
        weatherPicLabel = setWeatherPic(weatherPicLabel, weather[4]);

        signatureLabel.setFont(new Font("黑体", 1, 20));
        nicknameLabel.setForeground(Color.LIGHT_GRAY);

        portraitLabel.setBounds(10, 10, 100, 100);
        nicknameLabel.setBounds(130, 10, 150, 50);
        signatureLabel.setBounds(130, 75, 190, 20);
        weatherPicLabel.setBounds(340, 15, 90, 90);
        functionPanel.setBounds(0, 156, 450, 724);
        testButton.setBounds(0, 0, 100, 100);
        refresh();

        mainPanel.add(testButton);
        mainPanel.add(portraitLabel);
        mainPanel.add(nicknameLabel);
        mainPanel.add(signatureLabel);
        mainPanel.add(weatherPicLabel);
        mainPanel.add(functionPanel);

        this.getLayeredPane().setLayout(null);
        this.setResizable(false);
        this.getLayeredPane().add(backPic, new Integer(Integer.MIN_VALUE));
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) screenSize.getWidth() / 2;
        int y = (int) screenSize.getHeight() / 20;
        this.setBounds(x, y, 450, 920);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ImageIcon win = new ImageIcon("images\\win.jpg");
        this.setIconImage(win.getImage());
        this.setVisible(true);

        weatherPicLabel.setToolTipText("<html><body><p>" + weather[4] + "</p>" +
                "<p>风 力： " + weather[3] + "</p>" +
                "<p>现在温度： " + weather[1] + "</p>" +
                "<p>空气质量： " + weather[0] + "</p>" +
                "<p>今日温度： " + weather[5]);
        ToolTipManager.sharedInstance().setDismissDelay(10000);
        ToolTipManager.sharedInstance().setReshowDelay(1);

        testButton.addActionListener(this);
    }

    /**
     * description 设置按钮靠左，白色，字体
     *
     * @param jButton
     * @return javax.swing.JButton
     */
    private JButton setButton(JButton jButton) {
        Font buttonFont = new Font("宋体", 1, 20);
        jButton.addActionListener(chatLabelActionListener());
        jButton.setLayout(null);
        jButton.setHorizontalAlignment(SwingConstants.LEFT);
        jButton.setFont(buttonFont);
        jButton.setBackground(Color.WHITE);
        return jButton;
    }

    /**
     * description setWeatherPic
     *
     * @param weatherPicLabel
     * @param weather
     * @return javax.swing.JLabel
     */
    private JLabel setWeatherPic(JLabel weatherPicLabel, String weather) {
        String pic;
        Boolean cloudy = (weather.contains("多云") && weather.contains("阴")) || (weather.equals("阴")) || (weather.equals("多云"));
        if (weather.equals("晴")) {
            pic = "images\\weather\\晴.png";
        } else if (weather.contains("晴") && (weather.contains("多云") || weather.contains("阴")) && isDay()) {
            pic = "images\\weather\\多云.png";
        } else if (weather.contains("晴") && (weather.contains("多云") || weather.contains("阴")) && !isDay()) {
            pic = "images\\weather\\多云（晚）.png";
        } else if (cloudy && isDay()) {
            pic = "images\\weather\\阴.png";
        } else if (cloudy && !isDay()) {
            pic = "images\\weather\\阴（晚）.png";
        } else if (weather.contains("阴") && weather.contains("雨")) {
            pic = "images\\weather\\小雨.png";
        } else if (weather.contains("雷阵雨") && weather.contains("冰雹")) {
            pic = "images\\weather\\雷阵雨伴有冰雹.png";
        } else if (weather.contains("雷阵雨")) {
            pic = "images\\weather\\雷阵雨.png";
        } else if (weather.contains("阵雨")) {
            pic = "images\\weather\\阵雨";
        } else if (weather.contains("雨转晴.png")) {
            pic = "images\\weather\\大雨转晴.png";
        } else if (weather.contains("暴雨")) {
            pic = "images\\weather\\暴雨.png";
        } else if (weather.contains("大雨")) {
            pic = "images\\weather\\大雨.png";
        } else if (weather.contains("中雨")) {
            pic = "images\\weather\\中雨.png";
        } else if (weather.contains("小雨")) {
            pic = "images\\weather\\小雨.png";
        } else if (weather.contains("雨夹雪")) {
            pic = "images\\weather\\雨夹雪.png";
        } else if (weather.contains("暴雪")) {
            pic = "images\\weather\\暴雪.png";
        } else if (weather.contains("雪")) {
            pic = "images\\weather\\雪.png";
        } else if (weather.contains("雾") || weather.contains("霾")) {
            pic = "images\\weather\\霾、雾.png";
        } else {
            pic = "images\\weather\\晴";
        }
        ImageIcon weatherPng = new ImageIcon(pic);
        weatherPicLabel = new JLabel(weatherPng);
        return weatherPicLabel;
    }

    private boolean isDay() {
        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        int night = 18;

        if (hour >= night) {
            return false;
        } else {
            return true;
        }
    }

    private void setFunctionPanel() {
        forumList = GetData.getForum(user);
        classmateList = GetData.getClassmate(user.getClassId());
        functionPanel.setLayout(functionCard);
        functionPanel.add(chatPanel, "聊天");
        chatCard = new CardLayout();
        JPanel chatZero = setChatCard(0);
        JPanel chatOne = setChatCard(1);
        JPanel chatTwo = setChatCard(2);
        chatPanel.setLayout(chatCard);
        chatPanel.add(chatZero, "0");
        chatPanel.add(chatOne, "1");
        chatPanel.add(chatTwo, "2");
        chatCard.show(chatPanel, "0");
        functionPanel.setBackground(Color.WHITE);
        setVotePanel();
        functionPanel.add(announcementPanel, "公告");
        functionPanel.add(votePanel, "投票");
        functionCard.show(functionPanel, functionPage);
        functionPanel.setOpaque(false);
    }

    /**
     * description setCard
     *
     * @param listId 一共有三个列表，不同id表明生成不同的面板
     * @return javax.swing.JPanel
     */
    private JPanel setChatCard(int listId) {
        Font labelFont1 = new Font("宋体", 1, 25);
        Font labelFont2 = new Font("宋体", 1, 20);
        JPanel chatPanelAccessory = new JPanel();
        JButton forumButton;
        JButton classmateButton;
        int forumSize = forumList.size();
        if (listId == 1) {
            forumButton = new JButton("﹀  群聊");
            JLabel[] forumLabels = new JLabel[forumSize];
            Forum forum;
            for (int i = 0; i < forumSize; i++) {
                forum = forumList.get(i);
                ImageIcon forumPortrait = new ImageIcon("images\\portrait\\forum" + forum.getIsClass() + ".jpg");
                forumPortrait.setImage(forumPortrait.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH));
                forumLabels[i] = new JLabel("<html><p type=\"forum\" id=\"" + forum.getId() + "\">" + forum.getName() + "</p></html>", forumPortrait, JLabel.LEFT);
                forumLabels[i].setBounds(0, 45 + i * 60, 450, 60);
                forumLabels[i].setFont(labelFont1);
                forumLabels[i].setOpaque(true);
                forumLabels[i].setBackground(Color.WHITE);
                forumLabels[i].addMouseListener(chatLabelMouseListener());
                chatPanelAccessory.add(forumLabels[i]);
            }
        } else {
            forumButton = new JButton(" 〉 群聊");
        }
        forumButton = setButton(forumButton);
        forumButton.setSize(450, 45);

        int classmateSize = classmateList.size();
        if (listId == 2) {
            classmateButton = new JButton("﹀  同学");
            classmateButton.setBounds(0, 45, 450, 45);

            JLabel[] classmateLabels = new JLabel[classmateSize];
            User user;
            JPanel classmates = new JPanel(new GridLayout(classmateSize, 1));
            classmates.setSize(450, 60 * classmateSize);
            classmates.setBackground(Color.WHITE);
            JScrollPane jScrollPane = new JScrollPane(classmates);
            classmates.setBounds(0, 0, 450, 50);
            for (int i = 0; i < classmateSize; i++) {
                user = classmateList.get(i);
                ImageIcon userPortrait = new ImageIcon("images\\portrait\\" + user.getPortrait() + ".jpg");
                userPortrait.setImage(userPortrait.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH));
                classmateLabels[i] = new JLabel("<html><body><p>" + user.getNickname() + "&nbsp(" + user.getName() + ")</p>" +
                        "<p color=\"gray\" type=\"person\" id=\"" + user.getStuNo() + "\">" + user.getSignature() + "</p></html>", userPortrait, JLabel.LEFT);
                classmateLabels[i].setFont(labelFont2);
                classmateLabels[i].setBounds(0, i * 60, 450, 50);
                classmateLabels[i].setOpaque(true);
                classmateLabels[i].setBackground(Color.WHITE);
                classmateLabels[i].addMouseListener(chatLabelMouseListener());
                classmates.add(classmateLabels[i]);
            }
            if (classmateSize <= 10) {
                jScrollPane.setBounds(0, 90, 450, 62 * classmateSize);
            } else {
                jScrollPane.setBounds(0, 90, 450, 635);
            }
            jScrollPane.setBorder(null);
            jScrollPane.setBackground(Color.WHITE);
            chatPanelAccessory.add(jScrollPane);
        } else if (listId == 1) {
            classmateButton = new JButton(" 〉 同学");
            classmateButton.setBounds(0, 45 + forumSize * 60, 450, 45);
        } else {
            classmateButton = new JButton(" 〉 同学");
            classmateButton.setBounds(0, 45, 450, 45);
        }
        classmateButton = setButton(classmateButton);
        chatPanelAccessory.setBounds(0, 0, 450, 724);
        chatPanelAccessory.add(forumButton);
        chatPanelAccessory.add(classmateButton);
        chatPanelAccessory.setLayout(null);
        chatPanelAccessory.setOpaque(false);
        return chatPanelAccessory;
    }

    private MouseListener chatLabelMouseListener() {
        return new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JLabel jLabel = (JLabel) e.getSource();
                if (e.getClickCount() == 1) {
                    jLabel.setBackground(Color.LIGHT_GRAY);
                } else if (e.getClickCount() == 2) {
                    System.out.println(jLabel.getText());
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                JLabel jLabel = (JLabel) e.getSource();
                jLabel.setBackground(Color.LIGHT_GRAY);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                JLabel jLabel = (JLabel) e.getSource();
                jLabel.setBackground(Color.WHITE);
            }
        };
    }

    private ActionListener chatLabelActionListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String set = "〉";
                String forum = "群聊";
                JButton jButton = (JButton) e.getSource();
                if (jButton.getText().contains(set)) {
                    if (jButton.getText().contains(forum)) {
                        chatCard.show(chatPanel, "1");
                        chatPage = "1";
                    } else {
                        chatCard.show(chatPanel, "2");
                        chatPage = "2";
                    }
                } else {
                    chatCard.show(chatPanel, "0");
                    chatPage = "0";
                }
            }
        };
    }

    private int setVotePanel() {
        voteList = GetData.getVote(user);
        int voteSize = voteList.size();
        if (voteSize == 0) {
            JLabel notFind = new JLabel(new ImageIcon("images\\noVote.png"));
            notFind.setBounds(85, 180, 280, 285);
            votePanel.add(notFind);
        } else {
            Vote vote = voteList.get(votePage);
            Font titleFont = new Font("黑体", 1, 25);
            JLabel titleLabel;
            titleLabel = new JLabel(vote.getTitle(), JLabel.CENTER);
            titleLabel.setFont(titleFont);
            titleLabel.setBounds(0, 15, 450, 40);

            supportButton = new JButton("<html><p id=" + vote.getId() + ">赞成 " + vote.getSupport() + "</p></html>");
            opposeButton = new JButton("<html><p id=" + vote.getId() + ">反对 " + vote.getOppose() + "</p></html>");
            if (vote.getStatus() == 1) {
                supportButton.setEnabled(false);
                opposeButton.setEnabled(false);
            }
            setVoteButton();

            JButton lastButton = setPageButton(0, "上一页", voteSize);
            JButton nextButton = setPageButton(0, "下一页", voteSize);
            lastButton.setLocation(50, 600);
            nextButton.setLocation(280, 600);

            JLabel indexLabel = new JLabel((votePage + 1) + "/" + voteSize);
            indexLabel.setFont(new Font("黑体", 0, 15));
            indexLabel.setBounds(210, 600, 80, 40);

            Font contentFont = new Font("黑体", 1, 20);
            suggestionText.setFont(contentFont);
            suggestionText.setLineWrap(true);
            JScrollPane scrollPane = new JScrollPane(suggestionText);
            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            scrollPane.setBounds(25, 400, 300, 150);
            JButton addSuggestion = new JButton("<html><p id=" + vote.getId() + ">提</p><p>交</p><p>建</p><p>议</p></html>");
            addSuggestion.setFont(new Font("宋体",1,20));
            addSuggestion.setBounds(345, 400, 80, 150);
            addSuggestion.setBackground(Color.WHITE);
            addSuggestion.addActionListener(setSuggestionListener());

            JTextArea textArea = new JTextArea();
            textArea.setText("投票内容：\n" + vote.getContent() + "\n\n建议：" + vote.getSuggestion());
            textArea.setFont(contentFont);
            textArea.setForeground(Color.LIGHT_GRAY);
            textArea.setLineWrap(true);
            textArea.setEditable(false);
            votePanel.add(textArea);
            JScrollPane jScrollPane = new JScrollPane(textArea);
            jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            jScrollPane.setBounds(0, 50, 450, 220);
            votePanel.add(supportButton);
            votePanel.add(opposeButton);
            votePanel.add(lastButton);
            votePanel.add(nextButton);
            votePanel.add(scrollPane);
            votePanel.add(addSuggestion);
            votePanel.add(indexLabel);
            votePanel.add(titleLabel);
            votePanel.add(jScrollPane);
        }
        votePanel.setBounds(0, 0, 450, 724);
        votePanel.setLayout(null);
        votePanel.setOpaque(false);

        return voteSize;
    }

    private ActionListener setSuggestionListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FontUIResource font = new FontUIResource("微软雅黑", 0, 20);
                UIManager.put("OptionPane.buttonFont", font);
                UIManager.put("OptionPane.messageFont", font);
                if (suggestionText.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "请输入内容！", "提示", JOptionPane.ERROR_MESSAGE);
                } else {
                    String text = ((JButton)e.getSource()).getText();
                    int id = Integer.parseInt(text.substring(text.indexOf("=") + 1, text.indexOf(">", 8)));
                    if (UpdateData.addVoteSuggestion(id,"\n"+suggestionText.getText())){
                        JOptionPane.showMessageDialog(null, "建议提交成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
                        suggestionText.setText("");
                        functionPage = "投票";
                        refresh();
                    }else {
                        JOptionPane.showMessageDialog(null, "出现了奇奇怪怪的错误~", "提示", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        };
    }

    private void setVoteButton() {
        supportButton.setBackground(Color.WHITE);
        opposeButton.setBackground(Color.WHITE);
        supportButton.setFont(new Font("黑体", 1, 18));
        opposeButton.setFont(new Font("黑体", 1, 18));
        supportButton.setBounds(50, 300, 140, 40);
        opposeButton.setBounds(250, 300, 140, 40);
        supportButton.addActionListener(voteActionListener());
        opposeButton.addActionListener(voteActionListener());
    }

    private JButton setPageButton(final int sort, String name, int size) {
        final JButton jButton = new JButton(name);
        jButton.setFont(new Font("黑体", 1, 20));
        jButton.setBorderPainted(false);
        jButton.setBackground(Color.WHITE);
        jButton.setSize(100, 40);
        if (name.equals("上一页")) {
            if ((sort == 0 && votePage == 0) || (sort == 1 && annPage == 0)) {
                jButton.setEnabled(false);
            }
        } else {
            if ((sort == 0 && votePage == size - 1) || (sort == 1 && annPage == size - 1)) {
                jButton.setEnabled(false);
            }
        }
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton jButton1 = (JButton) e.getSource();
                if (jButton1.getText().contains("上一页")) {
                    if (sort == 0) {
                        votePage--;
                    } else {
                        annPage--;
                    }
                    functionPage = "投票";
                    refresh();
                } else {
                    if (sort == 0) {
                        votePage++;
                    } else {
                        annPage++;
                    }
                    functionPage = "投票";
                    refresh();
                }
            }
        });

        return jButton;
    }

    private ActionListener voteActionListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String support = "赞成";
                JButton jButton = (JButton) e.getSource();
                String text = jButton.getText();
                FontUIResource font = new FontUIResource("微软雅黑", 0, 20);
                UIManager.put("OptionPane.buttonFont", font);
                UIManager.put("OptionPane.messageFont", font);
                int id = Integer.parseInt(text.substring(text.indexOf("=") + 1, text.indexOf(">", 10)));

                if (text.contains(support)) {
                    if (UpdateData.vote(true, id, user.getStuNo(), user.getClassId())) {
                        JOptionPane.showMessageDialog(null, "投票成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
                        supportButton.setEnabled(false);
                        opposeButton.setEnabled(false);
                        functionPage = "投票";
                        refresh();
                    } else {
                        JOptionPane.showMessageDialog(null, "出现了奇奇怪怪的错误~", "提示", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    if (UpdateData.vote(false, id, user.getStuNo(), user.getClassId())) {
                        JOptionPane.showMessageDialog(null, "投票成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
                        supportButton.setEnabled(false);
                        opposeButton.setEnabled(false);
                        functionPage = "投票";
                        refresh();
                    } else {
                        JOptionPane.showMessageDialog(null, "出现了奇奇怪怪的错误~", "提示", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        };
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        functionCard.show(functionPanel, "投票");
    }

    private void refresh() {
        chatPanel.removeAll();
        votePanel.removeAll();
        setFunctionPanel();
        chatCard.show(chatPanel, chatPage);
    }
}
