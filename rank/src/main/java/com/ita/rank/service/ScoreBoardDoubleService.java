package com.ita.rank.service;

import com.ita.rank.dao.ScoreBoardDoubleMapper;
import com.ita.rank.dao.ScoreBoardMapper;
import com.ita.rank.enums.EventRound;
import com.ita.rank.pojo.EventInfoPojo;
import com.ita.rank.pojo.EventLevelPojo;
import com.ita.rank.pojo.ScoreBoardDoublePojo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: liuxingxin
 * @Date: 2019/2/12
 */

@Service
public class ScoreBoardDoubleService {

    @Autowired
    ScoreBoardDoubleMapper scoreBoardDoubleMapper;

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

    private final Logger logger = LoggerFactory.getLogger(ScoreBoardDoubleService.class);

    public int insert(ScoreBoardDoublePojo scoreBoardDoublePojo) {

        String eventName = scoreBoardDoublePojo.getEventName();
        int season = scoreBoardDoublePojo.getSeason();
        String player1NameA = scoreBoardDoublePojo.getPlayer1NameA();
        String player1NameB = scoreBoardDoublePojo.getPlayer1NameB();
        String player2NameA = scoreBoardDoublePojo.getPlayer2NameA();
        String player2NameB = scoreBoardDoublePojo.getPlayer2NameB();
        String round = scoreBoardDoublePojo.getRound();
        int matchMode = scoreBoardDoublePojo.getMatchMode();
        if (!EventRound.isRoundValid(round)) {
            logger.error("the round is wrong " + scoreBoardDoublePojo.toString());
            return 0;
        }
        if (scoreBoardDoublePojo.getPlayer1Score() == scoreBoardDoublePojo.getPlayer2Score() && scoreBoardDoublePojo.getRet() == 0) {
            logger.error("the scores can not be equal " + scoreBoardDoublePojo.toString());
            return 0;
        }

        try {
            EventInfoPojo eventInfoPojo = eventInfoService.selectByEventName(eventName, season);
            int eventId = eventInfoPojo.getEventId();
//            int week = eventInfoPojo.getWeek();
            int week;
            if (scoreBoardDoublePojo.getWeek() == 0) {
                week = eventInfoPojo.getWeek();
            } else {
                week = scoreBoardDoublePojo.getWeek();
            }
            String levelCode = eventInfoPojo.getLevelCode();

            if (eventLevelService.getScore(round, levelCode, season) == null) {
                logger.error("the round is wrong " + scoreBoardDoublePojo.toString());
                return 0;
            }

            String eventLevel;

            EventLevelPojo eventLevelPojo = eventLevelService.selectLevelByCode(levelCode);
            eventLevel = eventLevelPojo.getLevel();

            int player1IdA = playerInfoService.selectByPlayerName(player1NameA).getPlayerId();
            int player1IdB = playerInfoService.selectByPlayerName(player1NameB).getPlayerId();
            int player2IdA = playerInfoService.selectByPlayerName(player2NameA).getPlayerId();
            int player2IdB = playerInfoService.selectByPlayerName(player2NameB).getPlayerId();

            int player1RankA = ptsRecordService.selectRankOfLastWeek(player1IdA, week, season);
            int player1RankB = ptsRecordService.selectRankOfLastWeek(player1IdB, week, season);
            int player2RankA = ptsRecordService.selectRankOfLastWeek(player2IdA, week, season);
            int player2RankB = ptsRecordService.selectRankOfLastWeek(player2IdB, week, season);

            scoreBoardDoublePojo.setEventId(eventId);
            scoreBoardDoublePojo.setWeek(week);
            scoreBoardDoublePojo.setEventId(eventId);
            scoreBoardDoublePojo.setWeek(week);
            scoreBoardDoublePojo.setPlayer1IdA(player1IdA);
            scoreBoardDoublePojo.setPlayer1IdB(player1IdB);
            scoreBoardDoublePojo.setPlayer2IdA(player2IdA);
            scoreBoardDoublePojo.setPlayer2IdB(player2IdB);
            scoreBoardDoublePojo.setPlayer1RankA(player1RankA);
            scoreBoardDoublePojo.setPlayer1RankB(player1RankB);
            scoreBoardDoublePojo.setPlayer2RankA(player2RankA);
            scoreBoardDoublePojo.setPlayer2RankB(player2RankB);
            scoreBoardDoublePojo.setEventLevel(eventLevel);
            scoreBoardDoublePojo.setLevelCode(levelCode);
            scoreBoardDoublePojo.setHandle(0);
        } catch (Exception e) {
            logger.error("fail to insert " + scoreBoardDoublePojo.toString());
        }

        return scoreBoardDoubleMapper.insert(scoreBoardDoublePojo);
    }

