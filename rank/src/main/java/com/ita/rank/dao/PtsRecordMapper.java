package com.ita.rank.dao;

import com.ita.rank.pojo.PtsRecordPojo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PtsRecordMapper {
    
    String TABLE_NAME = "pts_record";

    @Select("select * from " + TABLE_NAME + " where player_id = #{playerId} and week = #{week} and season = #{season}")
    PtsRecordPojo selectByPlayerIdAndPhase(@Param("playerId") int playerId,
                                           @Param("week") int week,
                                           @Param("season") int season);

    @Select("select * from " + TABLE_NAME + " where week = #{week} and season = #{season}")
    List<PtsRecordPojo> selectByWeekAndSeason(@Param("week") int week,
                                              @Param("season") int season);

    @Select("select rank from " + TABLE_NAME + " where player_id = #{playerId} and week = #{week} - 1 and season = #{season}")
    Integer selectRankOfLastWeek(@Param("playerId") int playerId,
                                 @Param("week") int week,
                                 @Param("season") int season);

    @Select("select min(rank) from " + TABLE_NAME + " where player_id = #{playerId} and (week < #{week} or season < #{season}) and season > 1 and rank > 0")
    Integer selectHighestRank(@Param("playerId") int playerId,
                              @Param("week") int week,
                              @Param("season") int season);

    @Select("select * from " + TABLE_NAME + " where player_id = #{playerId} order by season asc, week asc")
    List<PtsRecordPojo> selectByPlayerId(@Param("playerId") int playerId);

    @Select("select * from " + TABLE_NAME + " where player_id = #{playerId} and season = #{season} order by season asc, week asc")
    List<PtsRecordPojo> selectByPlayerIdAndSeason(@Param("playerId") int playerId,
                                                  @Param("season") int season);

    @Update("update " + TABLE_NAME +
            " set total_pts = #{ptsRecordPojo.totalPts} " +
            "where id = #{ptsRecordPojo.id}")
    void updateTotalPts(@Param("ptsRecordPojo") PtsRecordPojo ptsRecordPojo);

    @Update("update " + TABLE_NAME +
            " set rank = #{ptsRecordPojo.rank} " +
            "where id = #{ptsRecordPojo.id}")
    void updateRank(@Param("ptsRecordPojo") PtsRecordPojo ptsRecordPojo);

    @Insert("insert into " + TABLE_NAME +
            " (player_id, player_name, total_pts, rank, week, season) " +
            "values (#{ptsRecordPojo.playerId, jdbcType=INTEGER}, #{ptsRecordPojo.playerName, jdbcType=VARCHAR}, " +
            "#{ptsRecordPojo.totalPts, jdbcType=INTEGER}, #{ptsRecordPojo.rank, jdbcType=INTEGER}, " +
            "#{ptsRecordPojo.week, jdbcType=INTEGER}, #{ptsRecordPojo.season, jdbcType=INTEGER})")
    int insert(@Param("ptsRecordPojo") PtsRecordPojo ptsRecordPojo);
    
}