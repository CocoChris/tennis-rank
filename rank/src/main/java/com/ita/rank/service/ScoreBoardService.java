package com.ita.rank.service;

import com.ita.rank.dao.ScoreBoardMapper;
import com.ita.rank.enums.EventRound;
import com.ita.rank.pojo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: liuxingxin
 * @Date: 2019/2/12
 */

@Service
public class ScoreBoardService {

    @Autowired
    ScoreBoardMapper scoreBoardMapper;

    @Autowired
    EventInfoService eventInfoService;

    @Autowired
    EventLevelService eventLevelService;

    @Autowired
    PlayerInfoService playerInfoService;

    @Autowired
    PtsRecordService ptsRecordService;

    private final int unhandled = 0;
    private final int handled = 1;

    private final int unWO = 0;

    private final Logger logger = LoggerFactory.getLogger(ScoreBoardService.class);

    public int insert(ScoreBoardPojo scoreBoardPojo) {

        String eventName = scoreBoardPojo.getEventName();
        int season = scoreBoardPojo.getSeason();
        String player1Name = scoreBoardPojo.getPlayer1Name();
        String player2Name = scoreBoardPojo.getPlayer2Name();
        String round = scoreBoardPojo.getRound();
        int matchMode = scoreBoardPojo.getMatchMode();
        if (!EventRound.isRoundValid(round)) {
            logger.error("the round is wrong " + scoreBoardPojo.toString());
            return 0;
        }
        if (scoreBoardPojo.getPlayer1Score() == scoreBoardPojo.getPlayer2Score()) {
            logger.error("the scores can not be equal " + scoreBoardPojo.toString());
            return 0;
        }

        try {
            EventInfoPojo eventInfoPojo = eventInfoService.selectByEventName(eventName, season);
            int eventId = eventInfoPojo.getEventId();
//            int week = eventInfoPojo.getWeek();
            int week;
            if (scoreBoardPojo.getWeek() == 0) {
                week = eventInfoPojo.getWeek();
            } else {
                week = scoreBoardPojo.getWeek();
            }
            String levelCode = eventInfoPojo.getLevelCode();

            if (eventLevelService.getScore(round, levelCode, season) == null) {
                logger.error("the round is wrong " + scoreBoardPojo.toString());
                return 0;
            }

            String eventLevel;

            EventLevelPojo eventLevelPojo = eventLevelService.selectLevelByCode(levelCode);
            eventLevel = eventLevelPojo.getLevel();

            int player1Id = playerInfoService.selectByPlayerName(player1Name).getPlayerId();
            int player2Id = playerInfoService.selectByPlayerName(player2Name).getPlayerId();

            int player1Rank = ptsRecordService.selectRankOfLastWeek(player1Id, week, season);
            int player2Rank = ptsRecordService.selectRankOfLastWeek(player2Id, week, season);

            scoreBoardPojo.setEventId(eventId);
            scoreBoardPojo.setWeek(week);
            scoreBoardPojo.setEventId(eventId);
            scoreBoardPojo.setWeek(week);
            scoreBoardPojo.setPlayer1Id(player1Id);
            scoreBoardPojo.setPlayer2Id(player2Id);
            scoreBoardPojo.setPlayer1Rank(player1Rank);
            scoreBoardPojo.setPlayer2Rank(player2Rank);
            scoreBoardPojo.setEventLevel(eventLevel);
            scoreBoardPojo.setLevelCode(levelCode);
            scoreBoardPojo.setHandle(0);
        } catch (Exception e) {
            logger.error("fail to insert " + scoreBoardPojo.toString());
        }

        return scoreBoardMapper.insert(scoreBoardPojo);
    }

