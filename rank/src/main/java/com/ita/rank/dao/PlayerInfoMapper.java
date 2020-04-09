package com.ita.rank.dao;

import com.ita.rank.pojo.PlayerInfoPojo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PlayerInfoMapper {

    String TABLE_NAME = "player_info";

    @Insert({
        "<script>",
        "insert into " + TABLE_NAME + "(player_name) values ",
        "<foreach collection='playerList' item='item' index='index' separator=','>",
        "(#{item.playerName})",
        "</foreach>",
        "</script>"
    })
    int insertBatch(@Param(value="playerList") List<PlayerInfoPojo> playerList);

    @Select("select * from " + TABLE_NAME + " where player_id = #{playerId}")
    PlayerInfoPojo selectByPlayerId(@Param("playerId") int playerId);

    @Select("select * from " + TABLE_NAME + " where binary player_name like concat(#{playerName}, '%')")
    List<PlayerInfoPojo> selectByPlayerName(@Param("playerName") String playerName);

    @Select("select player_id from " + TABLE_NAME)
    List<Integer> selectPlayerIdList();

    @Select("select * from " + TABLE_NAME)
    List<PlayerInfoPojo> selectAll();

    @Update("update " + TABLE_NAME +
            " set live_search_count = #{playerInfoPojo.liveSearchCount} where player_id = #{playerInfoPojo.playerId}")
    void updateLiveSearchCount(@Param("playerInfoPojo") PlayerInfoPojo playerInfoPojo);

    @Update("update " + TABLE_NAME +
            " set pre_search_count = #{playerInfoPojo.preSearchCount} where player_id = #{playerInfoPojo.playerId}")
    void updatePreSearchCount(@Param("playerInfoPojo") PlayerInfoPojo playerInfoPojo);

    @Select("select * from " + TABLE_NAME + " where live_search_count > 0 order by live_search_count DESC")
    List<PlayerInfoPojo> selectOrderByLiveSearchCount();
}