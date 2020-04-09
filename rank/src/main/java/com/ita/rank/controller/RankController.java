package com.ita.rank.controller;

import com.alibaba.fastjson.JSONObject;
import com.ita.rank.service.RankService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * @Author: liuxingxin
 * @Date: 2018/11/22
 */

@RestController
public class RankController {

    @Autowired
    RankService rankService;

    public JSONObject liveRankInfoJson = new JSONObject();
    public JSONObject championRankInfoJson = new JSONObject();

    public final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private final Logger logger = LoggerFactory.getLogger(RankController.class);

//    @PostConstruct
    public void init() {

        logger.info("start initializing rankInfo");
        liveRankInfoJson = rankService.queryLiveRankInfo();
//        championRankInfoJson = rankService.queryChampionRankInfo();
        logger.info("The rank info init time is: {}", sdf.format(new Date()));
    }

    @RequestMapping(method = GET, value = "/api/liveRank")
    @ResponseBody
    public JSONObject showLiveRank() {

//        logger.info("rankInfo = {}", new GsonBuilder().setPrettyPrinting().create().toJson(liveRankInfoJson));

        return liveRankInfoJson;
    }

    @RequestMapping(method = GET, value = "/api/championRank")
    @ResponseBody
    public JSONObject showChampionRank() {

//        logger.info("rankInfo = {}", new GsonBuilder().setPrettyPrinting().create().toJson(liveRankInfoJson));

        return championRankInfoJson;
    }

//    @Scheduled(cron = "0 5 * * * ?")
    public void updateRankInfo() {

        long startTime = System.currentTimeMillis();
        logger.info("start updating rankInfo");
        liveRankInfoJson = rankService.queryLiveRankInfo();
//        championRankInfoJson = rankService.queryChampionRankInfo();
        logger.info("The rank info update time is: {}", sdf.format(new Date()));
        long endTime = System.currentTimeMillis();
        logger.info("程序运行时间：" + (endTime - startTime)/1000 + "s");
    }
}