    public int insertBatch(List<ScoreBoardDoublePojo> scoreBoardList) {
        for (ScoreBoardDoublePojo scoreBoardDoublePojo : scoreBoardList) {
            int season = scoreBoardDoublePojo.getSeason();
            int week = scoreBoardDoublePojo.getWeek();
            String player1NameA = scoreBoardDoublePojo.getPlayer1NameA();
            String player1NameB = scoreBoardDoublePojo.getPlayer1NameB();
            String player2NameA = scoreBoardDoublePojo.getPlayer2NameA();
            String player2NameB = scoreBoardDoublePojo.getPlayer2NameB();
            String round = scoreBoardDoublePojo.getRound();
            if (!EventRound.isRoundValid(round)) {
                logger.error("the round is wrong " + scoreBoardDoublePojo.toString());
                return 0;
            }

            if (scoreBoardDoublePojo.getPlayer1Score() == scoreBoardDoublePojo.getPlayer2Score() && scoreBoardDoublePojo.getRet() == 0) {
                logger.error("the scores can not be equal " + scoreBoardDoublePojo.toString());
                return 0;
            }

            try {
                EventInfoPojo eventInfoPojo = eventInfoService.selectBySeasonAndWeek(season, week);

                int eventId = eventInfoPojo.getEventId();
                if (scoreBoardDoublePojo.getWeek() == 0) {
                    week = eventInfoPojo.getWeek();
                } else {
                    week = scoreBoardDoublePojo.getWeek();
                }
                String eventName = eventInfoPojo.getEventName();
                String levelCode = eventInfoPojo.getLevelCode();

                if (eventLevelService.getScore(round, levelCode, season) == null) {
                    logger.error("the round is wrong " + scoreBoardDoublePojo.toString());
                    return 0;
                }

                String eventLevel;

                EventLevelPojo eventLevelPojo = eventLevelService.selectLevelByCode(levelCode);
                eventLevel = eventLevelPojo.getLevel();

                int player1IdA = playerInfoService.selectByPlayerName(player1NameA).getPlayerId();
                int player1IdB = playerInfoService.selectByPlayerName(player1NameB).getPlayerId();
                int player2IdA = playerInfoService.selectByPlayerName(player2NameA).getPlayerId();
                int player2IdB = playerInfoService.selectByPlayerName(player2NameB).getPlayerId();

                int player1RankA = ptsRecordService.selectRankOfLastWeek(player1IdA, week, season);
                int player1RankB = ptsRecordService.selectRankOfLastWeek(player1IdB, week, season);
                int player2RankA = ptsRecordService.selectRankOfLastWeek(player2IdA, week, season);
                int player2RankB = ptsRecordService.selectRankOfLastWeek(player2IdB, week, season);

                scoreBoardDoublePojo.setEventId(eventId);
                scoreBoardDoublePojo.setEventName(eventName);
                scoreBoardDoublePojo.setPlayer1IdA(player1IdA);
                scoreBoardDoublePojo.setPlayer1IdB(player1IdB);
                scoreBoardDoublePojo.setPlayer2IdA(player2IdA);
                scoreBoardDoublePojo.setPlayer2IdB(player2IdB);
                scoreBoardDoublePojo.setPlayer1RankA(player1RankA);
                scoreBoardDoublePojo.setPlayer1RankB(player1RankB);
                scoreBoardDoublePojo.setPlayer2RankA(player2RankA);
                scoreBoardDoublePojo.setPlayer2RankB(player2RankB);
                scoreBoardDoublePojo.setEventLevel(eventLevel);
                scoreBoardDoublePojo.setLevelCode(levelCode);
                scoreBoardDoublePojo.setHandle(0);
            } catch (Exception e) {
                logger.error("fail to insert " + scoreBoardDoublePojo.toString());
            }
        }
//        System.out.println(scoreBoardList);
        return scoreBoardDoubleMapper.insertBatch(scoreBoardList);
    }

//    public int insertAFScore(ScoreBoardDoublePojo scoreBoardDoublePojo) {
//
//        String eventName = scoreBoardDoublePojo.getEventName();
//        int season = scoreBoardDoublePojo.getSeason();
//        String player1Name = scoreBoardDoublePojo.getPlayer1Name();
//        String player2Name = scoreBoardDoublePojo.getPlayer2Name();
//        String round = scoreBoardDoublePojo.getRound();
//        if (scoreBoardDoublePojo.getPlayer1Score() == scoreBoardDoublePojo.getPlayer2Score()) {
//            logger.error("the scores can not be equal " + scoreBoardDoublePojo.toString());
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
//            scoreBoardDoublePojo.setEventId(eventId);
//            scoreBoardDoublePojo.setWeek(week);
//            scoreBoardDoublePojo.setPlayer1Id(player1Id);
//            scoreBoardDoublePojo.setPlayer2Id(player2Id);
//            scoreBoardDoublePojo.setPlayer1Rank(player1Rank);
//            scoreBoardDoublePojo.setPlayer2Rank(player2Rank);
//            scoreBoardDoublePojo.setEventLevel(eventLevel);
//            scoreBoardDoublePojo.setLevelCode(levelCode);
//            scoreBoardDoublePojo.setMatchMode(matchMode);
//            scoreBoardDoublePojo.setHandle(1);
//        } catch (Exception e) {
//            logger.error("fail to insert " + scoreBoardDoublePojo.toString());
//        }
//
//        return scoreBoardDoubleMapper.insert(scoreBoardDoublePojo);
//    }

//    public int insertFCScore(ScoreBoardDoublePojo scoreBoardDoublePojo) {
//
//        String eventName = scoreBoardDoublePojo.getEventName();
//        int season = scoreBoardDoublePojo.getSeason();
//        String player1Name = scoreBoardDoublePojo.getPlayer1Name();
//        String player2Name = scoreBoardDoublePojo.getPlayer2Name();
//        int week = scoreBoardDoublePojo.getWeek();
//        if (scoreBoardDoublePojo.getPlayer1Score() == scoreBoardDoublePojo.getPlayer2Score()) {
//            logger.error("the scores can not be equal " + scoreBoardDoublePojo.toString());
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
//            scoreBoardDoublePojo.setEventId(eventId);
//            scoreBoardDoublePojo.setPlayer1Id(player1Id);
//            scoreBoardDoublePojo.setPlayer2Id(player2Id);
//            scoreBoardDoublePojo.setPlayer1Rank(player1Rank);
//            scoreBoardDoublePojo.setPlayer2Rank(player2Rank);
//            scoreBoardDoublePojo.setHandle(1);
//        } catch (Exception e) {
//            logger.error("fail to insert " + scoreBoardDoublePojo.toString());
//        }
//
//        return scoreBoardDoubleMapper.insert(scoreBoardDoublePojo);
//    }

