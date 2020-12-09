package com.ita.rank.processor;

import com.ita.rank.common.constants.EventLevelConstants;
import com.ita.rank.common.constants.NumConstants;
import com.ita.rank.common.utils.CommonUtil;
import com.ita.rank.enums.EventRound;
import com.ita.rank.pojo.*;
import com.ita.rank.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Author: liuxingxin
 * @Date: 2019/2/14
 */

@Service
public class ScoreCalculator {

    @Autowired
    PlayerInfoService playerInfoService;

    @Autowired
    ScoreBoardService scoreBoardService;

    @Autowired
    ScoreBoardDoubleService scoreBoardDoubleService;

    @Autowired
    GradeRecordService gradeRecordService;

    @Autowired
    EventLevelService eventLevelService;

    @Autowired
    CurrentPhaseService currentPhaseService;

    @Autowired
    PtsRecordService ptsRecordService;

    @Autowired
    PtsRecordChampionService ptsRecordChampionService;

    private final Logger logger = LoggerFactory.getLogger(ScoreCalculator.class);

    /**
     * 更新grade_record表
     * @return
     */
    public void gradeRecordUpdater() {

        // 单打比分
        List<ScoreBoardPojo> unhandledScoreBoardPojoList = scoreBoardService.selectUnhandled();
        for (ScoreBoardPojo scoreBoardPojoUnhandled : unhandledScoreBoardPojoList) {
            int week = scoreBoardPojoUnhandled.getWeek();
            int season = scoreBoardPojoUnhandled.getSeason();
            if (season > currentPhaseService.selectCurrentPhase().getCurrentSeason()) {
                currentPhaseService.updateWeek(week);
                currentPhaseService.updateSeason(season);

                ptsRecordInitializer(week, season);
                championPtsRecordInitializer(week, season);
            } else if (season == currentPhaseService.selectCurrentPhase().getCurrentSeason() && week > currentPhaseService.selectCurrentPhase().getCurrentWeek()) {
                currentPhaseService.updateWeek(week);

                ptsRecordInitializer(week, season);
                championPtsRecordInitializer(week, season);
            }
            try {
                updateGradeRecordFromScoreBoard(scoreBoardPojoUnhandled);
                updatePtsRecordFromScoreBoard(scoreBoardPojoUnhandled);
                scoreBoardService.updateHandle(scoreBoardPojoUnhandled);
            } catch (Exception e) {
                logger.error("fail to update grade record from score board, the score board id = {}", scoreBoardPojoUnhandled.getId());
            }
        }

        // 双打比分
        List<ScoreBoardDoublePojo> unhandledScoreBoardDoublePojoList = scoreBoardDoubleService.selectUnhandled();
        for (ScoreBoardDoublePojo scoreBoardDoublePojoUnhandled : unhandledScoreBoardDoublePojoList) {
            int week = scoreBoardDoublePojoUnhandled.getWeek();
            int season = scoreBoardDoublePojoUnhandled.getSeason();
            if (season > currentPhaseService.selectCurrentPhase().getCurrentSeason()) {
                currentPhaseService.updateWeek(week);
                currentPhaseService.updateSeason(season);

                ptsRecordInitializer(week, season);
                championPtsRecordInitializer(week, season);
            } else if (season == currentPhaseService.selectCurrentPhase().getCurrentSeason() && week > currentPhaseService.selectCurrentPhase().getCurrentWeek()) {
                currentPhaseService.updateWeek(week);

                ptsRecordInitializer(week, season);
                championPtsRecordInitializer(week, season);
            }
            try {
                updateGradeRecordFromScoreBoardDouble(scoreBoardDoublePojoUnhandled);
                updatePtsRecordFromScoreBoardDouble(scoreBoardDoublePojoUnhandled);
                scoreBoardDoubleService.updateHandle(scoreBoardDoublePojoUnhandled);
            } catch (Exception e) {
                logger.error("fail to update grade record from score board double, the score board id = {}", scoreBoardDoublePojoUnhandled.getId());
            }
        }
    }

