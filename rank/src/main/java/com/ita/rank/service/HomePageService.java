package com.ita.rank.service;

import com.alibaba.fastjson.JSONObject;
import com.ita.rank.pojo.PlayerInfoPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.apache.commons.lang3.math.NumberUtils.min;

/**
 * @Author: liuxingxin
 * @Date: 2019/3/9
 */

@Service
public class HomePageService {
  
    @Autowired
    PlayerInfoService playerInfoService;
    
    private static final int SIZE_OF_HOT_SEARCH_LIST = 10;
    
    public JSONObject queryHotSearchList() {
      
        JSONObject hotSearchList = new JSONObject();
        List<PlayerInfoPojo> playerInfoPojoList = playerInfoService.selectOrderByLiveSearchCount();
        
//        for(int i = 0; i < playerInfoPojoList.size(); i ++) {
//            System.out.println(playerInfoPojoList.get(i).toString());
//        }
        
        int num = min(SIZE_OF_HOT_SEARCH_LIST, playerInfoPojoList.size());
        for (int i = 0; i < num; i ++) {
             JSONObject json = new JSONObject();
             int liveSearchCount = playerInfoPojoList.get(i).getLiveSearchCount();
             int preSearchCount = playerInfoPojoList.get(i).getPreSearchCount();
             json.put("searchCount", liveSearchCount);
             json.put("increment", liveSearchCount - preSearchCount);
             json.put("rank", i+1);
             hotSearchList.put(playerInfoPojoList.get(i).getPlayerName(), json);
        }
        
        return hotSearchList;
    }

    public void updateHotSearchList() {

        List<PlayerInfoPojo> playerInfoPojoList = playerInfoService.selectAll();
        for (PlayerInfoPojo playerInfoPojo : playerInfoPojoList) {
            playerInfoPojo.setPreSearchCount(playerInfoPojo.getLiveSearchCount());
            playerInfoService.updatePreSearchCount(playerInfoPojo);
        }
    }
}
