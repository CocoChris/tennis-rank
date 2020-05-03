package com.ita.rank.controller;

import com.alibaba.fastjson.JSONObject;
import com.ita.rank.pojo.PlayerStatsPojo;
import com.ita.rank.service.PlayerStatsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * @Author: liuxingxin
 * @Date: 2020/4/23
 */

@RestController
public class PlayerStatsController {

    @Autowired
    PlayerStatsService playerStatsService;

    private static final Logger logger = LoggerFactory.getLogger(PlayerStatsController.class);

    @RequestMapping(method = GET, value = "/api/stats/ptsComponent")
    @ResponseBody
    public PlayerStatsPojo showPlayerStats(@RequestParam("playerName") String playerName) {
        PlayerStatsPojo playerStatsPojo = playerStatsService.getPlayerStats(playerName);
        return playerStatsPojo;
    }
}
