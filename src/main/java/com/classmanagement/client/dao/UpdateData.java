package com.classmanagement.client.dao;

import com.classmanagement.client.utils.DbHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * ClassManagement
 *
 * @author Richard-J
 * @description 更新数据
 * @date 2019.04
 */

public class UpdateData {
    public static boolean vote(Boolean isSupport, int voteId, String stuNo,int forumId) {
        String col;
        if (isSupport) {
            col = "support";
        } else {
            col = "oppose";
        }

        try {
            Connection connection = DbHelper.getConnection();
            String sql1 = "update vote set " + col + "=" + col + "+1 where id=" + voteId;
            PreparedStatement preparedStatement = connection.prepareStatement(sql1);
            preparedStatement.executeUpdate();

            String sql2 = "update vote_history set status=1 where vote_id=" + voteId + " and stu_no=" + stuNo;
            preparedStatement = connection.prepareStatement(sql2);
            preparedStatement.executeUpdate();

            DbHelper.close(preparedStatement, connection, null);

            AddData.selectUserToVote(forumId,voteId);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
