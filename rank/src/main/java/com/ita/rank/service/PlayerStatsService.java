package com.ita.rank.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ita.rank.common.constants.EventLevelConstants;
import com.ita.rank.common.constants.NumConstants;
import com.ita.rank.enums.EventType;
import com.ita.rank.pojo.GradeRecordPojo;
import com.ita.rank.pojo.PlayerStatsPojo;
import com.ita.rank.pojo.PtsRecordPojo;
import com.ita.rank.pojo.ScoreBoardPojo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Author: liuxingxin
 * @Date: 2020/4/22
 */
@Service
public class PlayerStatsService {

    @Autowired
    PlayerInfoService playerInfoService;

    @Autowired
    ScoreBoardService scoreBoardService;

    @Autowired
    ScoreBoardDoubleService scoreBoardDoubleService;

    @Autowired
    GradeRecordService gradeRecordService;

    @Autowired
    PtsRecordService ptsRecordService;

    @Autowired
    CurrentPhaseService currentPhaseService;

    @Autowired
    RankService rankService;

    @Autowired
    EventInfoService eventInfoService;

    private static final Logger logger = LoggerFactory.getLogger(PlayerStatsService.class);

    public PlayerStatsPojo getPlayerStats(String playerName) {

        int currWeek = currentPhaseService.selectCurrentPhase().getCurrentWeek();
        int currSeason = currentPhaseService.selectCurrentPhase().getCurrentSeason();

        PlayerStatsPojo playerStatsPojo = new PlayerStatsPojo();
        int playerId = playerInfoService.selectByPlayerName(playerName).getPlayerId();
        int currRank = ptsRecordService.selectRankOfLastWeek(playerId, currWeek + 1, currSeason);
        int highestRank = ptsRecordService.selectHighestRank(playerId, currWeek + 1, currSeason);

        int[] singleWinsAndLossesOfCurrSeason = rankService.getWinsAndLossesOfCurrSeason(playerId, currSeason, NumConstants.SINGLE_MATCH_MODE);
        int[] doubleWinsAndLossesOfCurrSeason = rankService.getWinsAndLossesOfCurrSeason(playerId, currSeason, NumConstants.DOUBLE_MATCH_MODE);
        int singleWinsOfCurrSeason = singleWinsAndLossesOfCurrSeason[0];
        int singleLossesOfCurrSeason = singleWinsAndLossesOfCurrSeason[1];
        int doubleWinsOfCurrSeason = doubleWinsAndLossesOfCurrSeason[0];
        int doubleLossesOfCurrSeason = doubleWinsAndLossesOfCurrSeason[1];

        JSONObject ptsComponentInEventLevel = getPtsComponentOfWholePeriod(playerId, currWeek, currSeason);
        JSONObject ptsComponentInEventDate = getPtsComponentOfWholePeriodInEventDate(playerId, currWeek, currSeason);
        JSONObject rankHistory = getRankHistory(playerId);
        JSONObject singleWinRateDist = getSingleWinRateDist(playerId);

        playerStatsPojo.setPlayerId(playerId);
        playerStatsPojo.setPlayerName(playerName);
        playerStatsPojo.setCurrRank(currRank);
        playerStatsPojo.setHighestRank(highestRank);
        playerStatsPojo.setSingleWinsOfCurrSeason(singleWinsOfCurrSeason);
        playerStatsPojo.setSingleLossesOfCurrSeason(singleLossesOfCurrSeason);
        playerStatsPojo.setDoubleWinsOfCurrSeason(doubleWinsOfCurrSeason);
        playerStatsPojo.setDoubleLossesOfCurrSeason(doubleLossesOfCurrSeason);
        playerStatsPojo.setPtsComponentInEventLevel(ptsComponentInEventLevel);
        playerStatsPojo.setPtsComponentInEventDate(ptsComponentInEventDate);
        playerStatsPojo.setRankHistory(rankHistory);
        playerStatsPojo.setSingleWinRateDist(singleWinRateDist);

        return playerStatsPojo;
    }

