package com.ita.rank.pojo;

import lombok.Data;

/**
 * @Author: liuxingxin
 * @Date: 2019/3/8
 */

@Data
public class H2HRecordDoublePojo {
  
    private String winnerNameAndRank;
    
    private String loserNameAndRank;
    
    private int season;
    
    private String eventLevel;
    
    private int eventMode;
    
    private String eventName;
    
    private String round;
    
    private String score;

    /**
     * 标记赛果
     * 0-退赛 1-player1获胜 2-player2或topN获胜
     */
    private int resultFlag; 
}
