package com.ita.rank.pojo;

import lombok.Data;

@Data
public class ScoreBoardPojo extends ClassifiblePojo {
    private int id;

    private int eventId;

    private String eventName;

    private String round;

    private int week;

    private int season;

    private int player1Id;

    private String player1Name;

    private int player1Score;

    private int player1Rank;

    private int player2Id;

    private String player2Name;

    private int player2Score;

    private int player2Rank;

    private int wo;

    private int ret;

    private String eventLevel;

    private String levelCode;

    private int handle;

    // 0-单打 1-双打
    private int matchMode;

    public ScoreBoardPojo(int id, int eventId, String eventName, String round, int week, int season,
                          int player1Id, String player1Name, int player1Score, int player1Rank,
                          int player2Id, String player2Name, int player2Score, int player2Rank,
                          int wo, int ret, String eventLevel, String levelCode, int matchMode, int handle) {
        super(eventLevel, matchMode);
        this.id = id;
        this.eventId = eventId;
        this.eventName = eventName;
        this.round = round;
        this.week = week;
        this.season = season;
        this.player1Id = player1Id;
        this.player1Name = player1Name;
        this.player1Score = player1Score;
        this.player1Rank = player1Rank;
        this.player2Id = player2Id;
        this.player2Name = player2Name;
        this.player2Score = player2Score;
        this.player2Rank = player2Rank;
        this.wo = wo;
        this.ret = ret;
        this.levelCode = levelCode;
        this.handle = handle;
    }

    public ScoreBoardPojo(String eventName, String round, int season,
                          String player1Name, int player1Score,
                          String player2Name, int player2Score) {
        this.eventName = eventName;
        this.round = round;
        this.season = season;
        this.player1Name = player1Name;
        this.player1Score = player1Score;
        this.player2Name = player2Name;
        this.player2Score = player2Score;
    }

    public ScoreBoardPojo(String eventName, String round, int season,
                          String player1Name, int player1Score, int player1Rank,
                          String player2Name, int player2Score, int player2Rank,
                          int ret) {
        this.eventName = eventName;
        this.round = round;
        this.season = season;
        this.player1Name = player1Name;
        this.player1Score = player1Score;
        this.player1Rank = player1Rank;
        this.player2Name = player2Name;
        this.player2Score = player2Score;
        this.player2Rank = player2Rank;
        this.ret = ret;
    }

    public ScoreBoardPojo(String eventName, String round, int season,
                          String player1Name, int player1Score,
                          String player2Name, int player2Score,
                          int ret) {
        this.eventName = eventName;
        this.round = round;
        this.season = season;
        this.player1Name = player1Name;
        this.player1Score = player1Score;
        this.player2Name = player2Name;
        this.player2Score = player2Score;
        this.ret = ret;
    }

    public ScoreBoardPojo() { super(); }
}