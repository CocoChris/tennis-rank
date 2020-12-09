package com.ita.rank.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ita.rank.dao.ScoreBoardDoubleMapper;
import com.ita.rank.enums.EventType;
import com.ita.rank.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: liuxingxin
 * @Date: 2019/3/8
 */

@Service
public class H2HService {
  
    @Autowired
    PlayerInfoService playerInfoService;

    @Autowired
    ScoreBoardService scoreBoardService;

    @Autowired
    ScoreBoardDoubleService scoreBoardDoubleService;

    @Autowired
    CurrentPhaseService currentPhaseService;

    @Autowired
    PtsRecordService ptsRecordService;

    @Autowired
    EventInfoService eventInfoService;
    
    public JSONObject querySingleH2HVsPlayer(int player1Id, int player2Id) {

        JSONObject scoreRecordOfRivals = new JSONObject();
        JSONArray scoreRecordArr = new JSONArray();
        int currWeek = currentPhaseService.selectCurrentPhase().getCurrentWeek();
        int currSeason = currentPhaseService.selectCurrentPhase().getCurrentSeason();
        String player1Name = playerInfoService.selectByPlayerId(player1Id).getPlayerName();
        String player2Name = playerInfoService.selectByPlayerId(player2Id).getPlayerName();
        int lastRankOfPlayer1 = ptsRecordService.selectRankOfLastWeek(player1Id, currWeek + 1, currSeason);
        int lastRankOfPlayer2 = ptsRecordService.selectRankOfLastWeek(player2Id, currWeek + 1, currSeason);
        scoreRecordOfRivals.put("player1Name", player1Name);
        scoreRecordOfRivals.put("player2Name", player2Name);
        scoreRecordOfRivals.put("player1Rank", lastRankOfPlayer1);
        scoreRecordOfRivals.put("player2Rank", lastRankOfPlayer2);

        List<ScoreBoardPojo> scoreBoardPojoList = scoreBoardService.selectOfRivals(player1Id, player2Id);
        int winsOfPlayer1 = 0;
        int winsOfPlayer2 = 0;
        for (ScoreBoardPojo scoreBoardPojo : scoreBoardPojoList) {
            int scoreOfPlayer1, scoreOfPlayer2;
            int rankOfPlayer1, rankOfPlayer2;
            String eventName = scoreBoardPojo.getEventName();
            int season = scoreBoardPojo.getSeason();
            EventInfoPojo eventInfo = eventInfoService.selectByEventName(eventName, season);
            int eventType = eventInfo.getEventType();
            String yearOfMatch = eventInfo.getDate().split("-")[0];
            int wo = scoreBoardPojo.getWo();
            int ret = scoreBoardPojo.getRet();
            H2HRecordPojo h2HRecordPojo = new H2HRecordPojo();
            if (scoreBoardPojo.getPlayer1Id() == player1Id) {
                scoreOfPlayer1 = scoreBoardPojo.getPlayer1Score();
                scoreOfPlayer2 = scoreBoardPojo.getPlayer2Score();
                rankOfPlayer1 = scoreBoardPojo.getPlayer1Rank();
                rankOfPlayer2 = scoreBoardPojo.getPlayer2Rank();
            } else {
                scoreOfPlayer1 = scoreBoardPojo.getPlayer2Score();
                scoreOfPlayer2 = scoreBoardPojo.getPlayer1Score();
                rankOfPlayer1 = scoreBoardPojo.getPlayer2Rank();
                rankOfPlayer2 = scoreBoardPojo.getPlayer1Rank();
                if (ret == 1) {
                    ret = 2;
                } else if (ret == 2) {
                    ret = 1;
                }
            }

            if (ret == 0) {
                if (scoreOfPlayer1 > scoreOfPlayer2) {
                    h2HRecordPojo.setWinnerName(player1Name);
                    h2HRecordPojo.setLoserName(player2Name);
                    h2HRecordPojo.setWinnerRank(rankOfPlayer1);
                    h2HRecordPojo.setLoserRank(rankOfPlayer2);
                    h2HRecordPojo.setSeason(scoreBoardPojo.getSeason());
                    h2HRecordPojo.setEventLevel(scoreBoardPojo.getEventLevel());
                    h2HRecordPojo.setEventMode(eventType);
                    h2HRecordPojo.setEventName(scoreBoardPojo.getEventName());
                    h2HRecordPojo.setRound(scoreBoardPojo.getRound());

                    if (wo == 0) {
                        winsOfPlayer1 += 1;
                        h2HRecordPojo.setScore(new StringBuilder().append(scoreOfPlayer1).append(" - ").append(scoreOfPlayer2).toString());
                        h2HRecordPojo.setResultFlag(1);
                    } else {
                        h2HRecordPojo.setScore("W/O");
                        h2HRecordPojo.setResultFlag(0);
                    }
                } else {
                    h2HRecordPojo.setWinnerName(player2Name);
                    h2HRecordPojo.setLoserName(player1Name);
                    h2HRecordPojo.setWinnerRank(rankOfPlayer2);
                    h2HRecordPojo.setLoserRank(rankOfPlayer1);
                    h2HRecordPojo.setSeason(scoreBoardPojo.getSeason());
                    h2HRecordPojo.setEventLevel(scoreBoardPojo.getEventLevel());
                    h2HRecordPojo.setEventMode(eventType);
                    h2HRecordPojo.setEventName(scoreBoardPojo.getEventName());
                    h2HRecordPojo.setRound(scoreBoardPojo.getRound());
                    if (wo == 0) {
                        winsOfPlayer2 += 1;
                        h2HRecordPojo.setScore(new StringBuilder().append(scoreOfPlayer2).append(" - ").append(scoreOfPlayer1).toString());
                        h2HRecordPojo.setResultFlag(2);
                    } else {
                        h2HRecordPojo.setScore("W/O");
                        h2HRecordPojo.setResultFlag(0);
                    }
                }
            } else if (ret == 1) {
                h2HRecordPojo.setWinnerName(player2Name);
                h2HRecordPojo.setLoserName(player1Name);
                h2HRecordPojo.setWinnerRank(rankOfPlayer2);
                h2HRecordPojo.setLoserRank(rankOfPlayer1);
                h2HRecordPojo.setSeason(scoreBoardPojo.getSeason());
                h2HRecordPojo.setEventLevel(scoreBoardPojo.getEventLevel());
                h2HRecordPojo.setEventMode(eventType);
                h2HRecordPojo.setEventName(scoreBoardPojo.getEventName());
                h2HRecordPojo.setRound(scoreBoardPojo.getRound());
                h2HRecordPojo.setScore(new StringBuilder().append(scoreOfPlayer2).append(" - ").append(scoreOfPlayer1).append(" ret.").toString());
                h2HRecordPojo.setResultFlag(2);
                winsOfPlayer2 += 1;
            } else if (ret == 2) {
                h2HRecordPojo.setWinnerName(player1Name);
                h2HRecordPojo.setLoserName(player2Name);
                h2HRecordPojo.setWinnerRank(rankOfPlayer1);
                h2HRecordPojo.setLoserRank(rankOfPlayer2);
                h2HRecordPojo.setSeason(scoreBoardPojo.getSeason());
                h2HRecordPojo.setEventLevel(scoreBoardPojo.getEventLevel());
                h2HRecordPojo.setEventMode(eventType);
                h2HRecordPojo.setEventName(scoreBoardPojo.getEventName());
                h2HRecordPojo.setRound(scoreBoardPojo.getRound());
                h2HRecordPojo.setScore(new StringBuilder().append(scoreOfPlayer1).append(" - ").append(scoreOfPlayer2).append(" ret.").toString());
                h2HRecordPojo.setResultFlag(1);
                winsOfPlayer1 += 1;
            }

            JSONObject h2hRecordJson = new JSONObject();
            h2hRecordJson.put("season", yearOfMatch);
            h2hRecordJson.put("eventLevel", h2HRecordPojo.getEventLevel());
            h2hRecordJson.put("matchMode", EventType.getModeByIndex(h2HRecordPojo.getEventMode()));
            h2hRecordJson.put("eventName", h2HRecordPojo.getEventName());
            h2hRecordJson.put("round", h2HRecordPojo.getRound());
            String result = new StringBuilder()
                    .append(h2HRecordPojo.getWinnerName())
                    .append("(")
                    .append(h2HRecordPojo.getWinnerRank())
                    .append(")")
                    .append(" d. ")
                    .append(h2HRecordPojo.getLoserName())
                    .append("(")
                    .append(h2HRecordPojo.getLoserRank())
                    .append(")")
                    .toString();
            h2hRecordJson.put("result", result);
            h2hRecordJson.put("score", h2HRecordPojo.getScore());
            h2hRecordJson.put("resultFlag", h2HRecordPojo.getResultFlag());

            scoreRecordArr.add(h2hRecordJson);
        }

        scoreRecordOfRivals.put("winsOfPlayer1", winsOfPlayer1);
        scoreRecordOfRivals.put("winsOfPlayer2", winsOfPlayer2);
        scoreRecordOfRivals.put("result", scoreRecordArr);

        return scoreRecordOfRivals;
    }

