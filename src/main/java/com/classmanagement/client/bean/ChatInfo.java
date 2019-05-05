package com.classmanagement.client.bean;

import javax.swing.text.StyledDocument;
import java.io.Serializable;

/**
 * ClassManagement
 *
 * @author Richard-J
 * @description 生成聊天框所需的信息
 * @date 2019.04
 */

public class ChatInfo implements Serializable {
    /**
     * description 聊天种类，0为私聊，1为群聊
     */
    private int type;
    private User self;
    private User classmate;
    private Forum forum;
    private StyledDocument content;

    public StyledDocument getContent() {
        return content;
    }

    public void setContent(StyledDocument content) {
        this.content = content;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public User getSelf() {
        return self;
    }

    public void setSelf(User self) {
        this.self = self;
    }

    public User getClassmate() {
        return classmate;
    }

    public void setClassmate(User classmate) {
        this.classmate = classmate;
    }

    public Forum getForum() {
        return forum;
    }

    public void setForum(Forum forum) {
        this.forum = forum;
    }
}
