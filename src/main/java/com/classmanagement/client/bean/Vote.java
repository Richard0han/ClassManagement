package com.classmanagement.client.bean;

/**
 * ClassManager
 *
 * @author Richard-J
 * @description 投票
 * @date 2019.03
 */

public class Vote {
    private int id;
    private String title;
    private String content;
    private int support;
    private int oppose;
    private int forumId;
    private String suggestion;

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

    public int getSupport() {
        return support;
    }

    public void setSupport(int support) {
        this.support = support;
    }

    public int getOppose() {
        return oppose;
    }

    public void setOppose(int oppose) {
        this.oppose = oppose;
    }

    public int getForumId() {
        return forumId;
    }

    public void setForumId(int forumId) {
        this.forumId = forumId;
    }

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }
}
