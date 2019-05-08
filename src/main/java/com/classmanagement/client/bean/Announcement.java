package com.classmanagement.client.bean;

import java.io.Serializable;

/**
 * ClassManager
 *
 * @author Richard-J
 * @description 公告
 * @date 2019.03
 */

public class Announcement implements Serializable {
    private int id;
    private String title;
    private String content;
    private int forumId;
    private String sender;

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getForumId() {
        return forumId;
    }

    public void setForumId(int forumId) {
        this.forumId = forumId;
    }
}
