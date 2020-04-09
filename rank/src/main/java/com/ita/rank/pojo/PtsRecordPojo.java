package com.ita.rank.pojo;

import lombok.Data;

@Data
public class PtsRecordPojo {
    private int id;

    private int playerId;

    private String playerName;

    private int totalPts;

    private int rank;

    private int week;

    private int season;

    public PtsRecordPojo(int id, int playerId, String playerName, int totalPts, int rank, int week, int season) {
        this.id = id;
        this.playerId = playerId;
        this.playerName = playerName;
        this.totalPts = totalPts;
        this.rank = rank;
        this.week = week;
        this.season = season;
    }

    public PtsRecordPojo() {
        super();
    }
}