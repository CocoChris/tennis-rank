package com.ita.rank.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * @Author: liuxingxin
 * @Date: 2019/2/22
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class RankControllerTest {

    @Autowired
    RankController rankController;

    @Test
    public void showLiveRank() {

        rankController.showLiveRank();
    }

    @Test
    public void showChampionRank() {

        rankController.showChampionRank();
    }
}