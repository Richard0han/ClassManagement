package com.classmanagement.client.bean;

import java.io.Serializable;

/**
 * ClassManagement
 *
 * @author Richard-J
 * @description 群
 * @date 2019.04
 */

public class Forum implements Serializable {
    private int id;
    private String name;
    private int isClass;

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

    public int getIsClass() {
        return isClass;
    }

    public void setIsClass(int isClass) {
        this.isClass = isClass;
    }
}