    public Map<String, List<JSONObject>> getComponentMapOfWholePeriod(int playerId, int currWeek, int currSeason) {

        Map<String, List<JSONObject>> ptsMap = new HashMap<>();
        List<GradeRecordPojo> gradeRecordPojoList = gradeRecordService.selectGradeRecordListOfWholePeriod(playerId, currWeek, currSeason);

        List<JSONObject> componentsOfT1 = new ArrayList<>();
        List<JSONObject> componentsOfT2 = new ArrayList<>();
        List<JSONObject> componentsOfT3 = new ArrayList<>();
        List<JSONObject> componentsOfYEC = new ArrayList<>();

        for (GradeRecordPojo gradeRecordPojo : gradeRecordPojoList) {
            int season = gradeRecordPojo.getSeason();

            String eventName = gradeRecordPojo.getEventName();
            String eventLevel = gradeRecordPojo.getEventLevel();
            int eventType = eventInfoService.selectByEventName(eventName, season).getEventType();
            String round = gradeRecordPojo.getGrade();
            int pts = gradeRecordPojo.getPts();
            String date = eventInfoService.selectByEventName(eventName, season).getDate();

            JSONObject json = new JSONObject();
            json.put("eventLevel", eventLevel);
            json.put("eventName", eventName);
            json.put("eventType", EventType.getModeByIndex(eventType));
            json.put("pts", pts);
            json.put("round", round);
            json.put("date", date);

            if (eventLevel.equals(EventLevelConstants.T1)) {
                componentsOfT1.add(json);
            } else if (eventLevel.equals(EventLevelConstants.T2) || eventLevel.equals(EventLevelConstants.T2_PLUS)) {
                componentsOfT2.add(json);
            } else if (eventLevel.equals(EventLevelConstants.T3)) {
                componentsOfT3.add(json);
            } else if (eventLevel.equals(EventLevelConstants.YEC)) {
                componentsOfYEC.add(json);
            } else {
                logger.error("the event level is invalid, gradeRecord = {}", gradeRecordPojo.toString());
            }
        }

        Collections.sort(componentsOfT1, new Comparator<JSONObject>() {
            @Override
            public int compare(JSONObject json1, JSONObject json2) {
                int pts1 = json1.getInteger("pts");
                int pts2 = json2.getInteger("pts");
                return pts2 - pts1;
            }
        });
        Collections.sort(componentsOfT2, new Comparator<JSONObject>() {
            @Override
            public int compare(JSONObject json1, JSONObject json2) {
                int pts1 = json1.getInteger("pts");
                int pts2 = json2.getInteger("pts");
                return pts2 - pts1;
            }
        });
        Collections.sort(componentsOfT3, new Comparator<JSONObject>() {
            @Override
            public int compare(JSONObject json1, JSONObject json2) {
                int pts1 = json1.getInteger("pts");
                int pts2 = json2.getInteger("pts");
                return pts2 - pts1;
            }
        });

        if (componentsOfYEC.size() == 1) {
            int ptsYEC = componentsOfYEC.get(0).getInteger("pts");
            if (componentsOfT3.size() > 5) {
                if (componentsOfT3.get(5).getInteger("pts") > ptsYEC) {
                    componentsOfT3 = componentsOfT3.subList(0, 6);
                    componentsOfYEC = new ArrayList<>();
                } else {
                    componentsOfT3 = componentsOfT3.subList(0, 5);
                }
            }
        } else if (componentsOfYEC.size() > 1) {
            logger.error("the num of YEC is wrong");
            return null;
        }

        if (componentsOfT1.size() > 0) {
            ptsMap.put(EventLevelConstants.T1, componentsOfT1);
        }
        if (componentsOfT2.size() > 0) {
            ptsMap.put(EventLevelConstants.T2, componentsOfT2);
        }
        if (componentsOfT3.size() > 0) {
            ptsMap.put(EventLevelConstants.T3, componentsOfT3);
        }
        if (componentsOfYEC.size() > 0) {
            ptsMap.put(EventLevelConstants.YEC, componentsOfYEC);
        }

        return ptsMap;
    }

