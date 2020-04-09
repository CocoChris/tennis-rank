package com.ita.rank.service;

import com.ita.rank.pojo.ClassifiblePojo;
import com.ita.rank.pojo.GradeRecordPojo;
import com.ita.rank.processor.Classifier;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: liuxingxin
 * @Date: 2019/2/21
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class RankServiceTest {

    @Autowired
    RankService rankService;

    @Autowired
    GradeRecordService gradeRecordService;

    @Autowired
    Classifier classifier;

    @Test
    public void testQueryRankInfo() {

        List<GradeRecordPojo> pojoList =  gradeRecordService.selectGradeRecordListOfWholePeriod(1002, 20, 1);
        List<ClassifiblePojo> classifiblePojoList = new ArrayList<>();
        for (GradeRecordPojo gradeRecordPojo : pojoList) {
            classifiblePojoList.add(gradeRecordPojo);
        }
        classifier.classifyByEventLevel(classifiblePojoList);
    }

    @Test
    public void testQueryChampionRankInfo() {

//        System.out.println(rankService.queryChampionRankInfo());
    }

    @Test
    public void testGetMinValidPts() {
        System.out.println(rankService.getMinValidPtsOfWholePeriod(1014, 0, 2));
    }

    @Test
    public void testGetWinsAndLossesOfCurrSeason() {
        int[] winsAndLosses = rankService.getWinsAndLossesOfCurrSeason(1018, 1);
        System.out.println(winsAndLosses[0] + "-" + winsAndLosses[1]);
    }

    @Test
    public void testGetWinsAndLossesOfCareer() {
        int[] winsAndLosses = rankService.getWinsAndLossesOfCareer(1018);
        System.out.println(winsAndLosses[0] + "-" + winsAndLosses[1]);
    }

    @Test
    public void testGetWinsAndLossesOfCareerVsTop10() {
        int[] winsAndLosses = rankService.getWinsAndLossesOfCareerVsTop10(1018);
        System.out.println(winsAndLosses[0] + "-" + winsAndLosses[1]);
    }

    @Test
    public void testGetStatusOfCurrWeek() {
        System.out.println(rankService.getStatusOfCurrWeek(1001, 7, 1));
    }

    @Test
    public void testGetNumOfEntriesOfWholePeriod() {
        System.out.println(rankService.getNumOfEntriesOfWholePeriod(1002, 0, 2));
    }

    @Test
    public void testGetNumOfTitlesOfCareer() {
        System.out.println(rankService.getNumOfTitlesOfCareer(1011));
    }

    @Test
    public void testGetPtsComponentOfCurrSeason() {
//        System.out.println(rankService.getPtsComponentOfCurrSeason(1002, 13, 2));
    }
}