    public JSONObject queryDoubleH2HVsPlayer(int player1Id, int player2Id) {

        JSONObject scoreRecordOfRivals = new JSONObject();
        JSONArray scoreRecordArr = new JSONArray();
        int currWeek = currentPhaseService.selectCurrentPhase().getCurrentWeek();
        int currSeason = currentPhaseService.selectCurrentPhase().getCurrentSeason();
        String player1Name = playerInfoService.selectByPlayerId(player1Id).getPlayerName();
        String player2Name = playerInfoService.selectByPlayerId(player2Id).getPlayerName();
        int lastRankOfPlayer1 = ptsRecordService.selectRankOfLastWeek(player1Id, currWeek + 1, currSeason);
        int lastRankOfPlayer2 = ptsRecordService.selectRankOfLastWeek(player2Id, currWeek + 1, currSeason);
        scoreRecordOfRivals.put("player1Name", player1Name);
        scoreRecordOfRivals.put("player2Name", player2Name);
        scoreRecordOfRivals.put("player1Rank", lastRankOfPlayer1);
        scoreRecordOfRivals.put("player2Rank", lastRankOfPlayer2);

        List<ScoreBoardDoublePojo> scoreBoardDoublePojoList = scoreBoardDoubleService.selectOfRivals(player1Id, player2Id);
        int winsOfPlayer1 = 0;
        int winsOfPlayer2 = 0;
        for (ScoreBoardDoublePojo scoreBoardDoublePojo : scoreBoardDoublePojoList) {
            String nameAndRankOfPlayer1, nameAndRankOfPlayer2;
            int scoreOfPlayer1, scoreOfPlayer2;
            String eventName = scoreBoardDoublePojo.getEventName();
            int season = scoreBoardDoublePojo.getSeason();
            EventInfoPojo eventInfo = eventInfoService.selectByEventName(eventName, season);
            int eventType = eventInfo.getEventType();
            String yearOfMatch = eventInfo.getDate().split("-")[0];
            int wo = scoreBoardDoublePojo.getWo();
            int ret = scoreBoardDoublePojo.getRet();
            H2HRecordDoublePojo h2HRecordDoublePojo = new H2HRecordDoublePojo();
            if (scoreBoardDoublePojo.getPlayer1IdA() == player1Id || scoreBoardDoublePojo.getPlayer1IdB() == player1Id) {
                nameAndRankOfPlayer1 = scoreBoardDoublePojo.getPlayer1NameA() + "(" + scoreBoardDoublePojo.getPlayer1RankA() + ")" + "/" +
                    scoreBoardDoublePojo.getPlayer1NameB() + "(" + scoreBoardDoublePojo.getPlayer1RankB() + ")";
                nameAndRankOfPlayer2 = scoreBoardDoublePojo.getPlayer2NameA() + "(" + scoreBoardDoublePojo.getPlayer2RankA() + ")" + "/" +
                    scoreBoardDoublePojo.getPlayer2NameB() + "(" + scoreBoardDoublePojo.getPlayer2RankB() + ")";
                scoreOfPlayer1 = scoreBoardDoublePojo.getPlayer1Score();
                scoreOfPlayer2 = scoreBoardDoublePojo.getPlayer2Score();
            } else {
                nameAndRankOfPlayer1 = scoreBoardDoublePojo.getPlayer2NameA() + "(" + scoreBoardDoublePojo.getPlayer2RankA() + ")" + "/" +
                    scoreBoardDoublePojo.getPlayer2NameB() + "(" + scoreBoardDoublePojo.getPlayer2RankB() + ")";
                nameAndRankOfPlayer2 = scoreBoardDoublePojo.getPlayer1NameA() + "(" + scoreBoardDoublePojo.getPlayer1RankA() + ")" + "/" +
                    scoreBoardDoublePojo.getPlayer1NameB() + "(" + scoreBoardDoublePojo.getPlayer1RankB() + ")";
                scoreOfPlayer1 = scoreBoardDoublePojo.getPlayer2Score();
                scoreOfPlayer2 = scoreBoardDoublePojo.getPlayer1Score();
                if (ret == 1) {
                    ret = 2;
                } else if (ret == 2) {
                    ret = 1;
                }
            }

            if (ret == 0) {
                if (scoreOfPlayer1 > scoreOfPlayer2) {
                    h2HRecordDoublePojo.setWinnerNameAndRank(nameAndRankOfPlayer1);
                    h2HRecordDoublePojo.setLoserNameAndRank(nameAndRankOfPlayer2);
                    h2HRecordDoublePojo.setSeason(scoreBoardDoublePojo.getSeason());
                    h2HRecordDoublePojo.setEventLevel(scoreBoardDoublePojo.getEventLevel());
                    h2HRecordDoublePojo.setEventMode(eventType);
                    h2HRecordDoublePojo.setEventName(scoreBoardDoublePojo.getEventName());
                    h2HRecordDoublePojo.setRound(scoreBoardDoublePojo.getRound());

                    if (wo == 0) {
                        winsOfPlayer1 += 1;
                        h2HRecordDoublePojo.setScore(new StringBuilder().append(scoreOfPlayer1).append(" - ").append(scoreOfPlayer2).toString());
                        h2HRecordDoublePojo.setResultFlag(1);
                    } else {
                        h2HRecordDoublePojo.setScore("W/O");
                        h2HRecordDoublePojo.setResultFlag(0);
                    }
                } else {
                    h2HRecordDoublePojo.setWinnerNameAndRank(nameAndRankOfPlayer2);
                    h2HRecordDoublePojo.setLoserNameAndRank(nameAndRankOfPlayer1);
                    h2HRecordDoublePojo.setSeason(scoreBoardDoublePojo.getSeason());
                    h2HRecordDoublePojo.setEventLevel(scoreBoardDoublePojo.getEventLevel());
                    h2HRecordDoublePojo.setEventMode(eventType);
                    h2HRecordDoublePojo.setEventName(scoreBoardDoublePojo.getEventName());
                    h2HRecordDoublePojo.setRound(scoreBoardDoublePojo.getRound());
                    if (wo == 0) {
                        winsOfPlayer2 += 1;
                        h2HRecordDoublePojo.setScore(new StringBuilder().append(scoreOfPlayer2).append(" - ").append(scoreOfPlayer1).toString());
                        h2HRecordDoublePojo.setResultFlag(2);
                    } else {
                        h2HRecordDoublePojo.setScore("W/O");
                        h2HRecordDoublePojo.setResultFlag(0);
                    }
                }
            } else if (ret == 1) {
                h2HRecordDoublePojo.setWinnerNameAndRank(nameAndRankOfPlayer2);
                h2HRecordDoublePojo.setLoserNameAndRank(nameAndRankOfPlayer1);
                h2HRecordDoublePojo.setSeason(scoreBoardDoublePojo.getSeason());
                h2HRecordDoublePojo.setEventLevel(scoreBoardDoublePojo.getEventLevel());
                h2HRecordDoublePojo.setEventMode(eventType);
                h2HRecordDoublePojo.setEventName(scoreBoardDoublePojo.getEventName());
                h2HRecordDoublePojo.setRound(scoreBoardDoublePojo.getRound());
                h2HRecordDoublePojo.setScore(new StringBuilder().append(scoreOfPlayer2).append(" - ").append(scoreOfPlayer1).append(" ret.").toString());
                h2HRecordDoublePojo.setResultFlag(2);
                winsOfPlayer2 += 1;
            } else if (ret == 2) {
                h2HRecordDoublePojo.setWinnerNameAndRank(nameAndRankOfPlayer1);
                h2HRecordDoublePojo.setLoserNameAndRank(nameAndRankOfPlayer2);
                h2HRecordDoublePojo.setSeason(scoreBoardDoublePojo.getSeason());
                h2HRecordDoublePojo.setEventLevel(scoreBoardDoublePojo.getEventLevel());
                h2HRecordDoublePojo.setEventMode(eventType);
                h2HRecordDoublePojo.setEventName(scoreBoardDoublePojo.getEventName());
                h2HRecordDoublePojo.setRound(scoreBoardDoublePojo.getRound());
                h2HRecordDoublePojo.setScore(new StringBuilder().append(scoreOfPlayer1).append(" - ").append(scoreOfPlayer2).append(" ret.").toString());
                h2HRecordDoublePojo.setResultFlag(1);
                winsOfPlayer1 += 1;
            }

            JSONObject h2hRecordJson = new JSONObject();
            h2hRecordJson.put("season", yearOfMatch);
            h2hRecordJson.put("eventLevel", h2HRecordDoublePojo.getEventLevel());
            h2hRecordJson.put("matchMode", EventType.getModeByIndex(h2HRecordDoublePojo.getEventMode()));
            h2hRecordJson.put("eventName", h2HRecordDoublePojo.getEventName());
            h2hRecordJson.put("round", h2HRecordDoublePojo.getRound());
            String result = new StringBuilder()
                .append(h2HRecordDoublePojo.getWinnerNameAndRank())
                .append(" d. ")
                .append(h2HRecordDoublePojo.getLoserNameAndRank())
                .toString();
            h2hRecordJson.put("result", result);
            h2hRecordJson.put("score", h2HRecordDoublePojo.getScore());
            h2hRecordJson.put("resultFlag", h2HRecordDoublePojo.getResultFlag());

            scoreRecordArr.add(h2hRecordJson);
        }

        scoreRecordOfRivals.put("winsOfPlayer1", winsOfPlayer1);
        scoreRecordOfRivals.put("winsOfPlayer2", winsOfPlayer2);
        scoreRecordOfRivals.put("result", scoreRecordArr);

        return scoreRecordOfRivals;
    }

