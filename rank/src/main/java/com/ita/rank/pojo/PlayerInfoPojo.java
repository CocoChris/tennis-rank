package com.ita.rank.pojo;

import lombok.Data;

@Data
public class PlayerInfoPojo {
    
    private int playerId;

    private String playerName;

    private int liveSearchCount;

    private int preSearchCount;

    public PlayerInfoPojo(int playerId, String playerName, int liveSearchCount, int preSearchCount) {
        this.playerId = playerId;
        this.playerName = playerName;
        this.liveSearchCount = liveSearchCount;
        this.preSearchCount = preSearchCount;
    }

    public PlayerInfoPojo() { super(); }
}