package com.ita.rank.service;

import com.ita.rank.dao.GradeRecordMapper;
import com.ita.rank.pojo.GradeRecordPojo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: liuxingxin
 * @Date: 2019/2/13
 */

@Service
public class GradeRecordService {

    private final Logger logger = LoggerFactory.getLogger(GradeRecordService.class);

    @Autowired
    GradeRecordMapper gradeRecordMapper;

    public int insertBatch(List<GradeRecordPojo> gradeRecordList) {
        return gradeRecordMapper.insertBatch(gradeRecordList);
    }

    public GradeRecordPojo selectByPlayerId(int playerId, int eventId) {

        return gradeRecordMapper.selectByPlayerIdAndEventId(playerId, eventId);
    }

    public List<GradeRecordPojo> selectGradeRecordListOfWholePeriod(int playerId, int week, int season) {

        return gradeRecordMapper.selectGradeRecordListOfWholePeriod(playerId, week, season);
    }

    public List<GradeRecordPojo> selectGradeRecordListOfCurrentSeason(int playerId, int week, int season) {

        return gradeRecordMapper.selectGradeRecordListOfCurrentSeason(playerId, week, season);
    }

    public int selectMinusPts(int playerId, int week, int season) {

        List<GradeRecordPojo> gradeRecordPojoList = gradeRecordMapper.selectByPlayerIdAndPhase(playerId, week, season - 1);
        int minusPts = 0;
        for (GradeRecordPojo gradeRecordPojo : gradeRecordPojoList) {
            minusPts += gradeRecordPojo.getPts();
        }
        return minusPts;
    }

    public int selectPlusPts(int playerId, int week, int season) {

        List<GradeRecordPojo> gradeRecordPojoList = gradeRecordMapper.selectByPlayerIdAndPhase(playerId, week, season);
        int plusPts = 0;
        for (GradeRecordPojo gradeRecordPojo : gradeRecordPojoList) {
            plusPts += gradeRecordPojo.getPts();
        }
        return plusPts;
    }

    public List<GradeRecordPojo> selectByPlayerAndPhase(int playerId, int week, int season) {

        return gradeRecordMapper.selectByPlayerIdAndPhase(playerId, week, season);
    }

    public int selectNumOfTitlesOfCareerByPlayerId(int playerId) {

        return gradeRecordMapper.selectNumOfTitlesOfCareerByPlayerId(playerId);
    }

    public List<GradeRecordPojo> selectByGrade(String grade) {

        return gradeRecordMapper.selectByGrade(grade);
    }

    public int insert(GradeRecordPojo gradeRecordPojo) {

        return gradeRecordMapper.insert(gradeRecordPojo);
    }

    public void updateGrade(GradeRecordPojo gradeRecordPojo) {

        gradeRecordMapper.updateGrade(gradeRecordPojo);
    }
}
