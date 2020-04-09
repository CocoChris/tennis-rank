package com.ita.rank.service;

import com.ita.rank.pojo.EventInfoPojo;
import com.ita.rank.pojo.EventLevelPojo;
import com.ita.rank.pojo.GradeRecordPojo;
import com.ita.rank.pojo.ScoreBoardPojo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: liuxingxin
 * @Date: 2019/2/13
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class ScoreBoardServiceTest {

    @Autowired
    ScoreBoardService scoreBoardService;

    @Test
    public void testInsert() {

//        ScoreBoardPojo scoreBoardPojo = new ScoreBoardPojo("广州公开赛", "R1", 1, "LisaHurry", 0, "ming", 1);
//        scoreBoardService.insert(scoreBoardPojo);
//        scoreBoardPojo = new ScoreBoardPojo("广州公开赛", "QFi", 1, "zk1995", 0, "ming", 1);
//        scoreBoardService.insert(scoreBoardPojo);

        ScoreBoardPojo scoreBoardPojo = new ScoreBoardPojo("广州公开赛", "R1", 1, "LisaHurry", 0, "ming", 1);
        System.out.println(scoreBoardPojo.toString());
    }

    @Test
    public void testInsertBatch() {
        try {
            String fileName = "src/main/resources/txt/score_week_2_season_2.txt";
            String[] splits1 = fileName.split("/");
            String[] splits2 = splits1[splits1.length - 1].split("_");

            int week = Integer.valueOf(splits2[2]);
            int season = Integer.valueOf(splits2[4].split("\\.")[0]);

            System.out.println("week = " + week + ", season = " + season);

            FileInputStream inputStream = new FileInputStream(fileName);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String str;
            List<ScoreBoardPojo> scoreBoardList = new ArrayList<>();
            while((str = bufferedReader.readLine()) != null)
            {
                if (!str.equals("")) {
//                    System.out.println(str);
                    String[] values = str.split(",");
                    String player1Name = values[0];
                    int player1Score = Integer.valueOf(values[1]);
                    String player2Name = values[3];
                    int player2Score = Integer.valueOf(values[2]);
                    String round = values[4];
                    int size = values.length;

                    ScoreBoardPojo scoreBoardPojo = new ScoreBoardPojo();
                    scoreBoardPojo.setWeek(week);
                    scoreBoardPojo.setSeason(season);
                    scoreBoardPojo.setPlayer1Name(player1Name);
                    scoreBoardPojo.setPlayer2Name(player2Name);
                    scoreBoardPojo.setPlayer1Score(player1Score);
                    scoreBoardPojo.setPlayer2Score(player2Score);
                    scoreBoardPojo.setRound(round);
                    scoreBoardPojo.setWo(0);
                    scoreBoardPojo.setRet(0);

                    if (size == 6) {
                        int wo = Integer.valueOf(values[5]);
                        scoreBoardPojo.setWo(wo);
                    }

                    if (size == 7) {
                        int ret = Integer.valueOf(values[6]);
                        scoreBoardPojo.setRet(ret);
                    }

                    scoreBoardList.add(scoreBoardPojo);
                }
            }

            scoreBoardService.insertBatch(scoreBoardList);

            //close
            inputStream.close();
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}