    public List<ScoreBoardDoublePojo> selectByPlayerId(int playerId) {

        return scoreBoardDoubleMapper.selectByPlayerId(playerId);
    }

    public List<ScoreBoardDoublePojo> selectByPlayerIdAndPhase(int playerId, int week, int season) {

        return scoreBoardDoubleMapper.selectByPlayerIdAndPhase(playerId, week, season);
    }

    public List<ScoreBoardDoublePojo> selectByPlayerIdWithoutWO(int playerId) {

        return scoreBoardDoubleMapper.selectByPlayerIdWithoutWO(playerId, unWO);
    }

    public List<ScoreBoardDoublePojo> selectByPlayerIdAndPhaseWithoutWO(int playerId, int week, int season) {

        return scoreBoardDoubleMapper.selectByPlayerIdAndPhaseWithoutWO(playerId, week, season, unWO);
    }

    public List<ScoreBoardDoublePojo> selectAsPlayer1WithoutWO(int playerId) {
        return scoreBoardDoubleMapper.selectAsPlayer1WithoutWO(playerId, unWO);
    }

    public List<ScoreBoardDoublePojo> selectAsPlayer2WithoutWO(int playerId) {
        return scoreBoardDoubleMapper.selectAsPlayer2WithoutWO(playerId, unWO);
    }

    public List<ScoreBoardDoublePojo> selectAsPlayer1BySeasonWithoutWO(int playerId, int season) {
        return scoreBoardDoubleMapper.selectAsPlayer1BySeasonWithoutWO(playerId, season, unWO);
    }

    public List<ScoreBoardDoublePojo> selectAsPlayer2BySeasonWithoutWO(int playerId, int season) {
        return scoreBoardDoubleMapper.selectAsPlayer2BySeasonWithoutWO(playerId, season, unWO);
    }

    public List<ScoreBoardDoublePojo> selectOfRivals(int player1Id, int player2Id) {
        return scoreBoardDoubleMapper.selectOfRivals(player1Id, player2Id);
    }

//    public List<ScoreBoardDoublePojo> selectVsTopNOfCareer(int playerId, int topN) {
//        return scoreBoardDoubleMapper.selectVsTopNOfCareer(playerId, topN);
//    }
//
//    public List<ScoreBoardDoublePojo> selectVsTopNOfCareerWithoutWO(int playerId, int topN) {
//        return scoreBoardDoubleMapper.selectVsTopNOfCareerWithoutWO(playerId, topN, unWO);
//    }

