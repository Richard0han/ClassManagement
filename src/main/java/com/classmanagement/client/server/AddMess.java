package com.classmanagement.client.server;

import com.classmanagement.client.bean.User;
import com.classmanagement.client.utils.DbHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 * @program: client
 * @description:
 * @author: Mr.Zhang
 * @create: 2019-04-19 19:07
 **/


public class AddMess {
    public static List<User> getUserList(int classId) {
        List<User> users = new ArrayList<User>();
        User user;
        try {
            Connection connection = DbHelper.getConnection();
            ResultSet resultSet = null;
            PreparedStatement stmt = null;
            String sql = "SELECT * FROM user WHERE class_id=" + classId;
            stmt = connection.prepareStatement(sql);
            resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                user = new User();
                user.setName(resultSet.getString("name"));
                user.setIsManager(resultSet.getInt("is_manager"));
                user.setPortrait(resultSet.getInt("portrait"));
                user.setStuNo(resultSet.getString("stu_no"));
                users.add(user);
            }
            DbHelper.close(stmt, connection, resultSet);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return users;
    }

}
