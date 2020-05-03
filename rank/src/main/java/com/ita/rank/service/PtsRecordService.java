package com.ita.rank.service;

import com.ita.rank.common.constants.NumConstants;
import com.ita.rank.dao.PtsRecordMapper;
import com.ita.rank.pojo.PtsRecordPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: liuxingxin
 * @Date: 2019/2/19
 */

@Service
public class PtsRecordService {

    @Autowired
    PtsRecordMapper ptsRecordMapper;

    @Autowired
    PlayerInfoService playerInfoService;

    private static final int RANK_NONE = 999;

    public PtsRecordPojo selectByPlayerIdAndPhase(int playerId, int week, int season) {
        return ptsRecordMapper.selectByPlayerIdAndPhase(playerId, week, season);
    }

    public List<PtsRecordPojo> selectByWeekAndSeason(int week, int season) {
        return ptsRecordMapper.selectByWeekAndSeason(week, season);
    }

    public int selectRankOfLastWeek(int playerId, int week, int season) {

        Integer rank;
        if (week == 0) {
            rank = ptsRecordMapper.selectByPlayerIdAndPhase(playerId, NumConstants.TOTAL_WEEKS_OF_SEASON, season - 1).getRank();
        } else {
            rank = ptsRecordMapper.selectRankOfLastWeek(playerId, week, season);
        }

        if (rank == null) {
            return RANK_NONE;
        } else {
            return rank;
        }
    }

    public int selectHighestRank(int playerId, int week, int season) {

        Integer highestRank = ptsRecordMapper.selectHighestRank(playerId, week, season);
        if (highestRank == null) {
            return RANK_NONE;
        } else {
            return highestRank;
        }
    }

    public List<PtsRecordPojo> selectByPlayerId(int playerId) {
        return ptsRecordMapper.selectByPlayerId(playerId);
    }

    public List<PtsRecordPojo> selectByPlayerIdAndSeason(int playerId, int season) {
        return ptsRecordMapper.selectByPlayerIdAndSeason(playerId, season);
    }

    public void updateTotalPts(PtsRecordPojo ptsRecordPojo) {

        ptsRecordMapper.updateTotalPts(ptsRecordPojo);
    }

    public void updateRank(PtsRecordPojo ptsRecordPojo) {

        ptsRecordMapper.updateRank(ptsRecordPojo);
    }

    public int insert(PtsRecordPojo ptsRecordPojo) {

        int playerId = ptsRecordPojo.getPlayerId();
        String playerName = playerInfoService.selectByPlayerId(playerId).getPlayerName();
        ptsRecordPojo.setPlayerName(playerName);
        return ptsRecordMapper.insert(ptsRecordPojo);
    }
}
