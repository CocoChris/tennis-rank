package com.ita.rank.service;

import com.ita.rank.pojo.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: liuxingxin
 * @Date: 2019/2/13
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class ScoreBoardServiceTest {

    @Autowired
    ScoreBoardService scoreBoardService;

    @Autowired
    ScoreBoardDoubleService scoreBoardDoubleService;

    @Autowired
    EventInfoService eventInfoService;

    @Autowired
    EventLevelService eventLevelService;

    @Autowired
    PtsRecordService ptsRecordService;

    @Autowired
    PlayerInfoService playerInfoService;

    @Test
    public void testInsert() {

//        ScoreBoardPojo scoreBoardPojo = new ScoreBoardPojo("广州公开赛", "R1", 1, "LisaHurry", 0, "ming", 1);
//        scoreBoardService.insert(scoreBoardPojo);
//        scoreBoardPojo = new ScoreBoardPojo("广州公开赛", "QFi", 1, "zk1995", 0, "ming", 1);
//        scoreBoardService.insert(scoreBoardPojo);

        ScoreBoardPojo scoreBoardPojo = new ScoreBoardPojo("广州公开赛", "R1", 1, "LisaHurry", 0, "ming", 1);
        System.out.println(scoreBoardPojo.toString());
    }

    @Test
    public void testInsertBatch() {
        try {
            String fileName = "src/main/resources/txt/score_week_13_season_2.txt";
            String[] splits1 = fileName.split("/");
            String[] splits2 = splits1[splits1.length - 1].split("_");

            int week = Integer.valueOf(splits2[2]);
            int season = Integer.valueOf(splits2[4].split("\\.")[0]);

            System.out.println("week = " + week + ", season = " + season);

            EventInfoPojo eventInfoPojo = eventInfoService.selectBySeasonAndWeek(season, week);
            int eventId = eventInfoPojo.getEventId();
            String eventName = eventInfoPojo.getEventName();
            String levelCode = eventInfoPojo.getLevelCode();
            String eventLevel = eventLevelService.selectLevelByCode(levelCode).getLevel();
            int matchMode = eventInfoPojo.getEventType();

            FileInputStream inputStream = new FileInputStream(fileName);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String str;
            List<ScoreBoardPojo> scoreBoardList = new ArrayList<>();
            List<ScoreBoardDoublePojo> scoreBoardDoubleList = new ArrayList<>();
            while((str = bufferedReader.readLine()) != null)
            {
                if (!str.equals("")) {
//                    System.out.println(str);
                    String[] values = str.split(",");
                    if (values[0].split("/").length == 1) {
                        // 单打比分
                        String player1Name = values[0];
                        int player1Score = Integer.valueOf(values[1]);
                        String player2Name = values[3];
                        int player2Score = Integer.valueOf(values[2]);

                        int player1Id = playerInfoService.selectByPlayerName(player1Name).getPlayerId();
                        int player1Rank = ptsRecordService.selectRankOfLastWeek(player1Id, week, season);
                        int player2Id = playerInfoService.selectByPlayerName(player2Name).getPlayerId();
                        int player2Rank = ptsRecordService.selectRankOfLastWeek(player2Id, week, season);
    
                        String round = values[4];
                        int size = values.length;
    
                        ScoreBoardPojo scoreBoardPojo = new ScoreBoardPojo();
                        scoreBoardPojo.setWeek(week);
                        scoreBoardPojo.setSeason(season);
                        scoreBoardPojo.setEventId(eventId);
                        scoreBoardPojo.setEventName(eventName);
                        scoreBoardPojo.setLevelCode(levelCode);
                        scoreBoardPojo.setEventLevel(eventLevel);
                        scoreBoardPojo.setMatchMode(matchMode);
    
                        scoreBoardPojo.setPlayer1Name(player1Name);
                        scoreBoardPojo.setPlayer2Name(player2Name);
                        scoreBoardPojo.setPlayer1Id(player1Id);
                        scoreBoardPojo.setPlayer2Id(player2Id);
                        scoreBoardPojo.setPlayer1Score(player1Score);
                        scoreBoardPojo.setPlayer2Score(player2Score);
                        scoreBoardPojo.setPlayer1Rank(player1Rank);
                        scoreBoardPojo.setPlayer2Rank(player2Rank);
                        scoreBoardPojo.setRound(round);
                        scoreBoardPojo.setWo(0);
                        scoreBoardPojo.setRet(0);
    
                        if (size == 6) {
                            int wo = Integer.valueOf(values[5]);
                            scoreBoardPojo.setWo(wo);
                        }
    
                        if (size == 7) {
                            int wo = Integer.valueOf(values[5]);
                            scoreBoardPojo.setWo(wo);
                            int ret = Integer.valueOf(values[6]);
                            scoreBoardPojo.setRet(ret);
                        }
    
                        scoreBoardList.add(scoreBoardPojo);
                    } else {
                        // 双打比分
                        String[] player1Name = values[0].split("/");
                        String player1NameA = player1Name[0];
                        String player1NameB = player1Name[1];
                        System.out.println(player1NameA);
                        int player1Score = Integer.valueOf(values[1]);
                        String[] player2Name = values[3].split("/");
                        String player2NameA = player2Name[0];
                        String player2NameB = player2Name[1];
                        int player2Score = Integer.valueOf(values[2]);

                        int player1IdA = playerInfoService.selectByPlayerName(player1NameA).getPlayerId();
                        int player1RankA = ptsRecordService.selectRankOfLastWeek(player1IdA, week, season);
                        int player1IdB = playerInfoService.selectByPlayerName(player1NameB).getPlayerId();
                        int player1RankB = ptsRecordService.selectRankOfLastWeek(player1IdB, week, season);
                        int player2IdA = playerInfoService.selectByPlayerName(player2NameA).getPlayerId();
                        int player2RankA = ptsRecordService.selectRankOfLastWeek(player2IdA, week, season);
                        int player2IdB = playerInfoService.selectByPlayerName(player2NameB).getPlayerId();
                        int player2RankB = ptsRecordService.selectRankOfLastWeek(player2IdB, week, season);

                        String round = values[4];
                        int size = values.length;

                        ScoreBoardDoublePojo scoreBoardDoublePojo = new ScoreBoardDoublePojo();
                        scoreBoardDoublePojo.setWeek(week);
                        scoreBoardDoublePojo.setSeason(season);
                        scoreBoardDoublePojo.setEventId(eventId);
                        scoreBoardDoublePojo.setEventName(eventName);
                        scoreBoardDoublePojo.setLevelCode(levelCode);
                        scoreBoardDoublePojo.setEventLevel(eventLevel);
                        scoreBoardDoublePojo.setMatchMode(matchMode);

                        scoreBoardDoublePojo.setPlayer1NameA(player1NameA);
                        scoreBoardDoublePojo.setPlayer1NameB(player1NameB);
                        scoreBoardDoublePojo.setPlayer2NameA(player2NameA);
                        scoreBoardDoublePojo.setPlayer2NameB(player2NameB);
                        scoreBoardDoublePojo.setPlayer1IdA(player1IdA);
                        scoreBoardDoublePojo.setPlayer1IdB(player1IdB);
                        scoreBoardDoublePojo.setPlayer2IdA(player2IdA);
                        scoreBoardDoublePojo.setPlayer2IdB(player2IdB);
                        scoreBoardDoublePojo.setPlayer1RankA(player1RankA);
                        scoreBoardDoublePojo.setPlayer1RankB(player1RankB);
                        scoreBoardDoublePojo.setPlayer2RankA(player2RankA);
                        scoreBoardDoublePojo.setPlayer2RankB(player2RankB);
                        scoreBoardDoublePojo.setPlayer1Score(player1Score);
                        scoreBoardDoublePojo.setPlayer2Score(player2Score);
                        scoreBoardDoublePojo.setRound(round);
                        scoreBoardDoublePojo.setWo(0);
                        scoreBoardDoublePojo.setRet(0);

                        if (size == 6) {
                            int wo = Integer.valueOf(values[5]);
                            scoreBoardDoublePojo.setWo(wo);
                        }

                        if (size == 7) {
                            int ret = Integer.valueOf(values[6]);
                            scoreBoardDoublePojo.setRet(ret);
                        }

                        scoreBoardDoubleList.add(scoreBoardDoublePojo);
                    } 
                }
            }

            System.out.println(scoreBoardList);
            System.out.println(scoreBoardDoubleList);

            if (scoreBoardList.size() > 0) {
                scoreBoardService.insertBatch(scoreBoardList);
            }
            if (scoreBoardDoubleList.size() > 0) {
                scoreBoardDoubleService.insertBatch(scoreBoardDoubleList);
            }

            //close
            inputStream.close();
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}