    /**
     * 基于score_board表更新或初始化grade_record的记录
     * @param scoreBoardPojo
     * @return
     */
    public int updateGradeRecordFromScoreBoard(ScoreBoardPojo scoreBoardPojo) {

        int winnerId, loserId;
        String winnerName, loserName;
        if (scoreBoardPojo.getPlayer1Score() > scoreBoardPojo.getPlayer2Score() || scoreBoardPojo.getRet() == 2) {
            winnerId = scoreBoardPojo.getPlayer1Id();
            winnerName = scoreBoardPojo.getPlayer1Name();
            loserId = scoreBoardPojo.getPlayer2Id();
            loserName = scoreBoardPojo.getPlayer2Name();
        } else if (scoreBoardPojo.getPlayer1Score() < scoreBoardPojo.getPlayer2Score() || scoreBoardPojo.getRet() == 1) {
            winnerId = scoreBoardPojo.getPlayer2Id();
            winnerName = scoreBoardPojo.getPlayer2Name();
            loserId = scoreBoardPojo.getPlayer1Id();
            loserName = scoreBoardPojo.getPlayer1Name();
        } else {
            logger.error("the scores can not be equal " + scoreBoardPojo.toString());
            return 0;
        }

        logger.info("winner = " + winnerName + " loser = " + loserName);

        winnerGradeUpdater(winnerId, winnerName, scoreBoardPojo);
        loserGradeUpdater(loserId, loserName, scoreBoardPojo);

        return 1;
    }

    public void winnerGradeUpdater(int winnerId, String winnerName, ScoreBoardPojo scoreBoardPojo) {

        int eventId = scoreBoardPojo.getEventId();
        String round = scoreBoardPojo.getRound();
        String levelCode = scoreBoardPojo.getLevelCode();
        int week = scoreBoardPojo.getWeek();
        int season = scoreBoardPojo.getSeason();

        String currGrade = eventLevelService.getNextRound(round, levelCode);

        String maxGrade = currGrade;
        int qualified = 0;
        GradeRecordPojo gradeRecordPojoOfWinner = gradeRecordService.selectByPlayerId(winnerId, eventId);
        if (gradeRecordPojoOfWinner != null) {
            maxGrade = EventRound.max(currGrade, gradeRecordPojoOfWinner.getGrade());
            qualified = gradeRecordPojoOfWinner.getQualified();
            if (gradeRecordPojoOfWinner.getGrade().equals(eventLevelService.getInitRound(EventRound.F.getRound(), levelCode)) && EventRound.compare(EventRound.Q.getRound(), currGrade)) {
                maxGrade = EventRound.Q.getRound();
            }
        }
        if (currGrade.equals(EventRound.Q.getRound())) {
            qualified = 1;
        }
        int pts;
        if (EventRound.compare(maxGrade, eventLevelService.getNextRound(eventLevelService.getInitRound(EventRound.F.getRound(), levelCode), levelCode))) {
            Integer scoreOfQ = eventLevelService.getScore(EventRound.Q.getRound(), levelCode, season);
            if (scoreOfQ == null) {
                pts = eventLevelService.getScore(maxGrade, levelCode, season);
            } else {
                pts = eventLevelService.getScore(maxGrade, levelCode, season) + qualified * scoreOfQ;
            }
        } else {
            pts = eventLevelService.getScore(maxGrade, levelCode, season);
        }

        logger.info("maxGrade = {}", maxGrade);
        logger.info("qualified = {}", qualified);
        logger.info("pts = {}", pts);

        if (gradeRecordPojoOfWinner == null) {
            gradeRecordPojoOfWinner = new GradeRecordPojo();
            gradeRecordPojoOfWinner.setPlayerId(winnerId);
            gradeRecordPojoOfWinner.setPlayerName(winnerName);
            gradeRecordPojoOfWinner.setEventId(scoreBoardPojo.getEventId());
            gradeRecordPojoOfWinner.setEventName(scoreBoardPojo.getEventName());
            gradeRecordPojoOfWinner.setLevelCode(levelCode);
            gradeRecordPojoOfWinner.setEventLevel(scoreBoardPojo.getEventLevel());
            gradeRecordPojoOfWinner.setGrade(maxGrade);
            gradeRecordPojoOfWinner.setQualified(qualified);
            gradeRecordPojoOfWinner.setPts(pts);
            gradeRecordPojoOfWinner.setWeek(week);
            gradeRecordPojoOfWinner.setSeason(season);

            System.out.println(gradeRecordPojoOfWinner);
            gradeRecordService.insert(gradeRecordPojoOfWinner);
        } else {
            gradeRecordPojoOfWinner.setGrade(maxGrade);
            gradeRecordPojoOfWinner.setQualified(qualified);
            gradeRecordPojoOfWinner.setPts(pts);

            gradeRecordService.updateGrade(gradeRecordPojoOfWinner);
        }
    }

