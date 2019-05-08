package com.classmanagement.client.bean;

import java.io.Serializable;

/**
 * ClassManagement
 *
 * @author Richard-J
 * @description 管理员日志
 * @date 2019.05
 */

public class AdministratorLog implements Serializable {
    private Vote vote;
    private Announcement announcement;

    public Vote getVote() {
        return vote;
    }

    public void setVote(Vote vote) {
        this.vote = vote;
    }

    public Announcement getAnnouncement() {
        return announcement;
    }

    public void setAnnouncement(Announcement announcement) {
        this.announcement = announcement;
    }
}
