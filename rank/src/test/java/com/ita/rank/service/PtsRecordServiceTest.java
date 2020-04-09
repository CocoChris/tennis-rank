package com.ita.rank.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.GsonBuilder;
import com.ita.rank.pojo.PtsRecordPojo;
import com.ita.rank.processor.RankSorter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

/**
 * @Author: liuxingxin
 * @Date: 2019/2/20
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class PtsRecordServiceTest {

    @Autowired
    PtsRecordService ptsRecordService;

    @Test
    public void testSelectByPlayerId() {
    }

    @Test
    public void testSelectByWeekAndSeason() {
        List<PtsRecordPojo> ptsRecordPojoList = ptsRecordService.selectByWeekAndSeason(0,2).subList(0, 10);
        RankSorter.sortRank(ptsRecordPojoList);
        JSONObject jsonObject = new JSONObject();
        JSONArray result = new JSONArray();
        jsonObject.put("id", 1);
        jsonObject.put("jsonrpc", "2.0");
        jsonObject.put("result", result);
        for (PtsRecordPojo ptsRecordPojo : ptsRecordPojoList) {
            JSONObject ptsRecordJson = new JSONObject();
            ptsRecordJson.put("rank", ptsRecordPojo.getRank());
            ptsRecordJson.put("playerName", ptsRecordPojo.getPlayerName());
            ptsRecordJson.put("totalPts", ptsRecordPojo.getTotalPts());
            ptsRecordJson.put("week", ptsRecordPojo.getWeek());
            ptsRecordJson.put("season", ptsRecordPojo.getSeason());
            result.add(ptsRecordJson);
        }

        System.out.println(new GsonBuilder().setPrettyPrinting().create().toJson(jsonObject));
    }

    @Test
    public void testSelectRankOfLastWeek() {
        System.out.println(ptsRecordService.selectRankOfLastWeek(1001, 2, 2));
    }

    @Test
    public void testUpdateTotalPts() {

    }

    @Test
    public void testUpdateRank() {
    }

    @Test
    public void insert() {
    }
}