package com.ita.rank.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ita.rank.common.constants.EventLevelConstants;
import com.ita.rank.common.constants.NumConstants;
import com.ita.rank.enums.EventRound;
import com.ita.rank.pojo.*;
import com.ita.rank.processor.Classifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.lang.Math.min;

/**
 * @Author: liuxingxin
 * @Date: 2019/2/21
 */

@Service
public class RankService {

    @Autowired
    Classifier classifier;

    @Autowired
    CurrentPhaseService currentPhaseService;

    @Autowired
    PtsRecordService ptsRecordService;

    @Autowired
    PtsRecordChampionService ptsRecordChampionService;

    @Autowired
    GradeRecordService gradeRecordService;

    @Autowired
    ScoreBoardService scoreBoardService;

    @Autowired
    ScoreBoardDoubleService scoreBoardDoubleService;

    @Autowired
    EventInfoService eventInfoService;

    private final Logger logger = LoggerFactory.getLogger(RankService.class);

    private final DecimalFormat df = new DecimalFormat("0.00");

    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public JSONObject queryLiveRankInfo() {

        JSONObject liveRankInfoJson = new JSONObject();
        liveRankInfoJson.put("id", 1);
        liveRankInfoJson.put("jsonrpc", "2.0");
        JSONArray result = new JSONArray();

        int currWeek = currentPhaseService.selectCurrentPhase().getCurrentWeek();
        int currSeason = currentPhaseService.selectCurrentPhase().getCurrentSeason();

        List<PtsRecordPojo> ptsRecordList = ptsRecordService.selectByWeekAndSeason(currWeek, currSeason);

        for (int i = 0; i < ptsRecordList.size(); i ++) {
            PtsRecordPojo ptsRecord = ptsRecordList.get(i);
            LiveRankInfoPojo rankInfo = new LiveRankInfoPojo();

            int playerId = ptsRecord.getPlayerId();
            int liveRank = ptsRecord.getRank();
            int preRank = ptsRecordService.selectRankOfLastWeek(playerId, currWeek, currSeason);
            int rankChange = liveRank - preRank;
            int highestRank = ptsRecordService.selectHighestRank(playerId, currWeek, currSeason);
            int reachNewHighsOfRank = (liveRank < highestRank) ? NumConstants.REACH_NEW_HIGHS_OF_RANK : NumConstants.NOT_REACH_NEW_HIGHS_OF_RANK;

            String playerName = ptsRecord.getPlayerName();
            int totalPts = ptsRecord.getTotalPts();
            int minusPts = getMinusPts(playerId, currWeek, currSeason);
            int plusPts = getPlusPts(playerId, currWeek, currSeason);
            int minValidPts = getMinValidPtsOfWholePeriod(playerId, currWeek, currSeason);

            String currGrade = getCurrGrade(playerId, currWeek, currSeason);
            int stateOfCurrWeek = getStatusOfCurrWeek(playerId, currWeek, currSeason);
            int numOfEntriesOfWholePeriod = getNumOfEntriesOfWholePeriod(playerId, currWeek, currSeason);
            int numOfTitlesOfCareer = getNumOfTitlesOfCareer(playerId);

            int[] singleWinsAndLossesOfCurrSeason = getWinsAndLossesOfCurrSeason(playerId, currSeason, NumConstants.SINGLE_MATCH_MODE);
            int[] doubleWinsAndLossesOfCurrSeason = getWinsAndLossesOfCurrSeason(playerId, currSeason, NumConstants.DOUBLE_MATCH_MODE);
            int winsOfCurrSeason = singleWinsAndLossesOfCurrSeason[0] + doubleWinsAndLossesOfCurrSeason[0];
            int lossesOfCurrSeason = singleWinsAndLossesOfCurrSeason[1] + doubleWinsAndLossesOfCurrSeason[1];
            double winRateOfCurrSeason = Double.valueOf((winsOfCurrSeason + lossesOfCurrSeason == 0) ?
                    df.format(0) : df.format(winsOfCurrSeason * 1.0 / (winsOfCurrSeason + lossesOfCurrSeason)));
//            int[] winsAndLossesOfCareer = getWinsAndLossesOfCareer(playerId);
//            int winsOfCareer = winsAndLossesOfCareer[0];
//            int lossesOfCareer = winsAndLossesOfCareer[1];
//            double winRateOfCareer = Double.valueOf((winsOfCareer + lossesOfCareer == 0) ?
//                    df.format(0) : df.format(winsOfCareer * 1.0 / (winsOfCareer + lossesOfCareer)));
            int[] winsAndLossesVsTop10 = getWinsAndLossesOfCareerVsTop10(playerId);
            int winsOfCareerVsTop10 = winsAndLossesVsTop10[0];
            int lossesOfCareerVsTop10 = winsAndLossesVsTop10[1];
            double winRateOfCareerVsTop10 = Double.valueOf((winsOfCareerVsTop10 + lossesOfCareerVsTop10 == 0) ?
                    df.format(0) : df.format(winsOfCareerVsTop10 * 1.0 / (winsOfCareerVsTop10 + lossesOfCareerVsTop10)));

            rankInfo.setLiveRank(liveRank);
            rankInfo.setRankChange(rankChange);
            rankInfo.setHighestRank(highestRank);
            rankInfo.setReachNewHighsOfRank(reachNewHighsOfRank);
            rankInfo.setPlayerName(playerName);
            rankInfo.setTotalPts(totalPts);
            rankInfo.setMinusPts(minusPts);
            rankInfo.setPlusPts(plusPts);
            rankInfo.setMinValidPts(minValidPts);
            rankInfo.setCurrGrade(currGrade);
            rankInfo.setStateOfCurrWeek(stateOfCurrWeek);
            rankInfo.setNumOfEntriesOfWholePeriod(numOfEntriesOfWholePeriod);
//            rankInfo.setNumOfTitlesOfCareer(numOfTitlesOfCareer);
            rankInfo.setWinsOfCurrSeason(winsOfCurrSeason);
            rankInfo.setLossesOfCurrSeason(lossesOfCurrSeason);
            rankInfo.setWinRateOfCurrSeason(winRateOfCurrSeason);
//            rankInfo.setWinsOfCareer(winsOfCareer);
//            rankInfo.setLossesOfCareer(lossesOfCareer);
//            rankInfo.setWinRateOfCareer(winRateOfCareer);
            rankInfo.setWinsOfCareerVsTop10(winsOfCareerVsTop10);
            rankInfo.setLossesOfCareerVsTop10(lossesOfCareerVsTop10);
            rankInfo.setWinRateOfCareerVsTop10(winRateOfCareerVsTop10);

            JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(rankInfo));
            result.add(jsonObject);
        }

