package com.ita.rank.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * @Author: liuxingxin
 * @Date: 2019/2/14
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class EventLevelServiceTest {

    @Autowired
    EventLevelService eventLevelService;

    @Test
    public void testGetScoreList() {

//        List<String> oldCodeList = eventLevelService.selectAllCode(1);
//        System.out.println(oldCodeList);
//        for (String code : oldCodeList) {
//            System.out.println(eventLevelService.getScoreList(code, 1));
//        }
//        List<String> newCodeList = eventLevelService.selectAllCode(2);
//        System.out.println(newCodeList);
//        for (String code : newCodeList) {
//            System.out.println(eventLevelService.getScoreList(code, 2));
//        }
        System.out.println(eventLevelService.getScoreList("H8"));
    }

    @Test
    public void testGetInitRound() {
        System.out.println(eventLevelService.getInitRound("QF", "H8"));
    }

    @Test
    public void testGetNextRound() {
        System.out.println(eventLevelService.getNextRound("Q2", "H8"));
    }
}