    public void loserGradeUpdater(int loserId, String loserName, ScoreBoardPojo scoreBoardPojo) {

        System.out.println(scoreBoardPojo);

        int eventId = scoreBoardPojo.getEventId();
        String round = scoreBoardPojo.getRound();
        String levelCode = scoreBoardPojo.getLevelCode();
        int week = scoreBoardPojo.getWeek();
        int season = scoreBoardPojo.getSeason();
        int wo = scoreBoardPojo.getWo();

        System.out.println("loser: " + eventId + ", " + round + ", " + levelCode + ", " + week + ", " + season);

        String grade = eventLevelService.getInitRound(round, levelCode);

        int pts = eventLevelService.getScore(grade, levelCode, season);

        System.out.println("grade = " + grade + "pts = " + pts);

        GradeRecordPojo gradeRecordPojoOfLoser = gradeRecordService.selectByPlayerId(loserId, eventId);
        if (gradeRecordPojoOfLoser == null) {
            if (wo != 0) {
                pts = 0;
            }

            gradeRecordPojoOfLoser = new GradeRecordPojo();
            gradeRecordPojoOfLoser.setPlayerId(loserId);
            gradeRecordPojoOfLoser.setPlayerName(loserName);
            gradeRecordPojoOfLoser.setEventId(scoreBoardPojo.getEventId());
            gradeRecordPojoOfLoser.setEventName(scoreBoardPojo.getEventName());
            gradeRecordPojoOfLoser.setLevelCode(levelCode);
            gradeRecordPojoOfLoser.setEventLevel(scoreBoardPojo.getEventLevel());
            gradeRecordPojoOfLoser.setGrade(grade);
            gradeRecordPojoOfLoser.setPts(pts);
            gradeRecordPojoOfLoser.setWeek(week);
            gradeRecordPojoOfLoser.setSeason(season);

            System.out.println(gradeRecordPojoOfLoser);
            gradeRecordService.insert(gradeRecordPojoOfLoser);
        } else {
            if (gradeRecordPojoOfLoser.getGrade().equals(eventLevelService.getInitRound(EventRound.F.getRound(), levelCode))) {
                String minGrade = eventLevelService.getInitRound(round, levelCode);
                int minPts = eventLevelService.getScore(minGrade, levelCode, season);
                gradeRecordPojoOfLoser.setGrade(minGrade);
                gradeRecordPojoOfLoser.setPts(minPts);

                gradeRecordService.updateGrade(gradeRecordPojoOfLoser);
            } else if (round.equals(EventRound.QFi.getRound())) {
                String maxGrade = EventRound.max(round, gradeRecordPojoOfLoser.getGrade());
                int maxPts = eventLevelService.getScore(maxGrade, levelCode, season);

                gradeRecordPojoOfLoser.setGrade(maxGrade);
                gradeRecordPojoOfLoser.setPts(maxPts);

                gradeRecordService.updateGrade(gradeRecordPojoOfLoser);
            }
        }
    }

    public int updateGradeRecordFromScoreBoardDouble(ScoreBoardDoublePojo scoreBoardDoublePojo) {

        int winnerIdA, winnerIdB, loserIdA, loserIdB;
        String winnerNameA, winnerNameB, loserNameA, loserNameB;
        if (scoreBoardDoublePojo.getPlayer1Score() > scoreBoardDoublePojo.getPlayer2Score() || scoreBoardDoublePojo.getRet() == 2) {
            winnerIdA = scoreBoardDoublePojo.getPlayer1IdA();
            winnerNameA = scoreBoardDoublePojo.getPlayer1NameA();
            winnerIdB = scoreBoardDoublePojo.getPlayer1IdB();
            winnerNameB = scoreBoardDoublePojo.getPlayer1NameB();
            loserIdA = scoreBoardDoublePojo.getPlayer2IdA();
            loserNameA = scoreBoardDoublePojo.getPlayer2NameA();
            loserIdB = scoreBoardDoublePojo.getPlayer2IdB();
            loserNameB = scoreBoardDoublePojo.getPlayer2NameB();
        } else if (scoreBoardDoublePojo.getPlayer1Score() < scoreBoardDoublePojo.getPlayer2Score() || scoreBoardDoublePojo.getRet() == 1) {
            winnerIdA = scoreBoardDoublePojo.getPlayer2IdA();
            winnerNameA = scoreBoardDoublePojo.getPlayer2NameA();
            winnerIdB = scoreBoardDoublePojo.getPlayer2IdB();
            winnerNameB = scoreBoardDoublePojo.getPlayer2NameB();
            loserIdA = scoreBoardDoublePojo.getPlayer1IdA();
            loserNameA = scoreBoardDoublePojo.getPlayer1NameA();
            loserIdB = scoreBoardDoublePojo.getPlayer1IdB();
            loserNameB = scoreBoardDoublePojo.getPlayer1NameB();
        } else {
            logger.error("the scores can not be equal " + scoreBoardDoublePojo.toString());
            return 0;
        }

        logger.info("winner = " + winnerNameA + "/" + winnerNameB + " loser = " + loserNameA + "/" + loserNameB);

        winnerGradeUpdater(winnerIdA, winnerNameA, scoreBoardDoublePojo);
        winnerGradeUpdater(winnerIdB, winnerNameB, scoreBoardDoublePojo);
        loserGradeUpdater(loserIdA, loserNameA, scoreBoardDoublePojo);
        loserGradeUpdater(loserIdB, loserNameB, scoreBoardDoublePojo);

        return 1;
    }

