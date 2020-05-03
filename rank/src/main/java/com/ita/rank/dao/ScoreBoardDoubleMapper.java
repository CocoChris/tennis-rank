package com.ita.rank.dao;

import com.ita.rank.pojo.ScoreBoardDoublePojo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ScoreBoardDoubleMapper {

    String TABLE_NAME = "score_board_double";

    @Select("select * from " + TABLE_NAME + " where player_1_id_a = #{playerId} or player_1_id_b = #{playerId} " +
        "or player_2_id_a = #{playerId} or player_2_id_b = #{playerId}")
    List<ScoreBoardDoublePojo> selectByPlayerId(@Param("playerId") int playerId);

    @Select("select * from " + TABLE_NAME + " where (player_1_id_a = #{playerId} or player_1_id_b = #{playerId} " +
        "or player_2_id_a = #{playerId} or player_2_id_b = #{playerId}) and week = #{week} and season = #{season}")
    List<ScoreBoardDoublePojo> selectByPlayerIdAndPhase(@Param("playerId") int playerId,
                                                  @Param("week") int week,
                                                  @Param("season") int season);

    @Select("select * from " + TABLE_NAME + " where (player_1_id_a = #{playerId} or player_1_id_b = #{playerId} " +
        "or player_2_id_a = #{playerId} or player_2_id_b = #{playerId}) and wo = #{wo}")
    List<ScoreBoardDoublePojo> selectByPlayerIdWithoutWO(@Param("playerId") int playerId,
                                                   @Param("wo") int wo);

    @Select("select * from " + TABLE_NAME + " where (player_1_id_a = #{playerId} or player_1_id_b = #{playerId} " +
        "or player_2_id_a = #{playerId} or player_2_id_b = #{playerId}) and week = #{week} and season = #{season} and wo = #{wo}")
    List<ScoreBoardDoublePojo> selectByPlayerIdAndPhaseWithoutWO(@Param("playerId") int playerId,
                                                           @Param("week") int week,
                                                           @Param("season") int season,
                                                           @Param("wo") int wo);

    @Select("select * from " + TABLE_NAME + " where (player_1_id_a = #{playerId} or player_1_id_b = #{playerId}) and wo = #{wo}")
    List<ScoreBoardDoublePojo> selectAsPlayer1WithoutWO(@Param("playerId") int playerId,
                                                  @Param("wo") int wo);

    @Select("select * from " + TABLE_NAME + " where (player_2_id_a = #{playerId} or player_2_id_b = #{playerId}) and wo = #{wo}")
    List<ScoreBoardDoublePojo> selectAsPlayer2WithoutWO(@Param("playerId") int playerId,
                                                  @Param("wo") int wo);

    @Select("select * from " + TABLE_NAME + " where (player_1_id_a = #{playerId} or player_1_id_b = #{playerId}) and season = #{season} and wo = #{wo}")
    List<ScoreBoardDoublePojo> selectAsPlayer1BySeasonWithoutWO(@Param("playerId") int playerId,
                                                          @Param("season") int season,
                                                          @Param("wo") int wo);

    @Select("select * from " + TABLE_NAME + " where (player_2_id_a = #{playerId} or player_2_id_b = #{playerId}) and season = #{season} and wo = #{wo}")
    List<ScoreBoardDoublePojo> selectAsPlayer2BySeasonWithoutWO(@Param("playerId") int playerId,
                                                          @Param("season") int season,
                                                          @Param("wo") int wo);

    @Select("select * from " + TABLE_NAME + " where (player_1_id_a = #{playerId} or player_1_id_b = #{playerId} " +
        "or player_2_id_a = #{playerId} or player_2_id_b = #{playerId}) and event_id = #{eventId}")
    List<ScoreBoardDoublePojo> selectByPlayerIdAndEventId(@Param("playerId") int playerId,
                                                    @Param("eventId") int eventId);

    @Select("select * from " + TABLE_NAME + " where (player_1_id_a = #{playerId} or player_1_id_b = #{playerId} " +
        "or player_2_id_a = #{playerId} or player_2_id_b = #{playerId}) and event_id = #{eventId} and wo = #{wo}")
    List<ScoreBoardDoublePojo> selectByPlayerIdAndEventIdWithoutWO(@Param("playerId") int playerId,
                                                             @Param("eventId") int eventId,
                                                             @Param("wo") int wo);

    @Select("select * from " + TABLE_NAME + " where " +
        "(player_1_id_a = #{player1Id} and player_2_id_a = #{player2Id}) or " +
        "(player_1_id_b = #{player1Id} and player_2_id_a = #{player2Id}) or " +
        "(player_1_id_a = #{player1Id} and player_2_id_b = #{player2Id}) or " +
        "(player_1_id_b = #{player1Id} and player_2_id_b = #{player2Id}) or " +
        "(player_1_id_a = #{player2Id} and player_2_id_a = #{player1Id}) or " +
        "(player_1_id_b = #{player2Id} and player_2_id_a = #{player1Id}) or " +
        "(player_1_id_a = #{player2Id} and player_2_id_b = #{player1Id}) or " +
        "(player_1_id_b = #{player2Id} and player_2_id_b = #{player1Id})")
    List<ScoreBoardDoublePojo> selectOfRivals(@Param("player1Id") int player1Id,
                                              @Param("player2Id") int player2Id);

//    @Select("select * from " + TABLE_NAME + " where (player_1_id = #{playerId} and player_2_rank <= #{topN}) " +
//            "or (player_2_id = #{playerId} and player_1_rank <= #{topN})")
//    List<ScoreBoardDoublePojo> selectVsTopNOfCareer(@Param("playerId") int playerId,
//                                              @Param("topN") int topN);
//
//    @Select("select * from " + TABLE_NAME + " where wo = #{wo} and ((player_1_id = #{playerId} and player_2_rank <= #{topN}) " +
//            "or (player_2_id = #{playerId} and player_1_rank <= #{topN}))")
//    List<ScoreBoardDoublePojo> selectVsTopNOfCareerWithoutWO(@Param("playerId") int playerId,
//                                                       @Param("topN") int topN,
//                                                       @Param("wo") int wo);

    @Insert("insert into " + TABLE_NAME +
            " (event_id, event_name, round, week, season, " +
            "player_1_id_a, player_1_name_a, player_1_rank_a, player_1_id_b, player_1_name_b, player_1_rank_b, player_1_score, " +
            "player_2_id_a, player_2_name_a, player_2_rank_a, player_2_id_b, player_2_name_b, player_2_rank_b, player_2_score, " +
            "wo, ret, event_level, level_code, match_mode, handle) " +
            "values (#{scoreBoardDoublePojo.eventId, jdbcType=INTEGER}, #{scoreBoardDoublePojo.eventName, jdbcType=VARCHAR}, #{scoreBoardDoublePojo.round, jdbcType=VARCHAR}, " +
            "#{scoreBoardDoublePojo.week, jdbcType=INTEGER}, #{scoreBoardDoublePojo.season, jdbcType=INTEGER}, " +
            "#{scoreBoardDoublePojo.player1IdA, jdbcType=INTEGER}, #{scoreBoardDoublePojo.player1NameA, jdbcType=VARCHAR}, #{scoreBoardDoublePojo.player1RankA, jdbcType=INTEGER}, " +
            "#{scoreBoardDoublePojo.player1IdB, jdbcType=INTEGER}, #{scoreBoardDoublePojo.player1NameB, jdbcType=VARCHAR}, #{scoreBoardDoublePojo.player1RankB, jdbcType=INTEGER}, " +
            "#{scoreBoardDoublePojo.player1Score, jdbcType=INTEGER}, " +
            "#{scoreBoardDoublePojo.player2IdA, jdbcType=INTEGER}, #{scoreBoardDoublePojo.player2NameA, jdbcType=VARCHAR}, #{scoreBoardDoublePojo.player2RankA, jdbcType=INTEGER}, " +
            "#{scoreBoardDoublePojo.player2IdB, jdbcType=INTEGER}, #{scoreBoardDoublePojo.player2NameB, jdbcType=VARCHAR}, #{scoreBoardDoublePojo.player2RankB, jdbcType=INTEGER}, " +
            "#{scoreBoardDoublePojo.player2Score, jdbcType=INTEGER}, " +
            "#{scoreBoardDoublePojo.wo, jdbcType=INTEGER}, #{scoreBoardDoublePojo.ret, jdbcType=INTEGER}, #{scoreBoardDoublePojo.eventLevel, jdbcType=VARCHAR}, " +
            "#{scoreBoardDoublePojo.levelCode, jdbcType=VARCHAR}, #{scoreBoardDoublePojo.matchMode, jdbcType=INTEGER}, #{scoreBoardDoublePojo.handle, jdbcType=INTEGER})")
    int insert(@Param("scoreBoardDoublePojo") ScoreBoardDoublePojo scoreBoardDoublePojo);

    @Insert({
        "<script>",
        "insert into " + TABLE_NAME + " (event_id, event_name, round, week, season, " +
            "player_1_id_a, player_1_name_a, player_1_rank_a, player_1_id_b, player_1_name_b, player_1_rank_b, player_1_score, " +
            "player_2_id_a, player_2_name_a, player_2_rank_a, player_2_id_b, player_2_name_b, player_2_rank_b, player_2_score, " +
            "wo, ret, event_level, level_code, match_mode, handle) " +
            "values ",
        "<foreach collection='scoreBoardList' item='item' index='index' separator=','>",
        "(#{item.eventId, jdbcType=INTEGER},#{item.eventName, jdbcType=VARCHAR},#{item.round, jdbcType=VARCHAR}," +
            "#{item.week, jdbcType=INTEGER},#{item.season, jdbcType=INTEGER}," +
            "#{item.player1IdA, jdbcType=INTEGER},#{item.player1NameA, jdbcType=VARCHAR},#{item.player1RankA, jdbcType=INTEGER}," +
            "#{item.player1IdB, jdbcType=INTEGER},#{item.player1NameB, jdbcType=VARCHAR},#{item.player1RankB, jdbcType=INTEGER}," +
            "#{item.player1Score, jdbcType=INTEGER}," +
            "#{item.player2IdA, jdbcType=INTEGER},#{item.player2NameA, jdbcType=VARCHAR},#{item.player2RankA, jdbcType=INTEGER}," +
            "#{item.player2IdB, jdbcType=INTEGER},#{item.player2NameB, jdbcType=VARCHAR},#{item.player2RankB, jdbcType=INTEGER}," +
            "#{item.player2Score, jdbcType=INTEGER}," +
            "#{item.wo, jdbcType=INTEGER},#{item.ret, jdbcType=INTEGER},#{item.eventLevel, jdbcType=VARCHAR}," +
            "#{item.levelCode, jdbcType=VARCHAR},#{item.matchMode, jdbcType=INTEGER},#{item.handle, jdbcType=INTEGER})",
        "</foreach>",
        "</script>"
    })
    int insertBatch(@Param("scoreBoardList") List<ScoreBoardDoublePojo> scoreBoardList);

    @Select("select * from " + TABLE_NAME)
    List<ScoreBoardDoublePojo> selectAllScoreRecord();

    @Select("select * from " + TABLE_NAME + " where week = #{week} and season = #{season}")
    List<ScoreBoardDoublePojo> selectScoreRecordByPhase(@Param("week") int week,
                                                  @Param("season") int season);

    @Select("select * from " + TABLE_NAME + " where handle = #{unhandled}")
    List<ScoreBoardDoublePojo> selectUnhandled(@Param("unhandled") int unhandled);

    @Update("update " + TABLE_NAME + " set handle = #{handled} where id = #{scoreBoardDoublePojo.id}")
    void updateHandle(@Param("scoreBoardDoublePojo") ScoreBoardDoublePojo scoreBoardDoublePojo,
                      @Param("handled") int handled);

    @Update("update " + TABLE_NAME + " set player_1_rank_a = #{scoreBoardDoublePojo.player1RankA}, player_1_rank_b = #{scoreBoardDoublePojo.player1RankB}, " +
        "player_2_rank_a = #{scoreBoardDoublePojo.player2RankA}, player_2_rank_b = #{scoreBoardDoublePojo.player2RankB} " +
        "where id = #{scoreBoardDoublePojo.id}")
    void updateRank(@Param("scoreBoardDoublePojo") ScoreBoardDoublePojo scoreBoardDoublePojo);
}