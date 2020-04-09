package com.ita.rank.pojo;

import lombok.Data;

@Data
public class EventInfoPojo {

    private int id;

    private int eventId;

    private String eventName;

    private String levelCode;

    private int eventType;

    private int eventMode;

    private int week;

    private int season;

    private String date;

    public EventInfoPojo(int id, int eventId, String eventName, String levelCode, int eventType, int eventMode, int week, int season, String date) {
        this.id = id;
        this.eventId = eventId;
        this.eventName = eventName;
        this.levelCode = levelCode;
        this.eventType = eventType;
        this.eventMode = eventMode;
        this.week = week;
        this.season = season;
        this.date = date;
    }

    public EventInfoPojo() { super(); }
}