    public int insertBatch(List<ScoreBoardPojo> scoreBoardList) {
        for (ScoreBoardPojo scoreBoardPojo : scoreBoardList) {
            int season = scoreBoardPojo.getSeason();
            int week = scoreBoardPojo.getWeek();
            String player1Name = scoreBoardPojo.getPlayer1Name();
            String player2Name = scoreBoardPojo.getPlayer2Name();
            String round = scoreBoardPojo.getRound();
            if (!EventRound.isRoundValid(round)) {
                logger.error("the round is wrong " + scoreBoardPojo.toString());
                return 0;
            }

            if (scoreBoardPojo.getPlayer1Score() == scoreBoardPojo.getPlayer2Score() && scoreBoardPojo.getWo() == 0) {
                logger.error("the scores can not be equal " + scoreBoardPojo.toString());
                return 0;
            }

            try {
                EventInfoPojo eventInfoPojo = eventInfoService.selectBySeasonAndWeek(season, week);

                int eventId = eventInfoPojo.getEventId();
                if (scoreBoardPojo.getWeek() == 0) {
                    week = eventInfoPojo.getWeek();
                } else {
                    week = scoreBoardPojo.getWeek();
                }
                String eventName = eventInfoPojo.getEventName();
                String levelCode = eventInfoPojo.getLevelCode();

                if (eventLevelService.getScore(round, levelCode, season) == null) {
                    logger.error("the round is wrong " + scoreBoardPojo.toString());
                    return 0;
                }

                String eventLevel;

                EventLevelPojo eventLevelPojo = eventLevelService.selectLevelByCode(levelCode);
                eventLevel = eventLevelPojo.getLevel();

                int player1Id = playerInfoService.selectByPlayerName(player1Name).getPlayerId();
                int player2Id = playerInfoService.selectByPlayerName(player2Name).getPlayerId();

                int player1Rank = ptsRecordService.selectRankOfLastWeek(player1Id, week, season);
                int player2Rank = ptsRecordService.selectRankOfLastWeek(player2Id, week, season);

                scoreBoardPojo.setEventId(eventId);
                scoreBoardPojo.setEventName(eventName);
                scoreBoardPojo.setPlayer1Id(player1Id);
                scoreBoardPojo.setPlayer2Id(player2Id);
                scoreBoardPojo.setPlayer1Rank(player1Rank);
                scoreBoardPojo.setPlayer2Rank(player2Rank);
                scoreBoardPojo.setEventLevel(eventLevel);
                scoreBoardPojo.setLevelCode(levelCode);
                scoreBoardPojo.setHandle(0);
            } catch (Exception e) {
                logger.error("fail to insert " + scoreBoardPojo.toString());
            }
        }
//        System.out.println(scoreBoardList);
        return scoreBoardMapper.insertBatch(scoreBoardList);
    }

//    public int insertAFScore(ScoreBoardPojo scoreBoardPojo) {
//
//        String eventName = scoreBoardPojo.getEventName();
//        int season = scoreBoardPojo.getSeason();
//        String player1Name = scoreBoardPojo.getPlayer1Name();
//        String player2Name = scoreBoardPojo.getPlayer2Name();
//        String round = scoreBoardPojo.getRound();
//        if (scoreBoardPojo.getPlayer1Score() == scoreBoardPojo.getPlayer2Score()) {
//            logger.error("the scores can not be equal " + scoreBoardPojo.toString());
//            return 0;
//        }
//
//        try {
//            EventInfoPojo eventInfoPojo = eventInfoService.selectByEventName(eventName, season);
//            int eventId = eventInfoPojo.getEventId();
//            int week = eventInfoPojo.getWeek();
//            String levelCode = eventInfoPojo.getLevelCode();
//            int matchMode = eventInfoPojo.getMatchMode();
//
//            EventLevelFinalsPojo eventLevelFinalsPojo = eventLevelService.selectFinalsLevelByCode(levelCode);
//            String eventLevel = eventLevelFinalsPojo.getLevel();
//
//            int player1Id = playerInfoService.selectByPlayerName(player1Name).getPlayerId();
//            int player2Id = playerInfoService.selectByPlayerName(player2Name).getPlayerId();
//
//            int player1Rank = ptsRecordService.selectRankOfLastWeek(player1Id, week, season);
//            int player2Rank = ptsRecordService.selectRankOfLastWeek(player2Id, week, season);
//
//            scoreBoardPojo.setEventId(eventId);
//            scoreBoardPojo.setWeek(week);
//            scoreBoardPojo.setPlayer1Id(player1Id);
//            scoreBoardPojo.setPlayer2Id(player2Id);
//            scoreBoardPojo.setPlayer1Rank(player1Rank);
//            scoreBoardPojo.setPlayer2Rank(player2Rank);
//            scoreBoardPojo.setEventLevel(eventLevel);
//            scoreBoardPojo.setLevelCode(levelCode);
//            scoreBoardPojo.setMatchMode(matchMode);
//            scoreBoardPojo.setHandle(1);
//        } catch (Exception e) {
//            logger.error("fail to insert " + scoreBoardPojo.toString());
//        }
//
//        return scoreBoardMapper.insert(scoreBoardPojo);
//    }

//    public int insertFCScore(ScoreBoardPojo scoreBoardPojo) {
//
//        String eventName = scoreBoardPojo.getEventName();
//        int season = scoreBoardPojo.getSeason();
//        String player1Name = scoreBoardPojo.getPlayer1Name();
//        String player2Name = scoreBoardPojo.getPlayer2Name();
//        int week = scoreBoardPojo.getWeek();
//        if (scoreBoardPojo.getPlayer1Score() == scoreBoardPojo.getPlayer2Score()) {
//            logger.error("the scores can not be equal " + scoreBoardPojo.toString());
//            return 0;
//        }
//
//        try {
//            EventInfoPojo eventInfoPojo = eventInfoService.selectByEventName(eventName, season);
//            int eventId = eventInfoPojo.getEventId();
//
//            int player1Id = playerInfoService.selectByPlayerName(player1Name).getPlayerId();
//            int player2Id = playerInfoService.selectByPlayerName(player2Name).getPlayerId();
//
//            int player1Rank = ptsRecordService.selectRankOfLastWeek(player1Id, week, season);
//            int player2Rank = ptsRecordService.selectRankOfLastWeek(player2Id, week, season);
//
//            scoreBoardPojo.setEventId(eventId);
//            scoreBoardPojo.setPlayer1Id(player1Id);
//            scoreBoardPojo.setPlayer2Id(player2Id);
//            scoreBoardPojo.setPlayer1Rank(player1Rank);
//            scoreBoardPojo.setPlayer2Rank(player2Rank);
//            scoreBoardPojo.setHandle(1);
//        } catch (Exception e) {
//            logger.error("fail to insert " + scoreBoardPojo.toString());
//        }
//
//        return scoreBoardMapper.insert(scoreBoardPojo);
//    }

