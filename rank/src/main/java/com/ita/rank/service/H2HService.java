package com.ita.rank.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ita.rank.enums.MatchMode;
import com.ita.rank.pojo.H2HRecordPojo;
import com.ita.rank.pojo.PlayerInfoPojo;
import com.ita.rank.pojo.ScoreBoardPojo;
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
    CurrentPhaseService currentPhaseService;

    @Autowired
    PtsRecordService ptsRecordService;
    
    
    public JSONObject queryH2HVsPlayer(int player1Id, int player2Id) {

        JSONObject scoreRecordOfRivals = new JSONObject();
        JSONArray scoreRecordArr = new JSONArray();
        int currWeek = currentPhaseService.selectCurrentPhase().getCurrentWeek();
        int currSeason = currentPhaseService.selectCurrentPhase().getCurrentSeason();
        String player1Name = playerInfoService.selectByPlayerId(player1Id).getPlayerName();
        String player2Name = playerInfoService.selectByPlayerId(player2Id).getPlayerName();
        int lastRankOfPlayer1 = ptsRecordService.selectRankOfLastWeek(player1Id, currWeek, currSeason);
        int lastRankOfPlayer2 = ptsRecordService.selectRankOfLastWeek(player2Id, currWeek, currSeason);
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
            }

            if (scoreOfPlayer1 > scoreOfPlayer2) {
                h2HRecordPojo.setWinnerName(player1Name);
                h2HRecordPojo.setLoserName(player2Name);
                h2HRecordPojo.setWinnerRank(rankOfPlayer1);
                h2HRecordPojo.setLoserRank(rankOfPlayer2);
                h2HRecordPojo.setSeason(scoreBoardPojo.getSeason());
                h2HRecordPojo.setEventLevel(scoreBoardPojo.getEventLevel());
                h2HRecordPojo.setMatchMode(scoreBoardPojo.getMatchMode());
                h2HRecordPojo.setEventName(scoreBoardPojo.getEventName());
                h2HRecordPojo.setRound(scoreBoardPojo.getRound());

                if (scoreBoardPojo.getRet() == 0) {
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
                h2HRecordPojo.setMatchMode(scoreBoardPojo.getMatchMode());
                h2HRecordPojo.setEventName(scoreBoardPojo.getEventName());
                h2HRecordPojo.setRound(scoreBoardPojo.getRound());
                if (scoreBoardPojo.getRet() == 0) {
                    winsOfPlayer2 += 1;
                    h2HRecordPojo.setScore(new StringBuilder().append(scoreOfPlayer2).append(" - ").append(scoreOfPlayer1).toString());
                    h2HRecordPojo.setResultFlag(2);
                } else {
                    h2HRecordPojo.setScore("W/O");
                    h2HRecordPojo.setResultFlag(0);
                }
            }

            JSONObject h2hRecordJson = new JSONObject();
            h2hRecordJson.put("season", new StringBuilder().append("第").append(h2HRecordPojo.getSeason() + 1).append("季").toString());
            h2hRecordJson.put("eventLevel", h2HRecordPojo.getEventLevel());
            h2hRecordJson.put("matchMode", MatchMode.getModeByIndex(h2HRecordPojo.getMatchMode()));
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
            if (scoreBoardPojo.getPlayer1Id() == playerId) {
                rankOfPlayer = scoreBoardPojo.getPlayer1Rank();
                rankOfTopN = scoreBoardPojo.getPlayer2Rank();
                scoreOfPlayer = scoreBoardPojo.getPlayer1Score();
                scoreOfTopN = scoreBoardPojo.getPlayer2Score();
                topNName = scoreBoardPojo.getPlayer2Name();
            } else {
                rankOfPlayer = scoreBoardPojo.getPlayer2Rank();
                rankOfTopN = scoreBoardPojo.getPlayer1Rank();
                scoreOfPlayer = scoreBoardPojo.getPlayer2Score();
                scoreOfTopN = scoreBoardPojo.getPlayer1Score();
                topNName = scoreBoardPojo.getPlayer1Name();
            }

            if (scoreOfPlayer > scoreOfTopN) {
                h2HRecordPojo.setWinnerName(playerName);
                h2HRecordPojo.setLoserName(topNName);
                h2HRecordPojo.setWinnerRank(rankOfPlayer);
                h2HRecordPojo.setLoserRank(rankOfTopN);
                h2HRecordPojo.setSeason(scoreBoardPojo.getSeason());
                h2HRecordPojo.setEventLevel(scoreBoardPojo.getEventLevel());
                h2HRecordPojo.setMatchMode(scoreBoardPojo.getMatchMode());
                h2HRecordPojo.setEventName(scoreBoardPojo.getEventName());
                h2HRecordPojo.setRound(scoreBoardPojo.getRound());
                if (scoreBoardPojo.getRet() == 0) {
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
                h2HRecordPojo.setMatchMode(scoreBoardPojo.getMatchMode());
                h2HRecordPojo.setEventName(scoreBoardPojo.getEventName());
                h2HRecordPojo.setRound(scoreBoardPojo.getRound());
                if (scoreBoardPojo.getRet() == 0) {
                    winsOfTopN += 1;
                    h2HRecordPojo.setScore(new StringBuilder().append(scoreOfTopN).append(" - ").append(scoreOfPlayer).toString());
                    h2HRecordPojo.setResultFlag(2);
                } else {
                    h2HRecordPojo.setScore("W/O");
                    h2HRecordPojo.setResultFlag(0);
                }
            }

            JSONObject h2hRecordJson = new JSONObject();
            h2hRecordJson.put("season", new StringBuilder().append("第").append(h2HRecordPojo.getSeason() + 1).append("季").toString());
            h2hRecordJson.put("eventLevel", h2HRecordPojo.getEventLevel());
            h2hRecordJson.put("matchMode", MatchMode.getModeByIndex(h2HRecordPojo.getMatchMode()));
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
