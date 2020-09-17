package com.ita.rank.service;

import com.ita.rank.pojo.EventInfoPojo;
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

    @Test
    public void testInsert() {
        EventInfoPojo eventInfoPojo = new EventInfoPojo();
        eventInfoPojo.setEventId(2006);
        eventInfoPojo.setEventName("天使之翼大师赛");
        eventInfoPojo.setLevelCode("BDG");
        eventInfoPojo.setEventType(1);
        eventInfoPojo.setEventMode(1);
        eventInfoPojo.setWeek(7);
        eventInfoPojo.setSeason(2);
        eventInfoPojo.setDate("2020-08-01");

        eventInfoService.insert(eventInfoPojo);
    }
}
