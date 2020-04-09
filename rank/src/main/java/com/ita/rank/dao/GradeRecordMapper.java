package com.ita.rank.dao;

import com.ita.rank.pojo.GradeRecordPojo;
import com.ita.rank.pojo.PlayerInfoPojo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface GradeRecordMapper {

    String TABLE_NAME = "grade_record";

    @Insert({
        "<script>",
        "insert into " + TABLE_NAME + "(player_id, player_name, event_id, event_name, " +
            "level_code, event_level, grade, qualified, pts, week, season) values ",
        "<foreach collection='gradeRecordList' item='item' index='index' separator=','>",
        "(#{item.playerId}, #{item.playerName}, #{item.eventId}, #{item.eventName}, " +
            "#{item.levelCode}, #{item.eventLevel}, #{item.grade}, #{item.qualified}, " +
            "#{item.pts}, #{item.week}, #{item.season})",
        "</foreach>",
        "</script>"
    })
    int insertBatch(@Param(value="gradeRecordList") List<GradeRecordPojo> gradeRecordList);

    @Select("select * from " + TABLE_NAME + " where player_id = #{playerId} and event_id = #{eventId}")
    GradeRecordPojo selectByPlayerIdAndEventId(@Param("playerId") int playerId,
                                               @Param("eventId") int eventId);

    @Select("select * from " + TABLE_NAME + " where ((season = #{season} - 1 and week > #{week}) or (season = #{season} and week <= #{week})) and player_id = #{playerId}")
    List<GradeRecordPojo> selectGradeRecordListOfWholePeriod(@Param("playerId") int playerId,
                                                             @Param("week") int week,
                                                             @Param("season") int season);

    @Select("select * from " + TABLE_NAME + " where season = #{season} and week <= #{week} and player_id = #{playerId}")
    List<GradeRecordPojo> selectGradeRecordListOfCurrentSeason(@Param("playerId") int playerId,
                                                               @Param("week") int week,
                                                               @Param("season") int season);

    @Select("select * from " + TABLE_NAME + " where player_id = #{playerId} and week = #{week} and season = #{season}")
    List<GradeRecordPojo> selectByPlayerIdAndPhase(@Param("playerId") int playerId,
                                                   @Param("week") int week,
                                                   @Param("season") int season);

    @Select("select count(*) from " + TABLE_NAME + " where player_id = #{playerId} and grade = 'W'")
    int selectNumOfTitlesOfCareerByPlayerId(@Param("playerId") int playerId);

    @Select("select * from " + TABLE_NAME + " where grade = #{grade}")
    List<GradeRecordPojo> selectByGrade(@Param("grade") String grade);

    @Insert("insert into " + TABLE_NAME +
            " (player_id, player_name, event_id, event_name, level_code, event_level, grade, qualified, pts, week, season) " +
            "values (#{gradeRecordPojo.playerId, jdbcType=INTEGER}, #{gradeRecordPojo.playerName, jdbcType=VARCHAR}, " +
            "#{gradeRecordPojo.eventId, jdbcType=INTEGER}, #{gradeRecordPojo.eventName, jdbcType=VARCHAR}, " +
            "#{gradeRecordPojo.levelCode, jdbcType=VARCHAR}, #{gradeRecordPojo.eventLevel, jdbcType=VARCHAR}, " +
            "#{gradeRecordPojo.grade, jdbcType=VARCHAR}, #{gradeRecordPojo.qualified, jdbcType=INTEGER}, #{gradeRecordPojo.pts, jdbcType=INTEGER}, " +
            "#{gradeRecordPojo.week, jdbcType=INTEGER}, #{gradeRecordPojo.season, jdbcType=INTEGER})")
    int insert(@Param("gradeRecordPojo") GradeRecordPojo gradeRecordPojo);

    @Update("update " + TABLE_NAME +
            " set grade = #{gradeRecordPojo.grade}, qualified = #{gradeRecordPojo.qualified}, pts = #{gradeRecordPojo.pts} " +
            "where id = #{gradeRecordPojo.id}")
    void updateGrade(@Param("gradeRecordPojo") GradeRecordPojo gradeRecordPojo);
}