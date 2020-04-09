package com.ita.rank.service;

import com.ita.rank.dao.PlayerInfoMapper;
import com.ita.rank.pojo.PlayerInfoPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: liuxingxin
 * @Date: 2019/2/13
 */

@Service
public class PlayerInfoService {

    @Autowired
    PlayerInfoMapper playerInfoMapper;

    public int insertBatch(List<PlayerInfoPojo> playerList) {
        return playerInfoMapper.insertBatch(playerList);
    }

    public PlayerInfoPojo selectByPlayerId(int playerId) {

        return playerInfoMapper.selectByPlayerId(playerId);
    }

    public PlayerInfoPojo selectByPlayerName(String playerName) {
        List<PlayerInfoPojo> playerInfoList = playerInfoMapper.selectByPlayerName(playerName);
        for (PlayerInfoPojo playerInfo : playerInfoList) {
            String name = playerInfo.getPlayerName();
            if (name.equals(playerName)) {
                return playerInfo;
            }
        }
        return null;
    }

    public List<Integer> selectPlayerIdList() {
        return playerInfoMapper.selectPlayerIdList();
    }

    public List<PlayerInfoPojo> selectAll() {
        return playerInfoMapper.selectAll();
    }

    public void updateLiveSearchCount(PlayerInfoPojo playerInfoPojo) {
        playerInfoMapper.updateLiveSearchCount(playerInfoPojo);
    }

    public void updatePreSearchCount(PlayerInfoPojo playerInfoPojo) {
        playerInfoMapper.updatePreSearchCount(playerInfoPojo);
    }

    public List<PlayerInfoPojo> selectOrderByLiveSearchCount() {
        return playerInfoMapper.selectOrderByLiveSearchCount();
    }
}
