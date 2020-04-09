package com.ita.rank.service;

import com.ita.rank.dao.CurrentPhaseMapper;
import com.ita.rank.pojo.CurrentPhasePojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: liuxingxin
 * @Date: 2019/2/20
 */

@Service
public class CurrentPhaseService {

    @Autowired
    CurrentPhaseMapper currentPhaseMapper;

    public CurrentPhasePojo selectCurrentPhase() {
        return currentPhaseMapper.selectCurrentPhase();
    }

    public void updateWeek(int week) {
        currentPhaseMapper.updateWeek(week);
    }

    public void updateSeason(int season) {
        currentPhaseMapper.updateSeason(season);
    }
}