    public void winnerGradeUpdater(int winnerId, String winnerName, ScoreBoardDoublePojo scoreBoardDoublePojo) {

        int eventId = scoreBoardDoublePojo.getEventId();
        String round = scoreBoardDoublePojo.getRound();
        String levelCode = scoreBoardDoublePojo.getLevelCode();
        int week = scoreBoardDoublePojo.getWeek();
        int season = scoreBoardDoublePojo.getSeason();

        String currGrade = eventLevelService.getNextRound(round, levelCode);

        String maxGrade = currGrade;
        int qualified = 0;
        GradeRecordPojo gradeRecordPojoOfWinner = gradeRecordService.selectByPlayerId(winnerId, eventId);
        if (gradeRecordPojoOfWinner != null) {
            maxGrade = EventRound.max(currGrade, gradeRecordPojoOfWinner.getGrade());
            qualified = gradeRecordPojoOfWinner.getQualified();
            if (gradeRecordPojoOfWinner.getGrade().equals(eventLevelService.getInitRound(EventRound.F.getRound(), levelCode)) && EventRound.compare(EventRound.Q.getRound(), currGrade)) {
                maxGrade = EventRound.Q.getRound();
            }
        }
        if (currGrade.equals(EventRound.Q.getRound())) {
            qualified = 1;
        }
        int pts;
        if (EventRound.compare(maxGrade, eventLevelService.getNextRound(eventLevelService.getInitRound(EventRound.F.getRound(), levelCode), levelCode))) {
            Integer scoreOfQ = eventLevelService.getScore(EventRound.Q.getRound(), levelCode, season);
            if (scoreOfQ == null) {
                pts = eventLevelService.getScore(maxGrade, levelCode, season);
            } else {
                pts = eventLevelService.getScore(maxGrade, levelCode, season) + qualified * scoreOfQ;
            }
        } else {
            pts = eventLevelService.getScore(maxGrade, levelCode, season);
        }

        logger.info("maxGrade = {}", maxGrade);
        logger.info("qualified = {}", qualified);
        logger.info("pts = {}", pts);

        if (gradeRecordPojoOfWinner == null) {
            gradeRecordPojoOfWinner = new GradeRecordPojo();
            gradeRecordPojoOfWinner.setPlayerId(winnerId);
            gradeRecordPojoOfWinner.setPlayerName(winnerName);
            gradeRecordPojoOfWinner.setEventId(scoreBoardDoublePojo.getEventId());
            gradeRecordPojoOfWinner.setEventName(scoreBoardDoublePojo.getEventName());
            gradeRecordPojoOfWinner.setLevelCode(levelCode);
            gradeRecordPojoOfWinner.setEventLevel(scoreBoardDoublePojo.getEventLevel());
            gradeRecordPojoOfWinner.setGrade(maxGrade);
            gradeRecordPojoOfWinner.setQualified(qualified);
            gradeRecordPojoOfWinner.setPts(pts);
            gradeRecordPojoOfWinner.setWeek(week);
            gradeRecordPojoOfWinner.setSeason(season);

            System.out.println(gradeRecordPojoOfWinner);
            gradeRecordService.insert(gradeRecordPojoOfWinner);
        } else {
            gradeRecordPojoOfWinner.setGrade(maxGrade);
            gradeRecordPojoOfWinner.setQualified(qualified);
            gradeRecordPojoOfWinner.setPts(pts);

            gradeRecordService.updateGrade(gradeRecordPojoOfWinner);
        }
    }

