package com.ita.rank.service;

import com.ita.rank.dao.EventInfoMapper;
import com.ita.rank.pojo.EventInfoPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: liuxingxin
 * @Date: 2019/2/13
 */

@Service
public class EventInfoService {

    @Autowired
    EventInfoMapper eventInfoMapper;

    public EventInfoPojo selectBySeasonAndWeek(int season, int week) {
        return eventInfoMapper.selectBySeasonAndWeek(season, week);
    }

    public EventInfoPojo selectByEventName(String eventName, int season) {

        return eventInfoMapper.selectByEventName(eventName, season);
    }

    public List<Integer> selectEventIdOfSpecificEventLevelOfWholePeriod(String eventLevel, int week, int season) {

        return eventInfoMapper.selectEventIdOfSpecificEventLevelOfWholePeriod(eventLevel, week, season);
    }

    public List<Integer> selectEventIdOfSpecificEventLevelOfCurrSeason(String eventLevel, int season) {

        return eventInfoMapper.selectEventIdOfSpecificEventLevelOfCurrSeason(eventLevel, season);
    }

    public int insert(EventInfoPojo eventInfoPojo) {
        return eventInfoMapper.insert(eventInfoPojo);
    }
}
