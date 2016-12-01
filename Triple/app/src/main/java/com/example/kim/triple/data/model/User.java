package com.example.kim.triple.data.model;

/**
 * Created by sihon on 2016-12-01.
 */

public class User {
    private int id;
    private String name;
    private int level;

    public int getId() {
        return id;
    }

    public User setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public int getLevel() {
        return level;
    }

    public User setLevel(int level) {
        this.level = level;
        return this;
    }

}