    public void loserGradeUpdater(int loserId, String loserName, ScoreBoardDoublePojo scoreBoardDoublePojo) {

        System.out.println(scoreBoardDoublePojo);

        int eventId = scoreBoardDoublePojo.getEventId();
        String round = scoreBoardDoublePojo.getRound();
        String levelCode = scoreBoardDoublePojo.getLevelCode();
        int week = scoreBoardDoublePojo.getWeek();
        int season = scoreBoardDoublePojo.getSeason();

        System.out.println("loser: " + eventId + ", " + round + ", " + levelCode + ", " + week + ", " + season);

        String grade = eventLevelService.getInitRound(round, levelCode);

        int pts = eventLevelService.getScore(grade, levelCode, season);

        System.out.println("grade = " + grade + "pts = " + pts);

        GradeRecordPojo gradeRecordPojoOfLoser = gradeRecordService.selectByPlayerId(loserId, eventId);
        if (gradeRecordPojoOfLoser == null) {
            gradeRecordPojoOfLoser = new GradeRecordPojo();
            gradeRecordPojoOfLoser.setPlayerId(loserId);
            gradeRecordPojoOfLoser.setPlayerName(loserName);
            gradeRecordPojoOfLoser.setEventId(scoreBoardDoublePojo.getEventId());
            gradeRecordPojoOfLoser.setEventName(scoreBoardDoublePojo.getEventName());
            gradeRecordPojoOfLoser.setLevelCode(levelCode);
            gradeRecordPojoOfLoser.setEventLevel(scoreBoardDoublePojo.getEventLevel());
            gradeRecordPojoOfLoser.setGrade(grade);
            gradeRecordPojoOfLoser.setPts(pts);
            gradeRecordPojoOfLoser.setWeek(week);
            gradeRecordPojoOfLoser.setSeason(season);

            System.out.println(gradeRecordPojoOfLoser);
            gradeRecordService.insert(gradeRecordPojoOfLoser);
        } else {
            if (gradeRecordPojoOfLoser.getGrade().equals(eventLevelService.getInitRound(EventRound.F.getRound(), levelCode))) {
                String minGrade = eventLevelService.getInitRound(round, levelCode);
                int minPts = eventLevelService.getScore(minGrade, levelCode, season);
                gradeRecordPojoOfLoser.setGrade(minGrade);
                gradeRecordPojoOfLoser.setPts(minPts);

                gradeRecordService.updateGrade(gradeRecordPojoOfLoser);
            } else if (round.equals(EventRound.QFi.getRound())) {
                String maxGrade = EventRound.max(round, gradeRecordPojoOfLoser.getGrade());
                int maxPts = eventLevelService.getScore(maxGrade, levelCode, season);

                gradeRecordPojoOfLoser.setGrade(maxGrade);
                gradeRecordPojoOfLoser.setPts(maxPts);

                gradeRecordService.updateGrade(gradeRecordPojoOfLoser);
            }
        }
    }

    /**
     * 基于score_board表更新或初始化pts_record的记录
     * @param scoreBoardPojo
     * @return
     */
    public void updatePtsRecordFromScoreBoard(ScoreBoardPojo scoreBoardPojo) {
        int player1Id = scoreBoardPojo.getPlayer1Id();
        int player2Id = scoreBoardPojo.getPlayer2Id();
        int week = scoreBoardPojo.getWeek();
        int season = scoreBoardPojo.getSeason();

        ptsCalculator(player1Id, week, season);
        ptsCalculator(player2Id, week, season);
        championPtsCalculator(player1Id, week, season);
        championPtsCalculator(player2Id, week, season);
    }

