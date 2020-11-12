package com.ita.rank.service;

import com.ita.rank.dao.PtsRecordChampionMapper;
import com.ita.rank.pojo.PtsRecordChampionPojo;
import com.ita.rank.pojo.PtsRecordPojo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: liuxingxin
 * @Date: 2019/2/19
 */

@Service
public class PtsRecordChampionService {

    @Autowired
    PtsRecordChampionMapper ptsRecordChampionMapper;

    @Autowired
    PlayerInfoService playerInfoService;

    private static final int RANK_NONE = 999;

    private final Logger logger = LoggerFactory.getLogger(PtsRecordChampionService.class);

    public PtsRecordChampionPojo selectByPlayerId(int playerId, int week, int season) {
        return ptsRecordChampionMapper.selectByPlayerId(playerId, week, season);
    }

    public List<PtsRecordChampionPojo> selectByWeekAndSeason(int week, int season) {
        return ptsRecordChampionMapper.selectByWeekAndSeason(week, season);
    }

    public PtsRecordChampionPojo selectByPlayerIdAndPhase(int playerId, int week, int season) {
        return ptsRecordChampionMapper.selectByPlayerIdAndPhase(playerId, week, season);
    }

    public Integer selectRankOfLastWeek(int playerId, int week, int season) {

        Integer rank = ptsRecordChampionMapper.selectRankOfLastWeek(playerId, week, season);

        if (rank == null) {
            return RANK_NONE;
        } else {
            return rank;
        }
    }

    public void updateTotalPts(PtsRecordChampionPojo ptsRecordChampionPojo) {
        ptsRecordChampionMapper.updateTotalPts(ptsRecordChampionPojo);
    }

    public void updateRank(PtsRecordChampionPojo ptsRecordChampionPojo) {
        ptsRecordChampionMapper.updateRank(ptsRecordChampionPojo);
    }

    public int insert(PtsRecordChampionPojo ptsRecordChampionPojo) {

        int playerId = ptsRecordChampionPojo.getPlayerId();
        String playerName = playerInfoService.selectByPlayerId(playerId).getPlayerName();
        ptsRecordChampionPojo.setPlayerName(playerName);
        return ptsRecordChampionMapper.insert(ptsRecordChampionPojo);
    }
}
