package com.classmanagement.client.bean;

/**
 * ClassManager
 *
 * @author Richard-J
 * @description 公告
 * @date 2019.03
 */

public class Announcement {
    private int id;
    private String title;
    private String content;
    private int forumId;

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
