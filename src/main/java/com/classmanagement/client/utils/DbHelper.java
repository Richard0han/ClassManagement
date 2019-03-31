package com.classmanagement.client.utils;

import java.sql.*;

/**
 * ClassManager
 *
 * @author Richard-J
 * @description 数据库连接类
 * @date 2019.03
 */

public class DbHelper {
    private static final String ip = "localhost";
    private static final String driver = "com.mysql.cj.jdbc.Driver";
    private static final String url = "jdbc:mysql://" + ip + ":3306/class_management" +
            "?useUnicode=true&&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&characterEncoding=UTF-8&useSSL=false";
    private static final String username = "root";
    private static final String password = "root";
    private static Connection con = null;

    static {
        try {
            Class.forName(driver);
        } catch (Exception var1) {
            var1.printStackTrace();
        }
    }

    public static Connection getConnection() throws Exception {
        Connection conn =  DriverManager.getConnection(url, username, password);
        return conn;
    }

    public static void close(PreparedStatement preparedStatement, Connection connection, ResultSet resultSet) {
        try {
            preparedStatement.close();
            connection.close();
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
