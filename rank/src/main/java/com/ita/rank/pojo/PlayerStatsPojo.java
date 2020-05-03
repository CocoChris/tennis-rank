package com.ita.rank.pojo;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

/**
 * @Author: liuxingxin
 * @Date: 2020/4/22
 */

@Data
public class PlayerStatsPojo {

    private int playerId;

    private String playerName;

    private int currRank;

    private int highestRank;

    private int singleWinsOfCurrSeason;

    private int singleLossesOfCurrSeason;

    private int doubleWinsOfCurrSeason;

    private int doubleLossesOfCurrSeason;

    private JSONObject ptsComponentInEventLevel;

    private JSONObject ptsComponentInEventDate;

    private JSONObject rankHistory;

    private JSONObject singleWinRateDist;
}
