package com.example.kim.triple.data.model;

/**
 * Created by sihon on 2016-12-01.
 */

public class MissionCart {
    private int id;
    private int userId;
    private int missionId;
    private int missionResult;

    public int getId() {
        return id;
    }

    public int getMissionId() {
        return missionId;
    }

    public int getMissionResult() {
        return missionResult;
    }

    public int getUserId() {
        return userId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMissionId(int missionId) {
        this.missionId = missionId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setMissionResult(int missionResult) {
        this.missionResult = missionResult;
    }
}
