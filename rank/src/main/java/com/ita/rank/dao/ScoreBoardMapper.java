package com.ita.rank.dao;

import com.ita.rank.pojo.ScoreBoardPojo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ScoreBoardMapper {

    String TABLE_NAME = "score_board";

    @Select("select * from " + TABLE_NAME + " where player_1_id = #{playerId} or player_2_id = #{playerId}")
    List<ScoreBoardPojo> selectByPlayerId(@Param("playerId") int playerId);

    @Select("select * from " + TABLE_NAME + " where (player_1_id = #{playerId} or player_2_id = #{playerId}) and week = #{week} and season = #{season}")
    List<ScoreBoardPojo> selectByPlayerIdAndPhase(@Param("playerId") int playerId,
                                                  @Param("week") int week,
                                                  @Param("season") int season);

    @Select("select * from " + TABLE_NAME + " where player_1_id = #{playerId} or player_2_id = #{playerId} and ret = #{ret}")
    List<ScoreBoardPojo> selectByPlayerIdWithoutRet(@Param("playerId") int playerId,
                                                    @Param("ret") int ret);

    @Select("select * from " + TABLE_NAME + " where (player_1_id = #{playerId} or player_2_id = #{playerId}) and week = #{week} and season = #{season} and ret = #{ret}")
    List<ScoreBoardPojo> selectByPlayerIdAndPhaseWithoutRet(@Param("playerId") int playerId,
                                                            @Param("week") int week,
                                                            @Param("season") int season,
                                                            @Param("ret") int ret);

    @Select("select * from " + TABLE_NAME + " where player_1_id = #{playerId} and ret = #{ret}")
    List<ScoreBoardPojo> selectAsPlayer1WithoutRet(@Param("playerId") int playerId,
                                                   @Param("ret") int ret);

    @Select("select * from " + TABLE_NAME + " where player_2_id = #{playerId} and ret = #{ret}")
    List<ScoreBoardPojo> selectAsPlayer2WithoutRet(@Param("playerId") int playerId,
                                                   @Param("ret") int ret);

    @Select("select * from " + TABLE_NAME + " where player_1_id = #{playerId} and season = #{season} and ret = #{ret}")
    List<ScoreBoardPojo> selectAsPlayer1BySeasonWithoutRet(@Param("playerId") int playerId,
                                                           @Param("season") int season,
                                                           @Param("ret") int ret);

    @Select("select * from " + TABLE_NAME + " where player_2_id = #{playerId} and season = #{season} and ret = #{ret}")
    List<ScoreBoardPojo> selectAsPlayer2BySeasonWithoutRet(@Param("playerId") int playerId,
                                                           @Param("season")int season,
                                                           @Param("ret") int ret);

    @Select("select * from " + TABLE_NAME + " where (player_1_id = #{playerId} or player_2_id = #{playerId}) and event_id = #{eventId}")
    List<ScoreBoardPojo> selectByPlayerIdAndEventId(@Param("playerId") int playerId,
                                                    @Param("eventId") int eventId);

    @Select("select * from " + TABLE_NAME + " where (player_1_id = #{playerId} or player_2_id = #{playerId}) and event_id = #{eventId} and ret = #{ret}")
    List<ScoreBoardPojo> selectByPlayerIdAndEventIdWithoutRet(@Param("playerId") int playerId,
                                                              @Param("eventId") int eventId,
                                                              @Param("ret") int ret);

    @Select("select * from " + TABLE_NAME + " where (player_1_id = #{player1Id} and player_2_id = #{player2Id}) or (player_1_id = #{player2Id} and player_2_id = #{player1Id})")
    List<ScoreBoardPojo> selectOfRivals(@Param("player1Id") int player1Id,
                                        @Param("player2Id") int player2Id);

    @Select("select * from " + TABLE_NAME + " where (player_1_id = #{playerId} and player_2_rank <= #{topN}) " +
            "or (player_2_id = #{playerId} and player_1_rank <= #{topN})")
    List<ScoreBoardPojo> selectVsTopNOfCareer(@Param("playerId") int playerId,
                                              @Param("topN") int topN);

    @Select("select * from " + TABLE_NAME + " where ret = #{ret} and ((player_1_id = #{playerId} and player_2_rank <= #{topN}) " +
            "or (player_2_id = #{playerId} and player_1_rank <= #{topN}))")
    List<ScoreBoardPojo> selectVsTopNOfCareerWithoutRet(@Param("playerId") int playerId,
                                                        @Param("topN") int topN,
                                                        @Param("ret") int ret);

    @Insert("insert into " + TABLE_NAME +
            " (event_id, event_name, round, week, season, player_1_id, player_1_name, player_1_score, player_1_rank, " +
            "player_2_id, player_2_name, player_2_score, player_2_rank, wo, ret, event_level, level_code, match_mode, handle) " +
            "values (#{scoreBoardPojo.eventId, jdbcType=INTEGER}, #{scoreBoardPojo.eventName, jdbcType=VARCHAR}, #{scoreBoardPojo.round, jdbcType=VARCHAR}, " +
            "#{scoreBoardPojo.week, jdbcType=INTEGER}, #{scoreBoardPojo.season, jdbcType=INTEGER}, " +
            "#{scoreBoardPojo.player1Id, jdbcType=INTEGER}, #{scoreBoardPojo.player1Name, jdbcType=VARCHAR}, #{scoreBoardPojo.player1Score, jdbcType=INTEGER}, #{scoreBoardPojo.player1Rank, jdbcType=INTEGER}, " +
            "#{scoreBoardPojo.player2Id, jdbcType=INTEGER}, #{scoreBoardPojo.player2Name, jdbcType=VARCHAR}, #{scoreBoardPojo.player2Score, jdbcType=INTEGER}, #{scoreBoardPojo.player2Rank, jdbcType=INTEGER}, " +
            "#{scoreBoardPojo.ret, jdbcType=INTEGER}, #{scoreBoardPojo.ret, jdbcType=INTEGER}, #{scoreBoardPojo.eventLevel, jdbcType=VARCHAR}, #{scoreBoardPojo.levelCode, jdbcType=VARCHAR}, #{scoreBoardPojo.matchMode, jdbcType=INTEGER}, #{scoreBoardPojo.handle, jdbcType=INTEGER})")
    int insert(@Param("scoreBoardPojo") ScoreBoardPojo scoreBoardPojo);

    @Insert({
        "<script>",
        "insert into " + TABLE_NAME + " (event_id, event_name, round, week, season, player_1_id, player_1_name, player_1_score, player_1_rank, " +
            "player_2_id, player_2_name, player_2_score, player_2_rank, wo, ret, event_level, level_code, match_mode, handle) " +
            "values ",
        "<foreach collection='scoreBoardList' item='item' index='index' separator=','>",
        "(#{item.eventId, jdbcType=INTEGER},#{item.eventName, jdbcType=VARCHAR},#{item.round, jdbcType=VARCHAR}," +
            "#{item.week, jdbcType=INTEGER},#{item.season, jdbcType=INTEGER}," +
            "#{item.player1Id, jdbcType=INTEGER},#{item.player1Name, jdbcType=VARCHAR},#{item.player1Score, jdbcType=INTEGER},#{item.player1Rank, jdbcType=INTEGER}," +
            "#{item.player2Id, jdbcType=INTEGER},#{item.player2Name, jdbcType=VARCHAR},#{item.player2Score, jdbcType=INTEGER},#{item.player2Rank, jdbcType=INTEGER}," +
            "#{item.wo, jdbcType=INTEGER},#{item.ret, jdbcType=INTEGER},#{item.eventLevel, jdbcType=VARCHAR},#{item.levelCode, jdbcType=VARCHAR},#{item.matchMode, jdbcType=INTEGER},#{item.handle, jdbcType=INTEGER})",
        "</foreach>",
        "</script>"
    })
    int insertBatch(@Param("scoreBoardList") List<ScoreBoardPojo> scoreBoardList);

    @Select("select * from " + TABLE_NAME)
    List<ScoreBoardPojo> selectAllScoreRecord();

    @Select("select * from " + TABLE_NAME + " where week = #{week} and season = #{season}")
    List<ScoreBoardPojo> selectScoreRecordByPhase(@Param("week") int week,
                                                  @Param("season") int season);

    @Select("select * from " + TABLE_NAME + " where handle = #{unhandled}")
    List<ScoreBoardPojo> selectUnhandled(@Param("unhandled") int unhandled);

    @Update("update " + TABLE_NAME + " set handle = #{handled} where id = #{scoreBoardPojo.id}")
    void updateHandle(@Param("scoreBoardPojo") ScoreBoardPojo scoreBoardPojo,
                      @Param("handled") int handled);

    @Update("update " + TABLE_NAME + " set player_1_rank = #{scoreBoardPojo.player1Rank}, player_2_rank = #{scoreBoardPojo.player2Rank} " +
            "where id = #{scoreBoardPojo.id}")
    void updateRank(@Param("scoreBoardPojo") ScoreBoardPojo scoreBoardPojo);
}