    public void updatePtsRecordFromScoreBoardDouble(ScoreBoardDoublePojo scoreBoardDoublePojo) {
        int player1IdA = scoreBoardDoublePojo.getPlayer1IdA();
        int player1IdB = scoreBoardDoublePojo.getPlayer1IdB();
        int player2IdA = scoreBoardDoublePojo.getPlayer2IdA();
        int player2IdB = scoreBoardDoublePojo.getPlayer2IdB();
        int week = scoreBoardDoublePojo.getWeek();
        int season = scoreBoardDoublePojo.getSeason();

        ptsCalculator(player1IdA, week, season);
        ptsCalculator(player1IdB, week, season);
        ptsCalculator(player2IdA, week, season);
        ptsCalculator(player2IdB, week, season);
        championPtsCalculator(player1IdA, week, season);
        championPtsCalculator(player1IdB, week, season);
        championPtsCalculator(player2IdA, week, season);
        championPtsCalculator(player2IdB, week, season);
    }

//    public void ptsCalculatorOfSeason1(int playerId, int week, int season) {
//
//        // 积分取过去一周期累加
//        List<GradeRecordSeason0Pojo> gradeRecordSeason0PojoList = gradeRecordSeason0Service.selectGradeRecordSeason0ListOfWholePeriod(playerId, week);
//        List<GradeRecordPojo> gradeRecordPojoList = gradeRecordService.selectGradeRecordListOfWholePeriod(playerId, week, season);
//
//        int total_pts = 0;
//        for (GradeRecordSeason0Pojo gradeRecordSeason0Pojo : gradeRecordSeason0PojoList) {
////            System.out.println(gradeRecordSeason0Pojo.toString());
//            total_pts += gradeRecordSeason0Pojo.getPts();
//        }
//        for (GradeRecordPojo gradeRecordPojo : gradeRecordPojoList) {
////            System.out.println(gradeRecordPojo.toString());
//            total_pts += gradeRecordPojo.getPts();
//        }
//
//        PtsRecordPojo ptsRecordPojo = ptsRecordService.selectByPlayerIdAndPhase(playerId, week, season);
//        if (ptsRecordPojo == null) {
//            if (total_pts > 0) {
//                ptsRecordPojo = new PtsRecordPojo();
//                ptsRecordPojo.setPlayerId(playerId);
//                ptsRecordPojo.setTotalPts(total_pts);
//                ptsRecordPojo.setWeek(week);
//                ptsRecordPojo.setSeason(season);
//
//                ptsRecordService.insert(ptsRecordPojo);
//            }
//        } else {
//            ptsRecordPojo.setTotalPts(total_pts);
//
//            ptsRecordService.updateTotalPts(ptsRecordPojo);
//        }
//    }

    public void championPtsCalculatorOfSeason1(int playerId, int week, int season) {

        // 积分取本赛季top8累加
        List<GradeRecordPojo> gradeRecordPojoList = gradeRecordService.selectGradeRecordListOfCurrentSeason(playerId, week, season);

        int total_pts = 0;
        List<Integer> ptsList = new ArrayList<>();
        for (GradeRecordPojo gradeRecordPojo : gradeRecordPojoList) {
//            System.out.println(gradeRecordPojo.toString());
            ptsList.add(gradeRecordPojo.getPts());
        }

        if (ptsList.size() <= NumConstants.TOP_PTS_NUM) {
            total_pts = CommonUtil.getSum(ptsList);
        } else {
            Collections.sort(ptsList, new Comparator<Integer>() {
                public int compare(Integer v1, Integer v2) {
                    return v2.compareTo(v1);
                }
            });
            total_pts = CommonUtil.getSum(ptsList.subList(0, NumConstants.TOP_PTS_NUM));
        }

//        System.out.println(total_pts);
//        System.out.println(ptsList);

        PtsRecordChampionPojo ptsRecordChampionPojo = ptsRecordChampionService.selectByPlayerId(playerId, week, season);
        if (ptsRecordChampionPojo == null) {
            if (total_pts > 0) {
                ptsRecordChampionPojo = new PtsRecordChampionPojo();
                ptsRecordChampionPojo.setPlayerId(playerId);
                ptsRecordChampionPojo.setTotalPts(total_pts);
                ptsRecordChampionPojo.setWeek(week);
                ptsRecordChampionPojo.setSeason(season);

                ptsRecordChampionService.insert(ptsRecordChampionPojo);
            }
        } else {
            ptsRecordChampionPojo.setTotalPts(total_pts);

            ptsRecordChampionService.updateTotalPts(ptsRecordChampionPojo);
        }
    }

