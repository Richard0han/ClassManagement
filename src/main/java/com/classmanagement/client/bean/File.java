package com.classmanagement.client.bean;

/**
 * ClassManager
 *
 * @author Richard-J
 * @description 文件
 * @date 2019.03
 */

public class File {
    private int id;
    private String name;
    private String describe;
    private String url;
    private int forumId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getForumId() {
        return forumId;
    }

    public void setForumId(int forumId) {
        this.forumId = forumId;
    }
}
