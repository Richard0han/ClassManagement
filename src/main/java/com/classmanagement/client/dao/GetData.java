package com.classmanagement.client.dao;

import com.classmanagement.client.bean.User;
import com.classmanagement.client.utils.DbHelper;

import java.sql.Connection;

/**
 * ClassManager
 *
 * @author Richard-J
 * @description 获取数据库数据
 * @date 2019.03
 */

public class GetData {
    public static User getUser(){
        User user = new User();
        try {
            Connection connection = DbHelper.getConnection();
//            PreparedStatement preparedStatement
        }catch (Exception e){
            user = null;
            e.printStackTrace();
        }

        return user;
    }
}
