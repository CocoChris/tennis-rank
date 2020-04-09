package com.ita.rank.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * @Author: liuxingxin
 * @Date: 2019/2/25
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class EventInfoServiceTest {
  
    @Autowired
    EventInfoService eventInfoService;

    @Test
    public void testSelectByEventName() {
        System.out.println(eventInfoService.selectByEventName("阿卡普尔科公开赛", 2));
    }

    @Test
    public void testselectEventIdOfSpecificEventLevelOfCurrSeason() {
        System.out.println(eventInfoService.selectEventIdOfSpecificEventLevelOfCurrSeason("GS",  2));
    }
}