    public void ptsCalculator(int playerId, int week, int season) {

        // 积分取过去一周期按T1+T1.5+T2+(YEC+T3(TOP5)/T3(TOP6))累加
        List<GradeRecordPojo> gradeRecordPojoList = gradeRecordService.selectGradeRecordListOfWholePeriod(playerId, week, season);

        int total_pts = 0;
        List<Integer> mandatoryPtsList = new ArrayList<>();
        List<Integer> yecPtsList = new ArrayList<>();
        List<Integer> otherPtsList = new ArrayList<>();
        for (GradeRecordPojo gradeRecordPojo : gradeRecordPojoList) {
            String eventLevel = gradeRecordPojo.getEventLevel();
            if (EventLevelConstants.MANDATORY_EVENT.contains(eventLevel)) {
                mandatoryPtsList.add(gradeRecordPojo.getPts());
            } else if (eventLevel.equals(EventLevelConstants.YEC)) {
                yecPtsList.add(gradeRecordPojo.getPts());
            } else {
                otherPtsList.add(gradeRecordPojo.getPts());
            }
        }

        if (yecPtsList.size() == 0) {
            total_pts += CommonUtil.getSum(mandatoryPtsList);
            if (otherPtsList.size() <= NumConstants.TOP_PTS_NUM) {
                total_pts += CommonUtil.getSum(otherPtsList);
            } else {
                Collections.sort(otherPtsList, new Comparator<Integer>() {
                    public int compare(Integer v1, Integer v2) {
                        return v2.compareTo(v1);
                    }
                });
                total_pts += CommonUtil.getSum(otherPtsList.subList(0, NumConstants.TOP_PTS_NUM));
            }
        } else if (yecPtsList.size() == 1) {
            total_pts += CommonUtil.getSum(mandatoryPtsList);
            total_pts += yecPtsList.get(0);
            if (otherPtsList.size() <= NumConstants.TOP_PTS_NUM - 1) {
                total_pts += CommonUtil.getSum(otherPtsList);
            } else {
                Collections.sort(otherPtsList, new Comparator<Integer>() {
                    public int compare(Integer v1, Integer v2) {
                        return v2.compareTo(v1);
                    }
                });
                total_pts += CommonUtil.getSum(otherPtsList.subList(0, NumConstants.TOP_PTS_NUM - 1));
            }
        } else {
            logger.error("grade record error");
        }

        PtsRecordPojo ptsRecordPojo = ptsRecordService.selectByPlayerIdAndPhase(playerId, week, season);
        if (ptsRecordPojo == null) {
            if (total_pts > 0) {
                ptsRecordPojo = new PtsRecordPojo();
                ptsRecordPojo.setPlayerId(playerId);
                ptsRecordPojo.setTotalPts(total_pts);
                ptsRecordPojo.setWeek(week);
                ptsRecordPojo.setSeason(season);

                ptsRecordService.insert(ptsRecordPojo);
            }
        } else {
            ptsRecordPojo.setTotalPts(total_pts);
            ptsRecordService.updateTotalPts(ptsRecordPojo);
        }
    }