    public List<ScoreBoardPojo> selectByPlayerId(int playerId) {

        return scoreBoardMapper.selectByPlayerId(playerId);
    }

    public List<ScoreBoardPojo> selectByPlayerIdAndPhase(int playerId, int week, int season) {

        return scoreBoardMapper.selectByPlayerIdAndPhase(playerId, week, season);
    }

    public List<ScoreBoardPojo> selectByPlayerIdWithoutWO(int playerId) {

        return scoreBoardMapper.selectByPlayerIdWithoutWO(playerId, unWO);
    }

    public List<ScoreBoardPojo> selectByPlayerIdAndPhaseWithoutWO(int playerId, int week, int season) {

        return scoreBoardMapper.selectByPlayerIdAndPhaseWithoutWO(playerId, week, season, unWO);
    }

    public List<ScoreBoardPojo> selectAsPlayer1WithoutWO(int playerId) {
        return scoreBoardMapper.selectAsPlayer1WithoutWO(playerId, unWO);
    }

    public List<ScoreBoardPojo> selectAsPlayer2WithoutWO(int playerId) {
        return scoreBoardMapper.selectAsPlayer2WithoutWO(playerId, unWO);
    }

    public List<ScoreBoardPojo> selectAsPlayer1BySeasonWithoutWO(int playerId, int season) {
        return scoreBoardMapper.selectAsPlayer1BySeasonWithoutWO(playerId, season, unWO);
    }

