package com.example.kim.triple.data.model;

/**
 * Created by sihon on 2016-12-01.
 */

public class Mission {
    private int id;
    private int endTime;
    private String latitude;
    private String longitude;
    private int classification;
    private int tripLocationId;
    private String ImageUrl;
    private String Mname;
    private String Mexplan;

    public int getId() {
        return id;
    }

    public Mission setId(int id) {
        this.id = id;
        return this;
    }

    public int getEndTime() {
        return endTime;
    }

    public String getLatitude() {
        return latitude;
    }

    public Mission setEndTime(int endTime) {
        this.endTime = endTime;
        return this;
    }

    public Mission setLatitude(String latitude) {
        this.latitude = latitude;
        return this;
    }

    public int getClassification() {
        return classification;
    }

    public String getLongitude() {
        return longitude;
    }

    public Mission setLongitude(String longitude) {
        this.longitude = longitude;
        return this;
    }

    public Mission setClassification(int classification) {
        this.classification = classification;
        return this;
    }

    public int getTripLocationId() {
        return tripLocationId;
    }

    public Mission setTripLocationId(int tripLocationId) {
        this.tripLocationId = tripLocationId;
        return this;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public Mission setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
        return this;
    }
    public Mission setName(String name){
        Mname = name;
        return this;
    }
    public String getName(){return Mname;}

    public Mission setExplan(String explan){
        Mexplan = explan;
        return this;
    }
    public String getExplan(){return Mexplan;}

}
