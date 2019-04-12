package com.classmanagement.client.utils;

import java.sql.*;

/**
 * @program: client
 * @description: 数据库连接
 * @author: Mr.Zhang
 * @create: 2019-03-31 17:54
 **/


public class DbUtil {
    private static final String driver = "com.mysql.cj.jdbc.Driver";
    private static final String dbUrl = "jdbc:mysql://localhost:3306/class_management?" +
            "useUnicode=true&&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&characterEncoding=UTF-8&useSSL=false";
    private static final String user = "root";
    private static final String psw = "root";

    static {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(dbUrl, user, psw);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }


    public static void close(Statement statement, Connection connection, ResultSet resultSet) {
        try {
            statement.close();
            connection.close();
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