    public JSONObject getPtsComponentOfWholePeriod(int playerId, int currWeek, int currSeason) {

        Map<String, List<JSONObject>> ptsMap = getComponentMapOfWholePeriod(playerId, currWeek, currSeason);
        JSONObject componentJson = new JSONObject(true);

        int totalPts = 0;
        List<String> eventLevelList = Arrays.asList("T1", "YEC", "T2", "T3");
        for (String eventLevel : eventLevelList) {
            if (ptsMap.containsKey(eventLevel)) {
                List<JSONObject> components = ptsMap.get(eventLevel);
                int pts = components.stream().mapToInt(json -> json.getInteger("pts")).sum();
                totalPts += pts;
                JSONArray componentsArr = JSONArray.parseArray(components.toString());

                JSONObject eventLevelJson = new JSONObject();
                eventLevelJson.put("totalPts", pts);
                eventLevelJson.put("eventLevel", EventLevelConstants.getChineseNameOfEventLevel(eventLevel));
                eventLevelJson.put("components", componentsArr);

                componentJson.put(eventLevel, eventLevelJson);
            }
        }
        componentJson.put("totalPts", totalPts);

        return componentJson;
    }

    public JSONObject getPtsComponentOfWholePeriodInEventDate(int playerId, int currWeek, int currSeason) {

        Map<String, List<JSONObject>> ptsMap = getComponentMapOfWholePeriod(playerId, currWeek, currSeason);
        JSONObject componentJson = new JSONObject(true);

        List<JSONObject> componentList = new ArrayList<>();
        for (String eventLevel : ptsMap.keySet()) {
            componentList.addAll(ptsMap.get(eventLevel));
        }

        Map<String, List<JSONObject>> ptsMapInDate = new HashMap<>();
        for (JSONObject json : componentList) {
            String[] dateSplits = json.getString("date").split("-");
            String dateWithoutDay = String.join(".", dateSplits[0], dateSplits[1]);
            if (ptsMapInDate.containsKey(dateWithoutDay)) {
                ptsMapInDate.get(dateWithoutDay).add(json);
            } else {
                List<JSONObject> list = new ArrayList<>();
                list.add(json);
                ptsMapInDate.put(dateWithoutDay, list);
            }
        }

        List keyList = new ArrayList(ptsMapInDate.keySet());
        Collections.sort(keyList);
        for(Iterator ite = keyList.iterator(); ite.hasNext();) {
            String dateWithoutDay = (String)ite.next();
            List<JSONObject> list = ptsMapInDate.get(dateWithoutDay);
            Collections.sort(list, new Comparator<JSONObject>() {
                @Override
                public int compare(JSONObject json1, JSONObject json2) {
                    String date1 = json1.getString("date");
                    String date2 = json2.getString("date");
                    return date1.compareTo(date2);
                }
            });
            int totalPts = list.stream().mapToInt(json -> json.getInteger("pts")).sum();
            JSONObject json = new JSONObject();
            json.put("totalPts", totalPts);
            json.put("components", JSONArray.parseArray(list.toString()));
            componentJson.put(dateWithoutDay, json);
        }

        return componentJson;
    }

    public JSONObject getRankHistory(int playerId) {
        List<PtsRecordPojo> ptsRecordPojos = ptsRecordService.selectByPlayerId(playerId);
        List<String> phaseList = new ArrayList<>();
        List<Integer> rankList = new ArrayList<>();
        for (PtsRecordPojo ptsRecordPojo : ptsRecordPojos) {
            int week = ptsRecordPojo.getWeek();
            int season = ptsRecordPojo.getSeason();
            int rank = ptsRecordPojo.getRank();

            phaseList.add("S" + season + "W" + week);
            rankList.add(rank);
        }

        JSONObject rankHistory = new JSONObject();
        rankHistory.put("phase", JSONArray.parseArray(JSON.toJSONString(phaseList)));
        rankHistory.put("rank", JSONArray.parseArray(rankList.toString()));

        return rankHistory;
    }

