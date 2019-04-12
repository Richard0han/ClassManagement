package com.classmanagement.client.dao;

import com.classmanagement.client.utils.DbHelper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @program: client
 * @description: 判断是否为第一次登陆
 * @author: Mr.Zhang
 * @create: 2019-03-31 18:21
 **/


public class IsFirstLogin {
    public static boolean isFirstLogin(String stuNo) {

        Connection connection = null;
        ResultSet resultSet = null;
        Statement stmt = null;

        try {
            connection = DbHelper.getConnection();
            stmt = connection.createStatement();
            String sql = "SELECT count(*) FROM user WHERE stu_No=" + stuNo;
            resultSet = stmt.executeQuery(sql);
            int count = 0;
            while (resultSet.next()) {
                count = resultSet.getInt(1);
            }

            if (count == 0) {
                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