    public List<ScoreBoardDoublePojo> selectByPlayerIdAndEventId(int playerId, int eventId) {
        return scoreBoardDoubleMapper.selectByPlayerIdAndEventId(playerId, eventId);
    }

    public List<ScoreBoardDoublePojo> selectByPlayerIdAndEventIdWithoutWO(int playerId, int eventId) {
        return scoreBoardDoubleMapper.selectByPlayerIdAndEventIdWithoutWO(playerId, eventId, unWO);
    }

    public List<ScoreBoardDoublePojo> selectAllScoreRecord() {
        return scoreBoardDoubleMapper.selectAllScoreRecord();
    }

    public List<ScoreBoardDoublePojo> selectScoreRecordByPhase(int week, int season) {
        return scoreBoardDoubleMapper.selectScoreRecordByPhase(week, season);
    }

    public List<ScoreBoardDoublePojo> selectUnhandled() {
        return scoreBoardDoubleMapper.selectUnhandled(unhandled);
    }

    public void updateHandle(ScoreBoardDoublePojo scoreBoardDoublePojo) {
        scoreBoardDoubleMapper.updateHandle(scoreBoardDoublePojo, handled);
    }

    public void updateRank(ScoreBoardDoublePojo scoreBoardDoublePojo) {
        scoreBoardDoubleMapper.updateRank(scoreBoardDoublePojo);
    }

    public void updateRankOfAllScoreRecord() {
        List<ScoreBoardDoublePojo> scoreBoardDoublePojoList = selectAllScoreRecord();
        logger.info("size is : " + scoreBoardDoublePojoList.size());
        for (ScoreBoardDoublePojo scoreBoardDoublePojo : scoreBoardDoublePojoList) {
            int player1IdA = scoreBoardDoublePojo.getPlayer1IdA();
            int player1IdB = scoreBoardDoublePojo.getPlayer1IdB();
            int player2IdA = scoreBoardDoublePojo.getPlayer2IdA();
            int player2IdB = scoreBoardDoublePojo.getPlayer2IdB();

            int week = scoreBoardDoublePojo.getWeek();
            int season = scoreBoardDoublePojo.getSeason();

            int player1RankA = ptsRecordService.selectRankOfLastWeek(player1IdA, week, season);
            int player1RankB = ptsRecordService.selectRankOfLastWeek(player1IdB, week, season);
            int player2RankA = ptsRecordService.selectRankOfLastWeek(player2IdA, week, season);
            int player2RankB = ptsRecordService.selectRankOfLastWeek(player2IdB, week, season);
            logger.info("rank1={}", player1RankA + '/' + player1RankB);
            logger.info("rank2={}", player2RankA + '/' + player2RankB);

            scoreBoardDoublePojo.setPlayer1RankA(player1RankA);
            scoreBoardDoublePojo.setPlayer1RankB(player1RankB);
            scoreBoardDoublePojo.setPlayer2RankA(player2RankA);
            scoreBoardDoublePojo.setPlayer2RankB(player2RankB);

            updateRank(scoreBoardDoublePojo);
        }
    }

    public void updateRankOfScoreRecordByPhase(int week, int season) {
        List<ScoreBoardDoublePojo> scoreBoardDoublePojoList = selectScoreRecordByPhase(week, season);
        logger.info("size is : " + scoreBoardDoublePojoList.size());
        for (ScoreBoardDoublePojo scoreBoardDoublePojo : scoreBoardDoublePojoList) {
            int player1IdA = scoreBoardDoublePojo.getPlayer1IdA();
            int player1IdB = scoreBoardDoublePojo.getPlayer1IdB();
            int player2IdA = scoreBoardDoublePojo.getPlayer2IdA();
            int player2IdB = scoreBoardDoublePojo.getPlayer2IdB();

            int player1RankA = ptsRecordService.selectRankOfLastWeek(player1IdA, week, season);
            int player1RankB = ptsRecordService.selectRankOfLastWeek(player1IdB, week, season);
            int player2RankA = ptsRecordService.selectRankOfLastWeek(player2IdA, week, season);
            int player2RankB = ptsRecordService.selectRankOfLastWeek(player2IdB, week, season);
            logger.info("rank1={}", player1RankA + '/' + player1RankB);
            logger.info("rank2={}", player2RankA + '/' + player2RankB);

            scoreBoardDoublePojo.setPlayer1RankA(player1RankA);
            scoreBoardDoublePojo.setPlayer1RankB(player1RankB);
            scoreBoardDoublePojo.setPlayer2RankA(player2RankA);
            scoreBoardDoublePojo.setPlayer2RankB(player2RankB);

            updateRank(scoreBoardDoublePojo);
        }
    }
}
