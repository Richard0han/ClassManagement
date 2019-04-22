package com.classmanagement.client.dao;

import com.classmanagement.client.bean.Forum;
import com.classmanagement.client.bean.User;
import com.classmanagement.client.bean.Vote;
import com.classmanagement.client.utils.DbHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * ClassManager
 *
 * @author Richard-J
 * @description 获取数据库数据
 * @date 2019.03
 */

public class GetData {
    public static User getUser(String stuNo) {
        User user = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DbHelper.getConnection();
            String sql = "select * from user where stu_no=" + stuNo;
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = new User();
                user.setNickname(resultSet.getString("nickname"));
                user.setName(resultSet.getString("name"));
                user.setSignature(resultSet.getString("signature"));
                user.setPortrait(resultSet.getInt("portrait"));
                user.setClassId(resultSet.getInt("class_id"));
                user.setClassName(resultSet.getString("class_name"));
                user.setSex(resultSet.getString("sex"));
                user.setIsManager(resultSet.getInt("is_manager"));
                user.setPort(resultSet.getInt("port"));
                user.setNetAddress(resultSet.getString("net_address"));
            }
        } catch (Exception e) {
            user = null;
            e.printStackTrace();
        } finally {
            DbHelper.close(preparedStatement, connection, resultSet);
        }

        return user;
    }

    public static List<Forum> getForum(User user) {
        List<Forum> list = new ArrayList<Forum>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Forum forum = null;
        try {
            String className = user.getClassName();
            connection = DbHelper.getConnection();
            String sql = "select * from forum where name='" + className + "' and is_class=1";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                forum = new Forum();
                forum.setId(resultSet.getInt("id"));
                forum.setName(className);
                forum.setIsClass(1);
                list.add(forum);
            }

            String sql1 = "select * from forum_history where stu_no=" + user.getStuNo();
            preparedStatement = connection.prepareStatement(sql1);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                forum = new Forum();
                forum.setId(resultSet.getInt("forum_id"));
                forum.setName(resultSet.getString("forum_name"));
                forum.setIsClass(0);
                list.add(forum);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbHelper.close(preparedStatement, connection, resultSet);
        }
        return list;
    }

    public static List<User> getClassmate(int classId) {
        List<User> list = new ArrayList<User>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        User user = null;
        try {
            connection = DbHelper.getConnection();
            String sql = "select * from user where class_id=" + classId;
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                user = new User();
                user.setStuNo(resultSet.getString("stu_no"));
                user.setNickname(resultSet.getString("nickname"));
                user.setName(resultSet.getString("name"));
                user.setSignature(resultSet.getString("signature"));
                user.setPortrait(resultSet.getInt("portrait"));
                user.setSex(resultSet.getString("sex"));
                user.setIsManager(resultSet.getInt("is_manager"));
                list.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbHelper.close(preparedStatement, connection, resultSet);
        }

        return list;
    }

    public static List<Vote> getVote(User user) {
        List<Vote> list = new ArrayList<Vote>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ResultSet rs = null;
        Vote vote = null;
        int classId = user.getClassId();
        String stuNo = user.getStuNo();
        try {
            connection = DbHelper.getConnection();
            String sql = "select * from vote_history where class_id=" + classId + " and stu_no=" + stuNo;
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                vote = new Vote();
                vote.setId(resultSet.getInt("vote_id"));
                vote.setOption(resultSet.getInt("option"));
                vote.setStatus(resultSet.getInt("status"));
                String sql0 = "select * from vote where id=" + vote.getId();
                preparedStatement = connection.prepareStatement(sql0);
                rs = preparedStatement.executeQuery();
                if (rs.next()) {
                    vote.setTitle(rs.getString("title"));
                    vote.setContent(rs.getString("content"));
                    vote.setSuggestion(rs.getString("suggestion"));
                    vote.setSupport(rs.getInt("support"));
                    vote.setOppose(rs.getInt("oppose"));
                    try {
                        rs.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                list.add(vote);
            }
            Collections.reverse(list);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbHelper.close(preparedStatement, connection, resultSet);
        }
        return list;
    }
}
