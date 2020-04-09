package com.ita.rank.service;

import com.ita.rank.pojo.EventInfoPojo;
import com.ita.rank.pojo.GradeRecordPojo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: liuxingxin
 * @Date: 2019/2/14
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class GradeRecordServiceTest {

    @Autowired
    GradeRecordService gradeRecordService;

    @Autowired
    PlayerInfoService playerInfoService;

    @Autowired
    EventInfoService eventInfoService;

    @Autowired
    EventLevelService eventLevelService;

    @Test
    public void testSelectByPlayerId() {
        System.out.println(gradeRecordService.selectByPlayerId(1019, 10001));
    }

    @Test
    public void testSelectGradeRecordList() {
        List<GradeRecordPojo> gradeRecordPojoList = gradeRecordService.selectGradeRecordListOfCurrentSeason(1004, 8, 2);
        for (GradeRecordPojo gradeRecordPojo : gradeRecordPojoList) {
            System.out.println(gradeRecordPojo.toString());
        }
    }

    @Test
    public void testInsertBatch() {
        try {
            FileInputStream inputStream = new FileInputStream("src/main/resources/txt/grade_season_1.txt");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String str;
            List<GradeRecordPojo> gradeRecordList = new ArrayList<>();
            while((str = bufferedReader.readLine()) != null)
            {
                if (!str.equals("")) {
//                    System.out.println(str);
                    String[] values = str.split(",");
                    int week = Integer.valueOf(values[0]) + 1;
                    String playerName = values[1];
                    int pts = Integer.valueOf(values[2]);
                    String grade = values[3];
                    int season = 1;
                    int qualified = 0;

                    GradeRecordPojo record = new GradeRecordPojo();
                    int playerId = playerInfoService.selectByPlayerName(playerName).getPlayerId();
                    EventInfoPojo eventInfoPojo = eventInfoService.selectBySeasonAndWeek(season, week);
                    int eventId = eventInfoPojo.getEventId();
                    String eventName = eventInfoPojo.getEventName();
                    String levelCode = eventInfoPojo.getLevelCode();
                    String eventLevel = eventLevelService.selectLevelByCode(levelCode).getLevel();

                    record.setPlayerId(playerId);
                    record.setPlayerName(playerName);
                    record.setEventId(eventId);
                    record.setEventName(eventName);
                    record.setEventLevel(eventLevel);
                    record.setLevelCode(levelCode);
                    record.setGrade(grade);
                    record.setQualified(qualified);
                    record.setPts(pts);
                    record.setWeek(week);
                    record.setSeason(season);

                    gradeRecordList.add(record);
                }
            }

            gradeRecordService.insertBatch(gradeRecordList);

            //close
            inputStream.close();
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testUpdateGrade() {
    }
}