        String updateTime = sdf.format(new Date());
        liveRankInfoJson.put("updateTime", updateTime);
        liveRankInfoJson.put("result", result);

        return liveRankInfoJson;
    }

//    public JSONObject queryChampionRankInfo() {
//
//        JSONObject championRankInfoJson = new JSONObject();
//        championRankInfoJson.put("id", 1);
//        championRankInfoJson.put("jsonrpc", "2.0");
//        JSONArray result = new JSONArray();
//
//        int currWeek = currentPhaseService.selectCurrentPhase().getCurrentWeek();
//        int currSeason = currentPhaseService.selectCurrentPhase().getCurrentSeason();
//
//        List<PtsRecordChampionPojo> ptsRecordChampionPojoList = ptsRecordChampionService.selectByWeekAndSeason(currWeek, currSeason);
//
//        for (int i = 0; i < ptsRecordChampionPojoList.size(); i ++) {
//            PtsRecordChampionPojo ptsRecordChampion = ptsRecordChampionPojoList.get(i);
//            ChampionRankInfoPojo championRankInfo = new ChampionRankInfoPojo();
//
//            int playerId = ptsRecordChampion.getPlayerId();
//            int championRank = ptsRecordChampion.getRank();
//            int preRank = ptsRecordChampionService.selectRankOfLastWeek(playerId, currWeek, currSeason);
//            int rankChange = championRank - preRank;
//
//            String playerName = ptsRecordChampion.getPlayerName();
//            int totalPts = ptsRecordChampion.getTotalPts();
//            int plusPts = getPlusPts(playerId, currWeek, currSeason);
//
//            Map<String, List<Integer>> ptsMap = getPtsComponentOfCurrSeason(playerId, currWeek, currSeason);
//            List<Integer> ptsListOfNM = ptsMap.get(EventLevelConstants.NM);
//            int minValidPts = ptsListOfNM.get(NumConstants.TOP_PTS_NUM - 1);
//            String currGrade = getCurrGrade(playerId, currWeek, currSeason);
//            int stateOfCurrWeek = getStatusOfCurrWeek(playerId, currWeek, currSeason);
//
//            int ptsOfGS1 = ptsMap.get(EventLevelConstants.GS).get(0);
//            int ptsOfGS2 = ptsMap.get(EventLevelConstants.GS).get(1);
//            int ptsOfPM1 = ptsMap.get(EventLevelConstants.PM).get(0);
//            int ptsOfPM2 = ptsMap.get(EventLevelConstants.PM).get(1);
//            int ptsOfAF = ptsMap.get(EventLevelConstants.AF).get(0);
//            int ptsOfET = ptsMap.get(EventLevelConstants.AF).get(1);
//            int ptsOfNo1 = ptsListOfNM.get(0);
//            int ptsOfNo2 = ptsListOfNM.get(1);
//            int ptsOfNo3 = ptsListOfNM.get(2);
//            int ptsOfNo4 = ptsListOfNM.get(3);
//            int ptsOfNo5 = ptsListOfNM.get(4);
//            int ptsOfNo6 = ptsListOfNM.get(5);
//            int ptsOfNo7 = ptsListOfNM.get(6);
//            int ptsOfNo8 = ptsListOfNM.get(7);
//
//            championRankInfo.setRank(championRank);
//            championRankInfo.setRankChange(rankChange);
//            championRankInfo.setPlayerName(playerName);
//            championRankInfo.setTotalPts(totalPts);
//            championRankInfo.setPlusPts(plusPts);
//            championRankInfo.setMinValidPts(minValidPts);
//            championRankInfo.setCurrGrade(currGrade);
//            championRankInfo.setStateOfCurrWeek(stateOfCurrWeek);
//            championRankInfo.setPtsOfGS1(ptsOfGS1);
//            championRankInfo.setPtsOfGS2(ptsOfGS2);
//            championRankInfo.setPtsOfPM1(ptsOfPM1);
//            championRankInfo.setPtsOfPM2(ptsOfPM2);
//            championRankInfo.setPtsOfAF(ptsOfAF);
//            championRankInfo.setPtsOfET(ptsOfET);
//            championRankInfo.setPtsOfNo1(ptsOfNo1);
//            championRankInfo.setPtsOfNo2(ptsOfNo2);
//            championRankInfo.setPtsOfNo3(ptsOfNo3);
//            championRankInfo.setPtsOfNo4(ptsOfNo4);
//            championRankInfo.setPtsOfNo5(ptsOfNo5);
//            championRankInfo.setPtsOfNo6(ptsOfNo6);
//            championRankInfo.setPtsOfNo7(ptsOfNo7);
//            championRankInfo.setPtsOfNo8(ptsOfNo8);
//
//            JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(championRankInfo));
//            result.add(jsonObject);
//        }
//
//        String updateTime = sdf.format(new Date());
//        championRankInfoJson.put("updateTime", updateTime);
//        championRankInfoJson.put("result", result);
//
//        return championRankInfoJson;
//    }


    public int getMinusPts(int playerId, int week, int season) {
        return gradeRecordService.selectMinusPts(playerId, week, season);
    }

    public int getPlusPts(int playerId, int week, int season) {
        return gradeRecordService.selectPlusPts(playerId, week, season);
    }

    public int getMinValidPtsOfWholePeriod(int playerId, int week, int season) {
        if (season < 2) {
            logger.error("season need >= 2");
            return 0;
        } else {
            List<GradeRecordPojo> gradeRecordPojoList = gradeRecordService.selectGradeRecordListOfWholePeriod(playerId, week, season);
            List<ClassifiblePojo> pojoList = new ArrayList<>();
            for (GradeRecordPojo gradeRecordPojo : gradeRecordPojoList) {
                pojoList.add(gradeRecordPojo);
            }
            Map<String, List<ClassifiblePojo>> gradeRecordMap = classifier.classifyByEventLevel(pojoList);
            List<Integer> otherList = new ArrayList<>();
            for (String eventLevel : gradeRecordMap.keySet()) {
                if (!EventLevelConstants.MANDATORY_EVENT.contains(eventLevel)) {
                    List<ClassifiblePojo> classifiblePojoList = gradeRecordMap.get(eventLevel);
                    for (int i = 0; i < classifiblePojoList.size(); i ++) {
                        GradeRecordPojo gradeRecordPojo = (GradeRecordPojo) classifiblePojoList.get(i);
                        otherList.add(gradeRecordPojo.getPts());
                    }
                }
            }
            Collections.sort(otherList, new Comparator<Integer>() {
                @Override
                public int compare(Integer o1, Integer o2) {
                    return -Integer.compare(o1, o2);
                }
            });

//            System.out.println(otherList);
            int minValidPts = (otherList.size() < NumConstants.TOP_PTS_NUM ? 0 : otherList.get(NumConstants.TOP_PTS_NUM - 1));

            return minValidPts;
        }
    }

    public int getMinValidPtsOfCurrSeason(int playerId, int week, int season) {
        if (season < 2) {
            logger.error("season need >= 2");
            return 0;
        } else {
            List<GradeRecordPojo> gradeRecordPojoList = gradeRecordService.selectGradeRecordListOfCurrentSeason(playerId, week, season);
            List<ClassifiblePojo> pojoList = new ArrayList<>();
            for (GradeRecordPojo gradeRecordPojo : gradeRecordPojoList) {
                pojoList.add(gradeRecordPojo);
            }
            Map<String, List<ClassifiblePojo>> gradeRecordMap = classifier.classifyByEventLevel(pojoList);
            List<Integer> otherList = new ArrayList<>();
            for (String eventLevel : gradeRecordMap.keySet()) {
                if (!EventLevelConstants.MANDATORY_EVENT.contains(eventLevel)) {
                    List<ClassifiblePojo> classifiblePojoList = gradeRecordMap.get(eventLevel);
                    for (int i = 0; i < classifiblePojoList.size(); i ++) {
                        GradeRecordPojo gradeRecordPojo = (GradeRecordPojo) classifiblePojoList.get(i);
                        otherList.add(gradeRecordPojo.getPts());
                    }
                }
            }
            Collections.sort(otherList, new Comparator<Integer>() {
                @Override
                public int compare(Integer o1, Integer o2) {
                    return -Integer.compare(o1, o2);
                }
            });

//            System.out.println(otherList);
            int minValidPts = (otherList.size() < NumConstants.TOP_PTS_NUM ? 0 : otherList.get(NumConstants.TOP_PTS_NUM - 1));

            return minValidPts;
        }
    }

    public String getCurrGrade(int playerId, int week, int season) {

        List<GradeRecordPojo> gradeRecordPojoList = gradeRecordService.selectByPlayerAndPhase(playerId, week, season);
        if (gradeRecordPojoList.size() == 0) {
            return "";
        }
        GradeRecordPojo lastGradeRecord = gradeRecordPojoList.get(gradeRecordPojoList.size() - 1);
        return lastGradeRecord.getEventName() + " " + lastGradeRecord.getGrade();
    }

    public int[] getWinsAndLossesOfCurrSeason(int playerId, int season, int singleOrDouble) {
        // 0-single 1-double
        int[] winsAndLosses = new int[2];
        winsAndLosses[0] = 0;
        winsAndLosses[1] = 0;

        if (singleOrDouble == NumConstants.SINGLE_MATCH_MODE) {
            List<ScoreBoardPojo> scoreBoardPojoList1 = scoreBoardService.selectAsPlayer1BySeasonWithoutWO(playerId, season);
            for (ScoreBoardPojo scoreBoardPojo : scoreBoardPojoList1) {
                int ret = scoreBoardPojo.getRet();
                if (ret == 1) {
                    winsAndLosses[1] += 1;
                } else if (ret == 2) {
                    winsAndLosses[0] += 1;
                } else if (ret == 0) {
                    if (scoreBoardPojo.getPlayer1Score() > scoreBoardPojo.getPlayer2Score()) {
                        winsAndLosses[0] += 1;
                    } else if (scoreBoardPojo.getPlayer1Score() < scoreBoardPojo.getPlayer2Score()) {
                        winsAndLosses[1] += 1;
                    }
                } else {
                    logger.error("ret is wrong");
                }
            }
    
            List<ScoreBoardPojo> scoreBoardPojoList2 = scoreBoardService.selectAsPlayer2BySeasonWithoutWO(playerId, season);
            for (ScoreBoardPojo scoreBoardPojo : scoreBoardPojoList2) {
                int ret = scoreBoardPojo.getRet();
                if (ret == 1) {
                    winsAndLosses[0] += 1;
                } else if (ret == 2) {
                    winsAndLosses[1] += 1;
                } else if (ret == 0) {
                    if (scoreBoardPojo.getPlayer1Score() > scoreBoardPojo.getPlayer2Score()) {
                        winsAndLosses[1] += 1;
                    } else if (scoreBoardPojo.getPlayer1Score() < scoreBoardPojo.getPlayer2Score()) {
                        winsAndLosses[0] += 1;
                    }
                } else {
                    logger.error("ret is wrong");
                }
            }
        } else if (singleOrDouble == NumConstants.DOUBLE_MATCH_MODE) {
            List<ScoreBoardDoublePojo> scoreBoardDoublePojoList1 = scoreBoardDoubleService.selectAsPlayer1BySeasonWithoutWO(playerId, season);
            for (ScoreBoardDoublePojo scoreBoardDoublePojo : scoreBoardDoublePojoList1) {
                int ret = scoreBoardDoublePojo.getRet();
                if (ret == 1) {
                    winsAndLosses[1] += 1;
                } else if (ret == 2) {
                    winsAndLosses[0] += 1;
                } else if (ret == 0) {
                    if (scoreBoardDoublePojo.getPlayer1Score() > scoreBoardDoublePojo.getPlayer2Score()) {
                        winsAndLosses[0] += 1;
                    } else if (scoreBoardDoublePojo.getPlayer1Score() < scoreBoardDoublePojo.getPlayer2Score()) {
                        winsAndLosses[1] += 1;
                    }
                } else {
                    logger.error("ret is wrong");
                }
            }

            List<ScoreBoardDoublePojo> scoreBoardDoublePojoList2 = scoreBoardDoubleService.selectAsPlayer2BySeasonWithoutWO(playerId, season);
            for (ScoreBoardDoublePojo scoreBoardDoublePojo : scoreBoardDoublePojoList2) {
                int ret = scoreBoardDoublePojo.getRet();
                if (ret == 1) {
                    winsAndLosses[0] += 1;
                } else if (ret == 2) {
                    winsAndLosses[1] += 1;
                } else if (ret == 0) {
                    if (scoreBoardDoublePojo.getPlayer1Score() > scoreBoardDoublePojo.getPlayer2Score()) {
                        winsAndLosses[1] += 1;
                    } else if (scoreBoardDoublePojo.getPlayer1Score() < scoreBoardDoublePojo.getPlayer2Score()) {
                        winsAndLosses[0] += 1;
                    }
                } else {
                    logger.error("ret is wrong");
                }
            }
        }

        return winsAndLosses;
    }

    public int[] getWinsAndLossesOfCareer(int playerId, int singleOrDouble) {
        // 0-single 1-double
        int[] winsAndLosses = new int[2];
        winsAndLosses[0] = 0;
        winsAndLosses[1] = 0;

        if (singleOrDouble == NumConstants.SINGLE_MATCH_MODE) {
            List<ScoreBoardPojo> scoreBoardPojoList1 = scoreBoardService.selectAsPlayer1WithoutWO(playerId);
            for (ScoreBoardPojo scoreBoardPojo : scoreBoardPojoList1) {
                int ret = scoreBoardPojo.getRet();
                if (ret == 1) {
                    winsAndLosses[1] += 1;
                } else if (ret == 2) {
                    winsAndLosses[0] += 1;
                } else if (ret == 0) {
                    if (scoreBoardPojo.getPlayer1Score() > scoreBoardPojo.getPlayer2Score()) {
                        winsAndLosses[0] += 1;
                    } else if (scoreBoardPojo.getPlayer1Score() < scoreBoardPojo.getPlayer2Score()) {
                        winsAndLosses[1] += 1;
                    }
                } else {
                    logger.error("ret is wrong");
                }
            }
    
            List<ScoreBoardPojo> scoreBoardPojoList2 = scoreBoardService.selectAsPlayer2WithoutWO(playerId);
            for (ScoreBoardPojo scoreBoardPojo : scoreBoardPojoList2) {
                int ret = scoreBoardPojo.getRet();
                if (ret == 1) {
                    winsAndLosses[0] += 1;
                } else if (ret == 2) {
                    winsAndLosses[1] += 1;
                } else if (ret == 0) {
                    if (scoreBoardPojo.getPlayer1Score() > scoreBoardPojo.getPlayer2Score()) {
                        winsAndLosses[1] += 1;
                    } else if (scoreBoardPojo.getPlayer1Score() < scoreBoardPojo.getPlayer2Score()) {
                        winsAndLosses[0] += 1;
                    }
                } else {
                    logger.error("ret is wrong");
                }
            }
        } else if (singleOrDouble == NumConstants.DOUBLE_MATCH_MODE) {
            List<ScoreBoardDoublePojo> scoreBoardDoublePojoList1 = scoreBoardDoubleService.selectAsPlayer1WithoutWO(playerId);
            for (ScoreBoardDoublePojo scoreBoardDoublePojo : scoreBoardDoublePojoList1) {
                int ret = scoreBoardDoublePojo.getRet();
                if (ret == 1) {
                    winsAndLosses[1] += 1;
                } else if (ret == 2) {
                    winsAndLosses[0] += 1;
                } else if (ret == 0) {
                    if (scoreBoardDoublePojo.getPlayer1Score() > scoreBoardDoublePojo.getPlayer2Score()) {
                        winsAndLosses[0] += 1;
                    } else if (scoreBoardDoublePojo.getPlayer1Score() < scoreBoardDoublePojo.getPlayer2Score()) {
                        winsAndLosses[1] += 1;
                    }
                } else {
                    logger.error("ret is wrong");
                }
            }

            List<ScoreBoardDoublePojo> scoreBoardDoublePojoList2 = scoreBoardDoubleService.selectAsPlayer2WithoutWO(playerId);
            for (ScoreBoardDoublePojo scoreBoardDoublePojo : scoreBoardDoublePojoList2) {
                int ret = scoreBoardDoublePojo.getRet();
                if (ret == 1) {
                    winsAndLosses[0] += 1;
                } else if (ret == 2) {
                    winsAndLosses[1] += 1;
                } else if (ret == 0) {
                    if (scoreBoardDoublePojo.getPlayer1Score() > scoreBoardDoublePojo.getPlayer2Score()) {
                        winsAndLosses[1] += 1;
                    } else if (scoreBoardDoublePojo.getPlayer1Score() < scoreBoardDoublePojo.getPlayer2Score()) {
                        winsAndLosses[0] += 1;
                    }
                } else {
                    logger.error("ret is wrong");
                }
            }
        }

        return winsAndLosses;
    }

    public int[] getWinsAndLossesOfCareerVsTop10(int playerId) {

        int[] winsAndLosses = new int[2];
        winsAndLosses[0] = 0;
        winsAndLosses[1] = 0;

        List<ScoreBoardPojo> scoreBoardPojoList = scoreBoardService.selectVsTopNOfCareerWithoutWO(playerId, 10);
        for (ScoreBoardPojo scoreBoardPojo : scoreBoardPojoList) {
            if (scoreBoardPojo.getPlayer1Id() == playerId) {
                int ret = scoreBoardPojo.getRet();
                if (ret == 1) {
                    winsAndLosses[1] += 1;
                } else if (ret == 2) {
                    winsAndLosses[0] += 1;
                } else if (ret == 0) {
                    if (scoreBoardPojo.getPlayer1Score() > scoreBoardPojo.getPlayer2Score()) {
                        winsAndLosses[0] += 1;
                    } else if (scoreBoardPojo.getPlayer1Score() < scoreBoardPojo.getPlayer2Score()) {
                        winsAndLosses[1] += 1;
                    }
                } else {
                    logger.error("ret is wrong");
                }
            } else if (scoreBoardPojo.getPlayer2Id() == playerId) {
                int ret = scoreBoardPojo.getRet();
                if (ret == 1) {
                    winsAndLosses[0] += 1;
                } else if (ret == 2) {
                    winsAndLosses[1] += 1;
                } else if (ret == 0) {
                    if (scoreBoardPojo.getPlayer1Score() > scoreBoardPojo.getPlayer2Score()) {
                        winsAndLosses[1] += 1;
                    } else if (scoreBoardPojo.getPlayer1Score() < scoreBoardPojo.getPlayer2Score()) {
                        winsAndLosses[0] += 1;
                    }
                }
            }
        }

        return winsAndLosses;
    }

    public int getStatusOfCurrWeek(int playerId, int week, int season) {

        EventInfoPojo eventInfoPojo = eventInfoService.selectBySeasonAndWeek(season, week);
        if (eventInfoPojo.getEventType() == NumConstants.SINGLE_MATCH_MODE) {

            List<ScoreBoardPojo> scoreBoardPojoList = scoreBoardService.selectByPlayerIdAndPhase(playerId, week, season);
            if (scoreBoardPojoList.size() == 0) {
                return NumConstants.DNP;
            }
            Collections.sort(scoreBoardPojoList, new Comparator<ScoreBoardPojo>() {
                @Override
                public int compare(ScoreBoardPojo o1, ScoreBoardPojo o2) {
                    return -Integer.compare(EventRound.getIndexByRound(o1.getRound()), EventRound.getIndexByRound(o2.getRound()));
                }
            });

            ScoreBoardPojo lastScoreBoardPojo = scoreBoardPojoList.get(0);
            if (lastScoreBoardPojo.getPlayer1Id() == playerId) {
                if (lastScoreBoardPojo.getPlayer1Score() > lastScoreBoardPojo.getPlayer2Score()) {
                    return NumConstants.IN;
                } else if (lastScoreBoardPojo.getPlayer1Score() < lastScoreBoardPojo.getPlayer2Score()) {
                    return NumConstants.OUT;
                }
            } else if (lastScoreBoardPojo.getPlayer2Id() == playerId) {
                if (lastScoreBoardPojo.getPlayer1Score() > lastScoreBoardPojo.getPlayer2Score()) {
                    return NumConstants.OUT;
                } else if (lastScoreBoardPojo.getPlayer1Score() < lastScoreBoardPojo.getPlayer2Score()) {
                    return NumConstants.IN;
                }
            }
            return NumConstants.DNP;
        } else if (eventInfoPojo.getEventType() == NumConstants.DOUBLE_MATCH_MODE) {
            List<ScoreBoardDoublePojo> scoreBoardDoublePojoList = scoreBoardDoubleService.selectByPlayerIdAndPhase(playerId, week, season);
            if (scoreBoardDoublePojoList.size() == 0) {
                return NumConstants.DNP;
            }
            Collections.sort(scoreBoardDoublePojoList, new Comparator<ScoreBoardDoublePojo>() {
                @Override
                public int compare(ScoreBoardDoublePojo o1, ScoreBoardDoublePojo o2) {
                    return -Integer.compare(EventRound.getIndexByRound(o1.getRound()), EventRound.getIndexByRound(o2.getRound()));
                }
            });

            ScoreBoardDoublePojo lastScoreBoardDoublePojo = scoreBoardDoublePojoList.get(0);
            if (lastScoreBoardDoublePojo.getPlayer1IdA() == playerId || lastScoreBoardDoublePojo.getPlayer1IdB() == playerId) {
                int ret = lastScoreBoardDoublePojo.getRet();
                if (ret == 0) {
                    if (lastScoreBoardDoublePojo.getPlayer1Score() > lastScoreBoardDoublePojo.getPlayer2Score()) {
                        return NumConstants.IN;
                    } else if (lastScoreBoardDoublePojo.getPlayer1Score() < lastScoreBoardDoublePojo.getPlayer2Score()) {
                        return NumConstants.OUT;
                    }
                } else if (ret == 1) {
                    return NumConstants.OUT;
                } else if (ret == 2) {
                    return NumConstants.IN;
                }
            } else if (lastScoreBoardDoublePojo.getPlayer2IdA() == playerId || lastScoreBoardDoublePojo.getPlayer2IdB() == playerId) {
                int ret = lastScoreBoardDoublePojo.getRet();
                if (ret == 0) {
                    if (lastScoreBoardDoublePojo.getPlayer1Score() > lastScoreBoardDoublePojo.getPlayer2Score()) {
                        return NumConstants.OUT;
                    } else if (lastScoreBoardDoublePojo.getPlayer1Score() < lastScoreBoardDoublePojo.getPlayer2Score()) {
                        return NumConstants.IN;
                    }
                } else if (ret == 1) {
                    return NumConstants.IN;
                } else if (ret == 2) {
                    return NumConstants.OUT;
                }
            }
            return NumConstants.DNP;
        }

        return NumConstants.DNP;
    }

    public int getNumOfEntriesOfWholePeriod(int playerId, int week, int season) {

        List<GradeRecordPojo> gradeRecordPojoList = gradeRecordService.selectGradeRecordListOfWholePeriod(playerId, week, season);
        return gradeRecordPojoList.size();
    }

    public int getNumOfTitlesOfCareer(int playerId) {

        return gradeRecordService.selectNumOfTitlesOfCareerByPlayerId(playerId);
    }

