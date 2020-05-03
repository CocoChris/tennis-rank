package com.ita.rank.controller;

import com.alibaba.fastjson.JSONObject;
import com.ita.rank.service.HomePageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * @Author: liuxingxin
 * @Date: 2019/3/9
 */

@RestController
@RequestMapping(value = "/api/home")
public class HomePageController {
    
    @Autowired
    HomePageService homePageService;

    public JSONObject hotSearchJson = new JSONObject();

    private final Logger logger = LoggerFactory.getLogger(HomePageController.class);

//    @PostConstruct
    public void init() {
  
        logger.info("start initializing hotSearchList");
        hotSearchJson = homePageService.queryHotSearchList();
    }
  
    @RequestMapping(method = GET, value = "/hotSearch")
    @ResponseBody
    public JSONObject showHotSearchList() {
        
        return hotSearchJson;
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void updateHotSearch() {
      
        logger.info("start updating hotSearchList");
        hotSearchJson = homePageService.queryHotSearchList();
        homePageService.updateHotSearchList();
    }
}
