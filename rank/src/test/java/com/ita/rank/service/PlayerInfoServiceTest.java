package com.ita.rank.service;

import com.alibaba.fastjson.JSON;
import com.ita.rank.pojo.PlayerInfoPojo;
import com.ita.rank.utils.XlsxUtil;
import org.apache.ibatis.annotations.Insert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: liuxingxin
 * @Date: 2019/2/21
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class PlayerInfoServiceTest {

    @Autowired
    PlayerInfoService playerInfoService;

    @Autowired
    ScoreBoardService scoreBoardService;

    @Test
    public void testInsertBatch() {
        try {
            File xlsx = new File("src/main/resources/xlsx/rank_200323.xlsx");
            String filepath = xlsx.getAbsolutePath();
            System.out.println(filepath);
            XlsxUtil excelReader = new XlsxUtil(filepath);

            List<List<Object>> list = excelReader.readExcelContent_();

            List<PlayerInfoPojo> playerList = new ArrayList<>();
            PlayerInfoPojo pojo = new PlayerInfoPojo();
            pojo.setPlayerName("小星");
            playerList.add(pojo);
            for (int i = 3; i < list.size() - 4; i ++) {
                String name = ((String)list.get(i).get(1)).trim();
                if (!name.equals("小星")) {
                    PlayerInfoPojo newPojo = new PlayerInfoPojo();
                    newPojo.setPlayerName(name);
                    playerList.add(newPojo);
                }
            }
            System.out.println(playerList);
            playerInfoService.insertBatch(playerList);
        } catch (FileNotFoundException e) {
            System.out.println("未找到指定路径的文件!");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testInsert() {
        List<PlayerInfoPojo> playerInfoPojoList = new ArrayList<>();
        PlayerInfoPojo pojo1 = new PlayerInfoPojo();
        pojo1.setPlayerName("Owen");
        playerInfoPojoList.add(pojo1);
        PlayerInfoPojo pojo2 = new PlayerInfoPojo();
        pojo2.setPlayerName("胖丘");
        playerInfoPojoList.add(pojo2);
        PlayerInfoPojo pojo3 = new PlayerInfoPojo();
        pojo3.setPlayerName("法学派");
        playerInfoPojoList.add(pojo3);
        PlayerInfoPojo pojo4 = new PlayerInfoPojo();
        pojo4.setPlayerName("Joey");
        playerInfoPojoList.add(pojo4);
//        PlayerInfoPojo pojo5 = new PlayerInfoPojo();
//        pojo5.setPlayerName("Chris");
//        playerInfoPojoList.add(pojo5);
//        PlayerInfoPojo pojo6 = new PlayerInfoPojo();
//        pojo6.setPlayerName("Kingkom");
//        playerInfoPojoList.add(pojo6);

        playerInfoService.insertBatch(playerInfoPojoList);
    }

    @Test
    public void testSelectByPlayerId() {
        System.out.println(playerInfoService.selectByPlayerId(1001).getPlayerName().equals(scoreBoardService.selectAsPlayer1WithoutWO(1001).get(0).getPlayer1Name()));
    }

    @Test
    public void testSelectByPlayerName() {

        System.out.println(playerInfoService.selectByPlayerName("不想不问不懂蔷"));
    }

    @Test
    public void testSelectPlayerIdList() {
    }
}