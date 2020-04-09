package com.ita.rank.processor;

import com.ita.rank.pojo.GradeRecordPojo;
import com.ita.rank.common.utils.ExcelReaderUtil;
import com.ita.rank.enums.EventRound;
import com.ita.rank.pojo.ScoreBoardPojo;
import com.ita.rank.service.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.*;

/**
 * @Author: liuxingxin
 * @Date: 2019/2/14
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class ScoreCalculatorTest {

    @Autowired
    ScoreCalculator scoreCalculator;

    @Autowired
    ScoreBoardService scoreBoardService;

    @Autowired
    EventInfoService eventInfoService;

    @Autowired
    EventLevelService eventLevelService;

    @Autowired
    PlayerInfoService playerInfoService;

    @Autowired
    GradeRecordService gradeRecordService;

    @Autowired
    ExcelReaderUtil excelReaderUtil;

    @Autowired
    CurrentPhaseService currentPhaseService;

    @Test
    public void testGradeRecordUpdater() {
        scoreCalculator.gradeRecordUpdater();
    }

    @Test
    public void testAFScoreCalculator() {

//        // 更新第一季大小年终期间的积分和冠军积分
//        for (int i = 18; i <= 21; i ++) {
//            scoreCalculator.ptsRecordOfSeason1Initializer(i, 1);
//            scoreCalculator.championPtsRecordOfSeason1Initializer(i, 1);
//        }

//        // 更新第一季0-21期排名
//        for (int i = 0; i <= 21; i ++) {
//            scoreCalculator.rankUpdater(i, 1);
//        }
//
//        // 更新第一季1-21期冠军排名
//        for (int i = 0; i <= 21; i ++) {
//            scoreCalculator.championRankUpdater(i, 1);
//        }
//
//
//        // 更新第一季比分板排名
//        scoreBoardService.updateRankOfAllScoreRecord();
//
//        // 更新第二季第0期积分和排名
//        scoreCalculator.ptsRecordInitializer(0, 2);
//        scoreCalculator.rankUpdater(0, 2);
    }

    @Test
    public void testInsertGradeRecordOfSeason0() {
//        excelReaderUtil.insertPtsOfSeason0();
    }

    @Test
    public void testGradeRecordFromScoreBoard() {
    }

    @Test
    public void testWinnerGradeUpdater() {
    }

    @Test
    public void testLoserGradeUpdater() {
    }

    @Test
    public void testPtsCalculatorOfSeason1() {
//        scoreCalculator.ptsCalculatorOfSeason1(1001, 21, 1);
    }

    @Test
    public void testChampionPtsCalculatorOfSeason1() {
        scoreCalculator.championPtsCalculatorOfSeason1(1001, 10, 1);
    }

    @Test
    public void testPtsCalculator() {
        scoreCalculator.ptsCalculator(1002, 0, 2);
    }

    @Test
    public void testPtsRecordOfSeason1Update() {
//        scoreCalculator.ptsRecordOfSeason1Initializer(0, 1);
    }

    @Test
    public void testchampionPtsRecordOfSeason1Update() {
        scoreCalculator.championPtsRecordOfSeason1Initializer(0, 1);
    }

    @Test
    public void testRankUpdater() {
//        for (int week = 1; week < 16; week ++) {
//            scoreCalculator.ptsRecordInitializer(week, 1);
//            scoreCalculator.rankUpdater(week, 1);
//        }
//        scoreCalculator.ptsRecordInitializer(0, 2);
//        scoreCalculator.rankUpdater(0, 2);
//        scoreCalculator.ptsRecordInitializer(1, 2);
//        scoreCalculator.rankUpdater(1, 2);
//        scoreCalculator.championPtsRecordInitializer(10, 2);

        scoreCalculator.rankUpdater(2,2);
    }

    @Test
    public void testScoreImport() {
//        ScoreBoardPojo
    }

    @Test
    public void testCalculateWinningOrLosingStreak() {

        Map<String, List<Integer>> scoreRecord = new HashMap<>();
        List<ScoreBoardPojo> scoreBoardPojoList = scoreBoardService.selectAllScoreRecord();
        for (ScoreBoardPojo scoreBoardPojo : scoreBoardPojoList) {
            String player1Name = scoreBoardPojo.getPlayer1Name();
            String player2Name = scoreBoardPojo.getPlayer2Name();
            if (scoreBoardPojo.getPlayer1Score() > scoreBoardPojo.getPlayer2Score()) {
                if (scoreRecord.containsKey(player1Name)) {
                    scoreRecord.get(player1Name).add(1);
                } else {
                    List<Integer> list = new ArrayList<>();
                    list.add(1);
                    scoreRecord.put(player1Name, list);
                }

                if (scoreRecord.containsKey(player2Name)) {
                    scoreRecord.get(player2Name).add(0);
                } else {
                    List<Integer> list = new ArrayList<>();
                    list.add(0);
                    scoreRecord.put(player2Name, list);
                }
            } else {
                if (scoreRecord.containsKey(player1Name)) {
                    scoreRecord.get(player1Name).add(0);
                } else {
                    List<Integer> list = new ArrayList<>();
                    list.add(0);
                    scoreRecord.put(player1Name, list);
                }

                if (scoreRecord.containsKey(player2Name)) {
                    scoreRecord.get(player2Name).add(1);
                } else {
                    List<Integer> list = new ArrayList<>();
                    list.add(1);
                    scoreRecord.put(player2Name, list);
                }
            }
        }

        for (String playerName : scoreRecord.keySet()) {
            System.out.println(playerName + " " + scoreRecord.get(playerName));
        }

        Map<String, List<Integer>> winningOrLosingStreakMap = new HashMap<>();
        for (String playerName : scoreRecord.keySet()) {
            List<Integer> winningOrLosingStreakList = new ArrayList<>();
            winningOrLosingStreakList.add(getWinningStreak(scoreRecord.get(playerName)));
            winningOrLosingStreakList.add(getLosingStreak(scoreRecord.get(playerName)));
            winningOrLosingStreakMap.put(playerName, winningOrLosingStreakList);
        }

        List<Map.Entry<String, List<Integer>>> list1 = new ArrayList<Map.Entry<String, List<Integer>>>(winningOrLosingStreakMap.entrySet());

        Collections.sort(list1, new Comparator<Map.Entry<String, List<Integer>>>() {
            public int compare(Map.Entry<String, List<Integer>> o1,
                               Map.Entry<String, List<Integer>> o2) {
                return -o1.getValue().get(0).compareTo(o2.getValue().get(0));
            }
        });

        List<Map.Entry<String, List<Integer>>> list2 = new ArrayList<Map.Entry<String, List<Integer>>>(winningOrLosingStreakMap.entrySet());

        Collections.sort(list2, new Comparator<Map.Entry<String, List<Integer>>>() {
            public int compare(Map.Entry<String, List<Integer>> o1,
                               Map.Entry<String, List<Integer>> o2) {
                return -o1.getValue().get(1).compareTo(o2.getValue().get(1));
            }
        });

        System.out.println("连胜榜");
        for (int i = 0; i < list1.size(); i ++) {
            System.out.println(list1.get(i).getKey() + " " + list1.get(i).getValue().get(0));
        }

        System.out.println("连败榜");
        for (int i = 0; i < list2.size(); i ++) {
            System.out.println(list2.get(i).getKey() + " " + list2.get(i).getValue().get(1));
        }

    }

    private int getWinningStreak(List<Integer> list) {
        int winningStreak = 0, ws = 0;
        for (int result : list) {
            if (result == 1) {
                ws += 1;
            } else {
                if (ws > winningStreak) {
                    winningStreak = ws;
                }
                ws = 0;
            }
        }
        if (ws > winningStreak) {
            winningStreak = ws;
        }
        return winningStreak;
    }

    private int getLosingStreak(List<Integer> list) {
        int lossingStreak = 0, ls = 0;
        for (int result : list) {
            if (result == 0) {
                ls += 1;
            } else {
                if (ls > lossingStreak) {
                    lossingStreak = ls;
                }
                ls = 0;
            }
        }
        if (ls > lossingStreak) {
            lossingStreak = ls;
        }
        return lossingStreak;
    }

    @Test
    public void testCalculateValueOfChampion() {

        List<GradeRecordPojo> championPojos = gradeRecordService.selectByGrade(EventRound.W.getRound());
        Map<GradeRecordPojo, List<Integer>> championRivalRankList = new HashMap<>();
        for (GradeRecordPojo championPojo : championPojos) {
            int championId = championPojo.getPlayerId();
            List<ScoreBoardPojo> scoreBoardPojoList =
                    scoreBoardService.selectByPlayerIdAndEventIdWithoutRet(championId, championPojo.getEventId());
            List<Integer> rivalRanks = new ArrayList<>();
            for (ScoreBoardPojo scoreBoardPojo : scoreBoardPojoList) {
                if (scoreBoardPojo.getPlayer1Id() == championId) {
                    if (scoreBoardPojo.getPlayer1Score() > scoreBoardPojo.getPlayer2Score()) {
                        rivalRanks.add(scoreBoardPojo.getPlayer2Rank());
                    } else {
                        rivalRanks.add(-scoreBoardPojo.getPlayer2Rank());
                    }
                } else if (scoreBoardPojo.getPlayer2Id() == championId) {
                    if (scoreBoardPojo.getPlayer1Score() > scoreBoardPojo.getPlayer2Score()) {
                        rivalRanks.add(-scoreBoardPojo.getPlayer1Rank());
                    } else {
                        rivalRanks.add(scoreBoardPojo.getPlayer1Rank());
                    }
                }
            }
            championRivalRankList.put(championPojo, rivalRanks);
        }

        for (GradeRecordPojo championPojo : championRivalRankList.keySet()) {
            System.out.println(championPojo.getPlayerName() + " " +
                    championPojo.getEventName() + " " + championRivalRankList.get(championPojo));
        }


    }
}