    public List<ScoreBoardPojo> selectAsPlayer2BySeasonWithoutWO(int playerId, int season) {
        return scoreBoardMapper.selectAsPlayer2BySeasonWithoutWO(playerId, season, unWO);
    }

    public List<ScoreBoardPojo> selectOfRivals(int player1Id, int player2Id) {
        return scoreBoardMapper.selectOfRivals(player1Id, player2Id);
    }

    public List<ScoreBoardPojo> selectVsTopNOfCareer(int playerId, int topN) {
        return scoreBoardMapper.selectVsTopNOfCareer(playerId, topN);
    }

    public List<ScoreBoardPojo> selectVsTopNOfCareerWithoutWO(int playerId, int topN) {
        return scoreBoardMapper.selectVsTopNOfCareerWithoutWO(playerId, topN, unWO);
    }

    public List<ScoreBoardPojo> selectByPlayerIdAndEventId(int playerId, int eventId) {
        return scoreBoardMapper.selectByPlayerIdAndEventId(playerId, eventId);
    }

    public List<ScoreBoardPojo> selectByPlayerIdAndEventIdWithoutWO(int playerId, int eventId) {
        return scoreBoardMapper.selectByPlayerIdAndEventIdWithoutWO(playerId, eventId, unWO);
    }

    public List<ScoreBoardPojo> selectAllScoreRecord() {
        return scoreBoardMapper.selectAllScoreRecord();
    }

    public List<ScoreBoardPojo> selectScoreRecordByPhase(int week, int season) {
        return scoreBoardMapper.selectScoreRecordByPhase(week, season);
    }

    public List<ScoreBoardPojo> selectUnhandled() {
        return scoreBoardMapper.selectUnhandled(unhandled);
    }

    public void updateHandle(ScoreBoardPojo scoreBoardPojo) {
        scoreBoardMapper.updateHandle(scoreBoardPojo, handled);
    }

    public void updateRank(ScoreBoardPojo scoreBoardPojo) {
        scoreBoardMapper.updateRank(scoreBoardPojo);
    }

    public void updateRankOfAllScoreRecord() {
        List<ScoreBoardPojo> scoreBoardPojoList = selectAllScoreRecord();
        logger.info("size is : " + scoreBoardPojoList.size());
        for (ScoreBoardPojo scoreBoardPojo : scoreBoardPojoList) {
            int player1Id = scoreBoardPojo.getPlayer1Id();
            int player2Id = scoreBoardPojo.getPlayer2Id();
            int week = scoreBoardPojo.getWeek();
            int season = scoreBoardPojo.getSeason();

            int player1Rank = ptsRecordService.selectRankOfLastWeek(player1Id, week, season);
            int player2Rank = ptsRecordService.selectRankOfLastWeek(player2Id, week, season);
            logger.info("rank1=" + player1Rank);
            logger.info("rank2=" + player2Rank);

            scoreBoardPojo.setPlayer1Rank(player1Rank);
            scoreBoardPojo.setPlayer2Rank(player2Rank);

            updateRank(scoreBoardPojo);
        }
    }

    public void updateRankOfScoreRecordByPhase(int week, int season) {
        List<ScoreBoardPojo> scoreBoardPojoList = selectScoreRecordByPhase(week, season);
        logger.info("size is : " + scoreBoardPojoList.size());
        for (ScoreBoardPojo scoreBoardPojo : scoreBoardPojoList) {
            int player1Id = scoreBoardPojo.getPlayer1Id();
            int player2Id = scoreBoardPojo.getPlayer2Id();

            int player1Rank = ptsRecordService.selectRankOfLastWeek(player1Id, week, season);
            int player2Rank = ptsRecordService.selectRankOfLastWeek(player2Id, week, season);
            logger.info("rank1=" + player1Rank);
            logger.info("rank2=" + player2Rank);

            scoreBoardPojo.setPlayer1Rank(player1Rank);
            scoreBoardPojo.setPlayer2Rank(player2Rank);

            updateRank(scoreBoardPojo);
        }
    }
}
