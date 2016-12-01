package com.example.kim.triple.data.model;

/**
 * Created by sihon on 2016-12-01.
 */

public class TripLocation {
    private int id;
    private String name;
    private String picture;
    private String tag;
    private String phoneNumber;
    private String address;

    public int getId() {
        return id;
    }

    public TripLocation setName(String name) {
        this.name = name;
        return this;
    }

    public String getName() {
        return name;
    }

    public TripLocation setId(int id) {
        this.id = id;
        return this;
    }

    public String getPicture() {
        return picture;
    }

    public TripLocation setPicture(String picture) {
        this.picture = picture;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getTag() {
        return tag;
    }

    public TripLocation setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public TripLocation setTag(String tag) {
        this.tag = tag;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public TripLocation setAddress(String address) {
        this.address = address;
        return this;
    }
}
