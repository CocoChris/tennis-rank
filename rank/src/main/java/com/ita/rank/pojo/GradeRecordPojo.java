package com.ita.rank.pojo;

import lombok.Data;

@Data
public class GradeRecordPojo extends ClassifiblePojo {
    private int id;

    private int playerId;

    private String playerName;

    private int eventId;

    private String eventName;

    private String levelCode;

    private String eventLevel;

    private String grade;

    private int qualified;

    private int pts;

    private int week;

    private int season;

//    public GradeRecordPojo(int id, int playerId, String playerName, int eventId, String eventName, String eventLevel, String levelCode, String grade, int qualified, int pts, int week, int season) {
//        super(eventLevel, matchMode);
//        this.id = id;
//        this.playerId = playerId;
//        this.playerName = playerName;
//        this.eventId = eventId;
//        this.eventName = eventName;
//        this.levelCode = levelCode;
//        this.grade = grade;
//        this.qualified = qualified;
//        this.pts = pts;
//        this.week = week;
//        this.season = season;
//    }

    public GradeRecordPojo(int playerId, String playerName, int eventId, String eventName, String eventLevel, String levelCode, String grade, int qualified, int pts, int week, int season) {
        super(eventLevel);
        this.playerId = playerId;
        this.playerName = playerName;
        this.eventId = eventId;
        this.eventName = eventName;
        this.levelCode = levelCode;
        this.grade = grade;
        this.qualified = qualified;
        this.pts = pts;
        this.week = week;
        this.season = season;
    }

    public GradeRecordPojo() { super(); }
}