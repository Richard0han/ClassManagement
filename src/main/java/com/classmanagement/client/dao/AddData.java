package com.classmanagement.client.dao;

import com.classmanagement.client.bean.Announcement;
import com.classmanagement.client.bean.User;
import com.classmanagement.client.bean.Vote;
import com.classmanagement.client.utils.DbHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * ClassManager
 *
 * @author Richard-J
 * @description 添加数据库数据
 * @date 2019.03
 */

public class AddData {

    public static boolean addUser(User user) {
        try {
            Connection connection = DbHelper.getConnection();
            PreparedStatement preparedStatement = null;
            ResultSet resultSet = null;
            String sql1 = "select count(*) from forum where name=? and is_class=1";
            preparedStatement = connection.prepareStatement(sql1);
            preparedStatement.setString(1, user.getClassName());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String sql;
                if (resultSet.getInt(1) == 0) {
                    sql = "insert into forum(name,is_class) values(?,?)";
                    preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setString(1, user.getClassName());
                    preparedStatement.setInt(2, 1);
                    preparedStatement.executeUpdate();
                }

                sql = "select * from forum where name=? and is_class=1";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, user.getClassName());
                resultSet = preparedStatement.executeQuery();
                resultSet.next();
                user.setClassId(resultSet.getInt("id"));
            }

            String sql2 = "insert into user (stu_no,name,nickname,signature,portrait,class_id,sex,is_manager,class_name) values(?,?,?,?,?,?,?,?,?)";
            preparedStatement = connection.prepareStatement(sql2);
            preparedStatement.setString(1, user.getStuNo());
            preparedStatement.setString(2, user.getName());
            preparedStatement.setString(3, user.getNickname());
            preparedStatement.setString(4, user.getSignature());
            preparedStatement.setInt(5, user.getPortrait());
            preparedStatement.setInt(6, user.getClassId());
            preparedStatement.setString(7, user.getSex());
            preparedStatement.setInt(8, user.getIsManager());
            preparedStatement.setString(9, user.getClassName());
            preparedStatement.executeUpdate();

            DbHelper.close(preparedStatement, connection, resultSet);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean addVote(Vote vote) {
        try {
            Connection connection = DbHelper.getConnection();
            String sql1 = "insert into vote (title,content,support,oppose,forum_id,suggestion) values(?,?,?,?,?,?)";
            String sql2 = "select last_insert_id();";
            PreparedStatement preparedStatement = connection.prepareStatement(sql1);
            preparedStatement.setString(1, vote.getTitle());
            preparedStatement.setString(2, vote.getContent());
            preparedStatement.setInt(3, 0);
            preparedStatement.setInt(4, 0);
            preparedStatement.setInt(5, vote.getForumId());
            preparedStatement.setString(6,"");
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.executeQuery(sql2);
            if (resultSet.next()) {
                vote.setId(resultSet.getInt(1));
            }

            List<String> stuNoList = new ArrayList<String>();
            String sql3 = "select * from user where class_id=" + vote.getForumId();
            preparedStatement = connection.prepareStatement(sql3);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                stuNoList.add(resultSet.getString("stu_no"));
            }

            int size = stuNoList.size();
            Random random = new Random();
            String stuNo = stuNoList.get(random.nextInt(size));

            addVoteHistory(stuNo, vote.getForumId(), vote.getId(), connection, preparedStatement);

            DbHelper.close(preparedStatement, connection, resultSet);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public static boolean selectUserToVote(int forumId, int voteId) {
        try {
            Connection connection = DbHelper.getConnection();
            List<String> stuNoList = new ArrayList<String>();
            String sql1 = "select * from user where class_id=" + forumId;
            PreparedStatement preparedStatement = connection.prepareStatement(sql1);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                stuNoList.add(resultSet.getString("stu_no"));
            }

            int size = stuNoList.size();
            int hasVoted = 0;
            String sql2 = "select * from vote where id=" + voteId;
            preparedStatement = connection.prepareStatement(sql2);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                hasVoted = resultSet.getInt("support") + resultSet.getInt("oppose");
            }
            if (size <= hasVoted) {
                return true;
            }

            int count = 1;
            Random random = new Random();
            String stuNo;
            do {
                stuNo = stuNoList.get(random.nextInt(size));
                String sql3 = "select count(*) from vote_history where stu_no='" + stuNo + "' and vote_id=" + voteId;
                preparedStatement = connection.prepareStatement(sql3);
                resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    count = resultSet.getInt(1);
                }
            } while (count != 0);
            addVoteHistory(stuNo, forumId, voteId, connection, preparedStatement);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static void addVoteHistory(String stuNo, int forumId, int voteId, Connection connection, PreparedStatement preparedStatement) {
        try {
            String sql = "insert into vote_history (stu_no,vote_id,status,class_id) values(?,?,?,?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, stuNo);
            preparedStatement.setInt(2, voteId);
            preparedStatement.setInt(3, 0);
            preparedStatement.setInt(4, forumId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean addAnnouncement(Announcement announcement) {
        Connection connection = null;
        try {
            connection = DbHelper.getConnection();
            PreparedStatement ps = null;
            String sql = "INSERT INTO announcement(title,content,forum_id) values(" + announcement.getTitle() + "," +
                    announcement.getContent() + "," + announcement.getForumId() + ")";
            ps = connection.prepareStatement(sql);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
