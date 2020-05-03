package com.ita.rank.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @Author: liuxingxin
 * @Date: 2020/4/22
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class PlayerStatsServiceTest {

    @Autowired
    PlayerStatsService playerStatsService;

    @Test
    public void testGetPlayerStats() {
    }

    @Test
    public void testGetComponentMapOfWholePeriod() {

        System.out.println(playerStatsService.getComponentMapOfWholePeriod(8, 3, 2));
    }

    @Test
    public void testGetPtsComponentOfWholePeriod() {

        System.out.println(playerStatsService.getPtsComponentOfWholePeriod(8, 3, 2));
    }

    @Test
    public void testGetPtsComponentOfWholeInEventDate() {
        System.out.println(playerStatsService.getPtsComponentOfWholePeriodInEventDate(2, 3, 2));
    }

    @Test
    public void testGetRankHistoty() {
        System.out.println(playerStatsService.getRankHistory(2));
    }

    @Test
    public void testGetSingleWinRateDist() {
        System.out.println(playerStatsService.getSingleWinRateDist(1));
    }
}