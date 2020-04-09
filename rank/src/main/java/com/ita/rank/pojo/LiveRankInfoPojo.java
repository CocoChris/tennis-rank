package com.ita.rank.pojo;

import lombok.Data;

/**
 * @Author: liuxingxin
 * @Date: 2019/2/21
 */

@Data
public class LiveRankInfoPojo {

    private int liveRank;

    private int rankChange;

    private int highestRank;

    private int reachNewHighsOfRank;

    private String playerName;

    private int totalPts;

    private int minusPts;

    private int plusPts;

    private int minValidPts;

    private String currGrade;

    private int stateOfCurrWeek;

    private int numOfEntriesOfWholePeriod;

    private int numOfTitlesOfCareer;

    private int winsOfCurrSeason;

    private int lossesOfCurrSeason;

    private double winRateOfCurrSeason;

    private int winsOfCareer;

    private int lossesOfCareer;

    private double winRateOfCareer;

    private int winsOfCareerVsTop10;

    private int lossesOfCareerVsTop10;

    private double winRateOfCareerVsTop10;
}
