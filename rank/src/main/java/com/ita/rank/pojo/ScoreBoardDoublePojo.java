package com.ita.rank.pojo;

import lombok.Data;

@Data
public class ScoreBoardDoublePojo extends ClassifiblePojo {
    private int id;

    private int eventId;

    private String eventName;

    private String round;

    private int week;

    private int season;

    private int player1IdA;

    private String player1NameA;

    private int player1RankA;

    private int player1IdB;

    private String player1NameB;

    private int player1RankB;

    private int player1Score;

    private int player2IdA;

    private String player2NameA;

    private int player2RankA;

    private int player2IdB;

    private String player2NameB;

    private int player2RankB;

    private int player2Score;

    private int wo;

    private int ret;

    private String eventLevel;

    private String levelCode;

    private int handle;

    // 0-单打 1-双打
    private int matchMode;

    public ScoreBoardDoublePojo() { super(); }
}