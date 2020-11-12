package com.ita.rank.dao;

import com.ita.rank.pojo.PtsRecordChampionPojo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PtsRecordChampionMapper {
    
    String TABLE_NAME = "pts_record_champion";

    @Select("select * from " + TABLE_NAME + " where player_id = #{playerId} and week = #{week} and season = #{season}")
    PtsRecordChampionPojo selectByPlayerIdAndPhase(@Param("playerId") int playerId,
                                                   @Param("week") int week,
                                                   @Param("season") int season);

    @Select("select * from " + TABLE_NAME + " where player_id = #{playerId} and week = #{week} and season = #{season}")
    PtsRecordChampionPojo selectByPlayerId(@Param("playerId") int playerId,
                                           @Param("week") int week,
                                           @Param("season") int season);

    @Select("select * from " + TABLE_NAME + " where week = #{week} and season = #{season}")
    List<PtsRecordChampionPojo> selectByWeekAndSeason(@Param("week") int week,
                                                      @Param("season") int season);

    @Select("select rank from " + TABLE_NAME + " where player_id = #{playerId} and week = #{week} - 1 and season = #{season}")
    Integer selectRankOfLastWeek(@Param("playerId") int playerId,
                                 @Param("week") int week,
                                 @Param("season") int season);

    @Update("update " + TABLE_NAME +
            " set total_pts = #{ptsRecordChampionPojo.totalPts} " +
            "where id = #{ptsRecordChampionPojo.id}")
    void updateTotalPts(@Param("ptsRecordChampionPojo") PtsRecordChampionPojo ptsRecordChampionPojo);

    @Update("update " + TABLE_NAME +
            " set rank = #{ptsRecordChampionPojo.rank} " +
            "where id = #{ptsRecordChampionPojo.id}")
    void updateRank(@Param("ptsRecordChampionPojo") PtsRecordChampionPojo ptsRecordChampionPojo);

    @Insert("insert into " + TABLE_NAME +
            " (player_id, player_name, total_pts, rank, week, season) " +
            "values (#{ptsRecordChampionPojo.playerId, jdbcType=INTEGER}, #{ptsRecordChampionPojo.playerName, jdbcType=VARCHAR}, " +
            "#{ptsRecordChampionPojo.totalPts, jdbcType=INTEGER}, #{ptsRecordChampionPojo.rank, jdbcType=INTEGER}, " +
            "#{ptsRecordChampionPojo.week, jdbcType=INTEGER}, #{ptsRecordChampionPojo.season, jdbcType=INTEGER})")
    int insert(@Param("ptsRecordChampionPojo") PtsRecordChampionPojo ptsRecordChampionPojo);
}