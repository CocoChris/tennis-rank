package com.ita.rank.pojo;

import lombok.Data;

@Data
public class CurrentPhasePojo {
    private int id;

    private int currentWeek;

    private int currentSeason;

    public CurrentPhasePojo(int id, int currentWeek, int currentSeason) {
        this.id = id;
        this.currentWeek = currentWeek;
        this.currentSeason = currentSeason;
    }

    public CurrentPhasePojo() {
        super();
    }
}