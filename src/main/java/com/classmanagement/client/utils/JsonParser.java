package com.classmanagement.client.utils;

import com.classmanagement.client.bean.User;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;


/**
 * isduAUDIT
 *
 * @author Richard-J
 * @description Json解析
 * @date 2019.02
 */

public class JsonParser {
    /**
     * description getUser
     *
     * @param cookie
     * @return com.classmanagement.client.bean.User
     */
    public static User getUser(String cookie) throws Exception {
        User user = new User();
        String json = LoginVerification.getJSON(cookie);
        JSONObject jsonObject = JSON.parseObject(json);
        String key = "object";
        jsonObject = jsonObject.getJSONObject(key);
        key = "xm";
        user.setName(jsonObject.getString(key));
        key = "bm";
        user.setClassName(jsonObject.getString(key));
        key = "xb";
        user.setSex(jsonObject.getString(key));
        key = "id";
        user.setStuNo(jsonObject.getString(key));
        return user;

    }
}