    public void championPtsCalculator(int playerId, int week, int season) {

        // 积分取本赛季按T1+T1.5+T2+(YEC+T3(TOP5)/T3(TOP6))累加
        List<GradeRecordPojo> gradeRecordPojoList = gradeRecordService.selectGradeRecordListOfCurrentSeason(playerId, week, season);

        int total_pts = 0;
        List<Integer> mandatoryPtsList = new ArrayList<>();
        List<Integer> yecPtsList = new ArrayList<>();
        List<Integer> otherPtsList = new ArrayList<>();
        for (GradeRecordPojo gradeRecordPojo : gradeRecordPojoList) {
            String eventLevel = gradeRecordPojo.getEventLevel();
            if (EventLevelConstants.MANDATORY_EVENT.contains(eventLevel)) {
                mandatoryPtsList.add(gradeRecordPojo.getPts());
            } else if (eventLevel.equals(EventLevelConstants.YEC)) {
                yecPtsList.add(gradeRecordPojo.getPts());
            } else {
                otherPtsList.add(gradeRecordPojo.getPts());
            }
        }

        if (yecPtsList.size() == 0) {
            total_pts += CommonUtil.getSum(mandatoryPtsList);
            if (otherPtsList.size() <= NumConstants.TOP_PTS_NUM) {
                total_pts += CommonUtil.getSum(otherPtsList);
            } else {
                Collections.sort(otherPtsList, new Comparator<Integer>() {
                    public int compare(Integer v1, Integer v2) {
                        return v2.compareTo(v1);
                    }
                });
                total_pts += CommonUtil.getSum(otherPtsList.subList(0, NumConstants.TOP_PTS_NUM));
            }
        } else if (yecPtsList.size() == 1) {
            total_pts += CommonUtil.getSum(mandatoryPtsList);
            total_pts += yecPtsList.get(0);
            if (otherPtsList.size() <= NumConstants.TOP_PTS_NUM - 1) {
                total_pts += CommonUtil.getSum(otherPtsList);
            } else {
                Collections.sort(otherPtsList, new Comparator<Integer>() {
                    public int compare(Integer v1, Integer v2) {
                        return v2.compareTo(v1);
                    }
                });
                total_pts += CommonUtil.getSum(otherPtsList.subList(0, NumConstants.TOP_PTS_NUM - 1));
            }
        } else {
            logger.error("grade record error");
        }

//        System.out.println(mandatoryPtsList);
//        System.out.println(yecPtsList);
//        System.out.println(otherPtsList);
//        System.out.println(total_pts);

        PtsRecordChampionPojo ptsRecordChampionPojo = ptsRecordChampionService.selectByPlayerIdAndPhase(playerId, week, season);
        if (ptsRecordChampionPojo == null) {
            if (total_pts > 0) {
                ptsRecordChampionPojo = new PtsRecordChampionPojo();
                ptsRecordChampionPojo.setPlayerId(playerId);
                ptsRecordChampionPojo.setTotalPts(total_pts);
                ptsRecordChampionPojo.setWeek(week);
                ptsRecordChampionPojo.setSeason(season);

                ptsRecordChampionService.insert(ptsRecordChampionPojo);
            }
        } else {
            ptsRecordChampionPojo.setTotalPts(total_pts);
            ptsRecordChampionService.updateTotalPts(ptsRecordChampionPojo);
        }
    }

//    public void ptsRecordOfSeason1Initializer(int week, int season) {
//
//        List<Integer> playerIdList = playerInfoService.selectPlayerIdList();
//
//        for (int playerId : playerIdList) {
//            ptsCalculatorOfSeason1(playerId, week, season);
//        }
//    }

    public void championPtsRecordOfSeason1Initializer(int week, int season) {

        List<Integer> playerIdList = playerInfoService.selectPlayerIdList();

        for (int playerId : playerIdList) {
            championPtsCalculatorOfSeason1(playerId, week, season);
        }
    }

    public void ptsRecordInitializer(int week, int season) {

        List<Integer> playerIdList = playerInfoService.selectPlayerIdList();

        for (int playerId : playerIdList) {
            ptsCalculator(playerId, week, season);
        }
    }

    public void championPtsRecordInitializer(int week, int season) {

        List<Integer> playerIdList = playerInfoService.selectPlayerIdList();

        for (int playerId : playerIdList) {
            championPtsCalculator(playerId, week, season);
        }
    }

    public void rankUpdater(int week, int season) {

        List<PtsRecordPojo> ptsRecordPojoList = ptsRecordService.selectByWeekAndSeason(week, season);
        RankSorter.sortRank(ptsRecordPojoList);

        for (PtsRecordPojo ptsRecordPojo : ptsRecordPojoList) {
            ptsRecordService.updateRank(ptsRecordPojo);
        }
    }

    public void championRankUpdater(int week, int season) {

        List<PtsRecordChampionPojo> ptsRecordChampionPojoList = ptsRecordChampionService.selectByWeekAndSeason(week, season);
        RankSorter.sortChampionRank(ptsRecordChampionPojoList);

        for (PtsRecordChampionPojo ptsRecordChampionPojo : ptsRecordChampionPojoList) {
            ptsRecordChampionService.updateRank(ptsRecordChampionPojo);
        }
    }

//    @Scheduled(cron = "0 0 0/1 * * ?")
//    public void rankScheduledUpdater() {
//        long startTime = System.currentTimeMillis();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        logger.info("the update time = {}", sdf.format(new Date()));
//
//        gradeRecordUpdater();
//
//        int currWeek = currentPhaseService.selectCurrentPhase().getCurrentWeek();
//        int currSeason = currentPhaseService.selectCurrentPhase().getCurrentSeason();
//
//        rankUpdater(currWeek - 1, currSeason);
//        rankUpdater(currWeek, currSeason);
//        championRankUpdater(currWeek - 1, currSeason);
//        championRankUpdater(currWeek, currSeason);
//        long endTime = System.currentTimeMillis();
//        logger.info("程序运行时间：" + (endTime - startTime)/1000 + "s");
//    }
}