    public JSONObject getSingleWinRateDist(int playerId) {
        List<List<Integer>> winOrLossList = new ArrayList<>();

        List<ScoreBoardPojo> scoreBoardPojos1 = scoreBoardService.selectAsPlayer1WithoutWO(playerId);
        for (ScoreBoardPojo scoreBoardPojo : scoreBoardPojos1) {
            int ret = scoreBoardPojo.getRet();
            int opponentRank = scoreBoardPojo.getPlayer2Rank();
            List<Integer> record = new ArrayList<>();
            if (ret == 1) {
                // 0-win 1-loss
                record = Arrays.asList(opponentRank, 1);
            } else if (ret == 2) {
                record = Arrays.asList(opponentRank, 0);
            } else if (ret == 0) {
                if (scoreBoardPojo.getPlayer1Score() > scoreBoardPojo.getPlayer2Score()) {
                    record = Arrays.asList(opponentRank, 0);
                } else {
                    record = Arrays.asList(opponentRank, 1);
                }
            } else {
                logger.error("the ret is wrong");
                return null;
            }
            winOrLossList.add(record);
        }

        List<ScoreBoardPojo> scoreBoardPojos2 = scoreBoardService.selectAsPlayer2WithoutWO(playerId);
        for (ScoreBoardPojo scoreBoardPojo : scoreBoardPojos2) {
            int ret = scoreBoardPojo.getRet();
            int opponentRank = scoreBoardPojo.getPlayer1Rank();
            List<Integer> record;
            if (ret == 1) {
                // 0-win 1-loss
                record = Arrays.asList(opponentRank, 0);
            } else if (ret == 2) {
                record = Arrays.asList(opponentRank, 1);
            } else if (ret == 0) {
                if (scoreBoardPojo.getPlayer1Score() < scoreBoardPojo.getPlayer2Score()) {
                    record = Arrays.asList(opponentRank, 0);
                } else {
                    record = Arrays.asList(opponentRank, 1);
                }
            } else {
                logger.error("the ret is wrong");
                return null;
            }
            winOrLossList.add(record);
        }

        Collections.sort(winOrLossList, new Comparator<List<Integer>>() {
            @Override
            public int compare(List<Integer> record1, List<Integer> record2) {
                int rank1 = record1.get(0);
                int rank2 = record2.get(0);
                return rank1 - rank2;
            }
        });

        System.out.println(winOrLossList);

        List<Integer> opponentRankList = new ArrayList<>();
        List<Integer> winsList = new ArrayList<>();
        List<Integer> lossesList = new ArrayList<>();

        for (List<Integer> record : winOrLossList) {
            int opponentRank = record.get(0);
            int winOrLoss = record.get(1);
            if (opponentRankList.contains(opponentRank)) {
                int index = opponentRankList.indexOf(opponentRank);
                winsList.set(index, winsList.get(index) + (1 - winOrLoss));
                lossesList.set(index, lossesList.get(index) + winOrLoss);
            } else {
                opponentRankList.add(opponentRank);
                winsList.add(1 - winOrLoss);
                lossesList.add(winOrLoss);
            }
        }

        System.out.println(opponentRankList);
        System.out.println(winsList);
        System.out.println(lossesList);

        List<Integer> accumWinsList = new ArrayList<>();
        List<Integer> accumLossesList = new ArrayList<>();
        List<Double> winRateList = new ArrayList<>();

        for (int i = 0; i < winsList.size(); i ++) {
            int accumWins = winsList.subList(0, i + 1).stream().mapToInt(x -> x).sum();
            int accumLosses = lossesList.subList(0, i + 1).stream().mapToInt(x -> x).sum();
            accumWinsList.add(accumWins);
            accumLossesList.add(accumLosses);
            winRateList.add(accumWins * 1.0 / (accumWins + accumLosses));
        }

        System.out.println(accumWinsList);
        System.out.println(accumLossesList);
        System.out.println(winRateList);

        JSONObject winRateDist = new JSONObject();
        winRateDist.put("opponentRank", JSONArray.parseArray(opponentRankList.toString()));
        winRateDist.put("winRate", JSONArray.parseArray(winRateList.toString()));

        return winRateDist;
    }
}