//    public JSONObject getPtsComponentOfWholePeriod(int playerId, int week, int season) {
//
//        Map<String, List<Integer>> ptsMap = new HashMap<>();
//        List<GradeRecordPojo> gradeRecordPojoList = gradeRecordService.selectGradeRecordListOfWholePeriod(playerId, week, season);
//
//        List<Integer> eventIdListOfT1 = eventInfoService.selectEventIdOfSpecificEventLevelOfWholePeriod(EventLevelConstants.T1, week, season);
//        List<Integer> eventIdListOfT2 = eventInfoService.selectEventIdOfSpecificEventLevelOfWholePeriod(EventLevelConstants.T2, week, season);
//        eventIdListOfT2.addAll(eventInfoService.selectEventIdOfSpecificEventLevelOfWholePeriod(EventLevelConstants.T2_PLUS, week, season));
//        List<Integer> eventIdListOfT3 = eventInfoService.selectEventIdOfSpecificEventLevelOfWholePeriod(EventLevelConstants.T3, week, season);
//        List<Integer> eventIdListOfYEC = eventInfoService.selectEventIdOfSpecificEventLevelOfWholePeriod(EventLevelConstants.YEC, week, season);
//        if (eventIdListOfYEC.size() != 1) {
//            logger.error("the num of YEC is wrong");
//            return ptsMap;
//        }
//
//        List<Integer> T1List = new ArrayList<>();
//        List<Integer> T2List = new ArrayList<>();
//        List<Integer> T3List = new ArrayList<>();
//        List<Integer> YECList = new ArrayList<>();
//
//        for (GradeRecordPojo gradeRecordPojo : gradeRecordPojoList) {
//            int eventId = gradeRecordPojo.getEventId();
//            int pts = gradeRecordPojo.getPts();
//            if (eventIdListOfGS.contains(eventId)) {
//                int index = eventIdListOfGS.indexOf(eventId);
//                GSList.set(index, pts);
//            } else if (eventIdListOfPM.contains(eventId)) {
//                int index = eventIdListOfPM.indexOf(eventId);
//                PMList.set(index, pts);
//            } else if (eventIdListOfAF.contains(eventId)) {
//                int index = eventIdListOfAF.indexOf(eventId);
//                AFList.set(index, pts);
//            } else {
//                NMList.add(pts);
//            }
//        }
//
//        Collections.sort(NMList, new Comparator<Integer>() {
//            @Override
//            public int compare(Integer o1, Integer o2) {
//                return o2 - o1;
//            }
//        });
//
//        ptsMap.put(EventLevelConstants.GS, GSList);
//        ptsMap.put(EventLevelConstants.PM, PMList);
//        ptsMap.put(EventLevelConstants.AF, AFList);
//
//        int num = NMList.size();
//        if (num < NumConstants.TOP_PTS_NUM) {
//            for (int i = 0; i < NumConstants.TOP_PTS_NUM - num; i ++) {
//                NMList.add(0);
//            }
//            ptsMap.put(EventLevelConstants.NM, NMList);
//        } else {
//            ptsMap.put(EventLevelConstants.NM, NMList.subList(0, NumConstants.TOP_PTS_NUM));
//        }
//
//        return ptsMap;
//    }
}