    public JSONObject queryH2HVsTopN(int playerId, int topN) {

        JSONObject scoreRecordVsTopN = new JSONObject();
        JSONArray scoreRecordArr = new JSONArray();
        int currWeek = currentPhaseService.selectCurrentPhase().getCurrentWeek();
        int currSeason = currentPhaseService.selectCurrentPhase().getCurrentSeason();
        String playerName = playerInfoService.selectByPlayerId(playerId).getPlayerName();
        int lastRankOfPlayer = ptsRecordService.selectRankOfLastWeek(playerId, currWeek, currSeason);
        scoreRecordVsTopN.put("playerName", playerName);
        scoreRecordVsTopN.put("playerRank", lastRankOfPlayer);

        List<ScoreBoardPojo> scoreBoardPojoList = scoreBoardService.selectVsTopNOfCareer(playerId, topN);
        int winsOfPlayer = 0;
        int winsOfTopN = 0;
        for (ScoreBoardPojo scoreBoardPojo : scoreBoardPojoList) {
            H2HRecordPojo h2HRecordPojo = new H2HRecordPojo();
            int rankOfPlayer, rankOfTopN;
            int scoreOfPlayer, scoreOfTopN;
            String topNName;
            int winOrLose = 2; // 0-win 1-lose
            int ret = scoreBoardPojo.getRet();
            String eventName = scoreBoardPojo.getEventName();
            EventInfoPojo eventInfo = eventInfoService.selectByEventName(eventName, currSeason);
            int eventType = eventInfo.getEventType();

            String yearOfMatch = eventInfo.getDate().split("-")[0];

            if (scoreBoardPojo.getPlayer1Id() == playerId) {
                rankOfPlayer = scoreBoardPojo.getPlayer1Rank();
                rankOfTopN = scoreBoardPojo.getPlayer2Rank();
                scoreOfPlayer = scoreBoardPojo.getPlayer1Score();
                scoreOfTopN = scoreBoardPojo.getPlayer2Score();
                topNName = scoreBoardPojo.getPlayer2Name();
                if (ret == 1) {
                    winOrLose = 1;
                    winsOfTopN += 1;
                } else if (ret == 2) {
                    winOrLose = 0;
                    winsOfPlayer += 1;
                }
            } else {
                rankOfPlayer = scoreBoardPojo.getPlayer2Rank();
                rankOfTopN = scoreBoardPojo.getPlayer1Rank();
                scoreOfPlayer = scoreBoardPojo.getPlayer2Score();
                scoreOfTopN = scoreBoardPojo.getPlayer1Score();
                topNName = scoreBoardPojo.getPlayer1Name();
                if (ret == 1) {
                    winOrLose = 0;
                    winsOfPlayer += 1;
                } else if (ret == 2) {
                    winOrLose = 1;
                    winsOfTopN += 1;
                }
            }

            int wo = scoreBoardPojo.getWo();

            if (ret == 0) {
                if (scoreOfPlayer > scoreOfTopN) {
                    h2HRecordPojo.setWinnerName(playerName);
                    h2HRecordPojo.setLoserName(topNName);
                    h2HRecordPojo.setWinnerRank(rankOfPlayer);
                    h2HRecordPojo.setLoserRank(rankOfTopN);
                    h2HRecordPojo.setSeason(scoreBoardPojo.getSeason());
                    h2HRecordPojo.setEventLevel(scoreBoardPojo.getEventLevel());
                    h2HRecordPojo.setEventMode(eventType);
                    h2HRecordPojo.setEventName(scoreBoardPojo.getEventName());
                    h2HRecordPojo.setRound(scoreBoardPojo.getRound());
                    if (wo == 0) {
                        winsOfPlayer += 1;
                        h2HRecordPojo.setScore(new StringBuilder().append(scoreOfPlayer).append(" - ").append(scoreOfTopN).toString());
                        h2HRecordPojo.setResultFlag(1);
                    } else {
                        h2HRecordPojo.setScore("W/O");
                        h2HRecordPojo.setResultFlag(0);
                    }
                } else {
                    h2HRecordPojo.setWinnerName(topNName);
                    h2HRecordPojo.setLoserName(playerName);
                    h2HRecordPojo.setWinnerRank(rankOfTopN);
                    h2HRecordPojo.setLoserRank(rankOfPlayer);
                    h2HRecordPojo.setSeason(scoreBoardPojo.getSeason());
                    h2HRecordPojo.setEventLevel(scoreBoardPojo.getEventLevel());
                    h2HRecordPojo.setEventMode(eventType);
                    h2HRecordPojo.setEventName(scoreBoardPojo.getEventName());
                    h2HRecordPojo.setRound(scoreBoardPojo.getRound());
                    if (wo == 0) {
                        winsOfTopN += 1;
                        h2HRecordPojo.setScore(new StringBuilder().append(scoreOfTopN).append(" - ").append(scoreOfPlayer).toString());
                        h2HRecordPojo.setResultFlag(2);
                    } else {
                        h2HRecordPojo.setScore("W/O");
                        h2HRecordPojo.setResultFlag(0);
                    }
                }
            } else if (ret == 1 || ret == 2) {
                if (ret == 1) {
                    h2HRecordPojo.setWinnerName(scoreBoardPojo.getPlayer2Name());
                    h2HRecordPojo.setLoserName(scoreBoardPojo.getPlayer1Name());
                    h2HRecordPojo.setWinnerRank(scoreBoardPojo.getPlayer2Rank());
                    h2HRecordPojo.setLoserRank(scoreBoardPojo.getPlayer1Rank());
                } else if (ret == 2) {
                    h2HRecordPojo.setWinnerName(scoreBoardPojo.getPlayer1Name());
                    h2HRecordPojo.setLoserName(scoreBoardPojo.getPlayer2Name());
                    h2HRecordPojo.setWinnerRank(scoreBoardPojo.getPlayer1Rank());
                    h2HRecordPojo.setLoserRank(scoreBoardPojo.getPlayer2Rank());
                }
                h2HRecordPojo.setSeason(scoreBoardPojo.getSeason());
                h2HRecordPojo.setEventLevel(scoreBoardPojo.getEventLevel());
                h2HRecordPojo.setEventMode(eventType);
                h2HRecordPojo.setEventName(scoreBoardPojo.getEventName());
                h2HRecordPojo.setRound(scoreBoardPojo.getRound());
                if (wo == 0) {
                    if (winOrLose == 0) {
                        h2HRecordPojo.setResultFlag(1);
                        h2HRecordPojo.setScore(new StringBuilder().append(scoreOfPlayer).append(" - ").append(scoreOfTopN).append(" ret.").toString());
                    } else if (winOrLose == 1) {
                        h2HRecordPojo.setResultFlag(2);
                        h2HRecordPojo.setScore(new StringBuilder().append(scoreOfTopN).append(" - ").append(scoreOfPlayer).append(" ret.").toString());
                    }
                } else {
                    h2HRecordPojo.setScore("W/O");
                    h2HRecordPojo.setResultFlag(0);
                }
            }

            JSONObject h2hRecordJson = new JSONObject();
            h2hRecordJson.put("season", yearOfMatch);
            h2hRecordJson.put("eventLevel", h2HRecordPojo.getEventLevel());
            h2hRecordJson.put("matchMode", EventType.getModeByIndex(h2HRecordPojo.getEventMode()));
            h2hRecordJson.put("eventName", h2HRecordPojo.getEventName());
            h2hRecordJson.put("round", h2HRecordPojo.getRound());
            String result = new StringBuilder()
                    .append(h2HRecordPojo.getWinnerName())
                    .append("(")
                    .append(h2HRecordPojo.getWinnerRank())
                    .append(")")
                    .append(" d. ")
                    .append(h2HRecordPojo.getLoserName())
                    .append("(")
                    .append(h2HRecordPojo.getLoserRank())
                    .append(")")
                    .toString();
            h2hRecordJson.put("result", result);
            h2hRecordJson.put("score", h2HRecordPojo.getScore());
            h2hRecordJson.put("resultFlag", h2HRecordPojo.getResultFlag());

            scoreRecordArr.add(h2hRecordJson);
        }

        scoreRecordVsTopN.put("winsOfPlayer", winsOfPlayer);
        scoreRecordVsTopN.put("winsOfTopN", winsOfTopN);
        scoreRecordVsTopN.put("result", scoreRecordArr);

        return scoreRecordVsTopN;
    }

    public JSONArray queryPlayerList() {

        JSONArray playerList = new JSONArray();
        List<PlayerInfoPojo> playerInfoPojoList = playerInfoService.selectAll();
        for (PlayerInfoPojo playerInfoPojo : playerInfoPojoList) {
            int playerId = playerInfoPojo.getPlayerId();
            String playerName = playerInfoPojo.getPlayerName();
            JSONObject playerJson = new JSONObject();
            playerJson.put("id", playerId);
            playerJson.put("name", playerName);
            playerList.add(playerJson);
        }

        return playerList;
    }
}
