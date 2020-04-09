package com.ita.rank.service;

import com.ita.rank.dao.EventLevelMapper;
import com.ita.rank.pojo.EventLevelPojo;
import com.ita.rank.enums.EventRound;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: liuxingxin
 * @Date: 2019/2/13
 */

@Service
public class EventLevelService {

    @Autowired
    EventLevelMapper eventLevelMapper;

    private final Logger logger = LoggerFactory.getLogger(EventLevelService.class);

    public EventLevelPojo selectLevelByCode(String code) {

        return eventLevelMapper.selectByCode(code);
    }

    public List<String> selectAllCode() {
        return eventLevelMapper.selectAllCode();
    }

    public List<Integer> getScoreList(String code) {

        List<Integer> scoreList = new ArrayList<>();

        EventLevelPojo eventLevelPojo = selectLevelByCode(code);
        scoreList.add(eventLevelPojo.getQr1());
        scoreList.add(eventLevelPojo.getQr2());
        scoreList.add(eventLevelPojo.getQfi());
        scoreList.add(eventLevelPojo.getQ());
        scoreList.add(eventLevelPojo.getR64());
        scoreList.add(eventLevelPojo.getR32());
        scoreList.add(eventLevelPojo.getR16());
        scoreList.add(eventLevelPojo.getQf());
        scoreList.add(eventLevelPojo.getSf());
        scoreList.add(eventLevelPojo.getF());
        scoreList.add(eventLevelPojo.getW());

        return scoreList;
    }

    public String getInitRound(String round, String code) {

        try {
            int index = EventRound.getIndexByRound(round);

            List<Integer> scoreList = getScoreList(code);

            if (scoreList.get(index) == null || scoreList.get(index) == 0) {
                logger.error("the round is wrong");
                return null;
            }

            if (index < EventRound.Q.getIndex()) {
                if (scoreList.get(EventRound.QR1.getIndex()) != null && scoreList.get(EventRound.QR1.getIndex()) > 0) {
                    return EventRound.QR1.getRound();
                }
                if (scoreList.get(EventRound.Q.getIndex()) > 0) {
                    return EventRound.QR1.getRound();
                }
            } else if (index > EventRound.Q.getIndex()) {
                for (int i = EventRound.R64.getIndex(); i < EventRound.F.getIndex(); i ++) {
                    if (scoreList.get(i) != null && scoreList.get(i) > 0) {
                        return EventRound.getRoundByIndex(i);
                    }
                }
            } else {
                throw new Exception();
            }
        } catch (Exception e) {
            logger.error("error = {}", e.getMessage());
        }

        return null;
    }

    public String getNextRound(String round, String code) {

        try {
            int index = EventRound.getIndexByRound(round);

            List<Integer> scoreList = getScoreList(code);

            if (scoreList.get(index) == null || scoreList.get(index) == 0) {
                logger.error("the round is wrong");
                return null;
            }

            if (index < EventRound.Q.getIndex()) {
                for (int i = index + 1; i <= EventRound.Q.getIndex(); i ++) {
                    if (scoreList.get(i) != null && scoreList.get(i) > 0) {
                        return EventRound.getRoundByIndex(i);
                    }
                }
            } else if (index > EventRound.Q.getIndex()) {
                for (int i = index + 1; index <= EventRound.W.getIndex(); i ++) {
                    if (scoreList.get(i) != null && scoreList.get(i) > 0) {
                        return EventRound.getRoundByIndex(i);
                    }
                }
            } else {
                throw new Exception();
            }
        } catch (Exception e) {
            logger.error("error = {}", e.getMessage());
        }

        return null;
    }

    public Integer getScore(String round, String code, int season) {

        int index = EventRound.getIndexByRound(round);

        List<Integer> scoreList = getScoreList(code);

        if (scoreList.get(index) == null || scoreList.get(index) == 0) {
            if (index == EventRound.QR1.getIndex()) {
                logger.info("the round is QR1 and has no score");
                return 0;
            } else {
                logger.error("the round {} of {} in {} has no score", round, code, season);
                return null;
            }
        } else {
            return scoreList.get(index);
        }
    }
}
