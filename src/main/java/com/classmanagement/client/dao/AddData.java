package com.classmanagement.client.dao;

import com.classmanagement.client.bean.User;
import com.classmanagement.client.utils.DbHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * ClassManager
 *
 * @author Richard-J
 * @description 添加数据库数据
 * @date 2019.03
 */

public class AddData {
    /**
     * description 添加用户
     *
     * @param user
     * @return boolean
     */
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
}
