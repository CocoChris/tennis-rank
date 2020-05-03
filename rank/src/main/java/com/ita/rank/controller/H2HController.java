package com.ita.rank.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ita.rank.common.constants.NumConstants;
import com.ita.rank.pojo.PlayerInfoPojo;
import com.ita.rank.service.H2HService;
import com.ita.rank.service.PlayerInfoService;
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
 * @Date: 2019/3/8
 */

@RestController
@RequestMapping(value = "/api/h2h")
public class H2HController {

    @Autowired
    H2HService h2HService;

    @Autowired
    PlayerInfoService playerInfoService;

    private final Logger logger = LoggerFactory.getLogger(RankController.class);

    @RequestMapping(method = GET, value = "/vsPlayer")
    @ResponseBody
    public JSONObject showH2HVsPlayer(@RequestParam("player1Id") int player1Id,
                                      @RequestParam("player2Id") int player2Id,
                                      @RequestParam("singleOrDouble") int singleOrDouble) {

        JSONObject h2hRecordVsPlayerJson = new JSONObject();
        if (singleOrDouble == NumConstants.SINGLE_MATCH_MODE) {
            h2hRecordVsPlayerJson = h2HService.querySingleH2HVsPlayer(player1Id, player2Id);
        } else if (singleOrDouble == NumConstants.DOUBLE_MATCH_MODE) {
            h2hRecordVsPlayerJson = h2HService.queryDoubleH2HVsPlayer(player1Id, player2Id);
        }

        PlayerInfoPojo playerInfoPojo1 = playerInfoService.selectByPlayerId(player1Id);
        PlayerInfoPojo playerInfoPojo2 = playerInfoService.selectByPlayerId(player2Id);
        int searchCountOfPlayer1 = playerInfoPojo1.getLiveSearchCount();
        int searchCountOfPlayer2 = playerInfoPojo2.getLiveSearchCount();
        playerInfoPojo1.setLiveSearchCount(searchCountOfPlayer1 + 1);
        playerInfoPojo2.setLiveSearchCount(searchCountOfPlayer2 + 1);
        playerInfoService.updateLiveSearchCount(playerInfoPojo1);
        playerInfoService.updateLiveSearchCount(playerInfoPojo2);

//        System.out.println(h2hRecordVsPlayerJson.getString("player1Name") + " " + h2hRecordVsPlayerJson.getInteger("player1Rank"));
//        System.out.println(h2hRecordVsPlayerJson.getString("player2Name") + " " + h2hRecordVsPlayerJson.getInteger("player2Rank"));
//        System.out.println(h2hRecordVsPlayerJson.getInteger("winsOfPlayer1") + " - " + h2hRecordVsPlayerJson.getInteger("winsOfPlayer2"));
//        for (int i = 0; i < h2hRecordVsPlayerJson.getJSONArray("result").size(); i ++) {
//            JSONObject json = h2hRecordVsPlayerJson.getJSONArray("result").getJSONObject(i);
//            System.out.println(json.getString("season") + "\t" + json.getString("eventLevel") + "\t" +
//                    json.getString("matchMode") + "\t" + json.getString("eventName") + "\t" +
//                    json.getString("round") + "\t" + json.getString("result") + "\t" +
//                    json.getString("score") + "\t" + json.getInteger("resultFlag"));
//        }

        return h2hRecordVsPlayerJson;
    }

    @RequestMapping(method = GET, value = "/vsTopN")
    @ResponseBody
    public JSONObject showH2HVsTopN(@RequestParam("playerId") int playerId,
                                    @RequestParam("topN") int topN) {

        JSONObject h2hRecordVsTopNJson = h2HService.queryH2HVsTopN(playerId, topN);

        PlayerInfoPojo playerInfoPojo = playerInfoService.selectByPlayerId(playerId);
        int searchCount = playerInfoPojo.getLiveSearchCount();
        playerInfoPojo.setLiveSearchCount(searchCount + 1);
        playerInfoService.updateLiveSearchCount(playerInfoPojo);

//        System.out.println(h2hRecordVsTopNJson.getString("playerName") + " " + h2hRecordVsTopNJson.getInteger("playerRank"));
//        System.out.println(h2hRecordVsTopNJson.getInteger("winsOfPlayer") + " - " + h2hRecordVsTopNJson.getInteger("winsOfTopN"));
//        for (int i = 0; i < h2hRecordVsTopNJson.getJSONArray("result").size(); i ++) {
//            JSONObject json = h2hRecordVsTopNJson.getJSONArray("result").getJSONObject(i);
//            System.out.println(json.getString("season") + "\t" + json.getString("eventLevel") + "\t" +
//                    json.getString("matchMode") + "\t" + json.getString("eventName") + "\t" +
//                    json.getString("round") + "\t" + json.getString("result") + "\t" +
//                    json.getString("score") + "\t" + json.getInteger("resultFlag"));
//        }

        return h2hRecordVsTopNJson;
    }

    @RequestMapping(method = GET, value = "/playerList")
    @ResponseBody
    public JSONArray showPlayerList() {

        JSONArray playerList = h2HService.queryPlayerList();

        return playerList;
    }
}
