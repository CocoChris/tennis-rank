package com.ita.rank.dao;

import com.ita.rank.pojo.EventInfoPojo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import java.util.List;

@Mapper
public interface EventInfoMapper {

    String TABLE_NAME = "event_info";

    @Select("select * from " + TABLE_NAME + " where season = #{season} and week = #{week}")
    EventInfoPojo selectBySeasonAndWeek(@Param("season") int season,
                                        @Param("week") int week);

    @Select("select * from " + TABLE_NAME + " where season = #{season} and binary event_name like concat('%', #{eventName}, '%')")
    EventInfoPojo selectByEventName(@Param("eventName") String eventName,
                                    @Param("season") int season);

    @Select("select event_id from " + TABLE_NAME + " where event_level = #{eventLevel} and " +
            "((week <= #{week} and season = #{season}) or (week > #{week} and season = #{season} - 1))")
    List<Integer> selectEventIdOfSpecificEventLevelOfWholePeriod(@Param("eventLevel") String eventLevel,
                                                                 @Param("week") int week,
                                                                 @Param("season") int season);

    @Select("select event_id from " + TABLE_NAME + " where event_level = #{eventLevel} and season = #{season}")
    List<Integer> selectEventIdOfSpecificEventLevelOfCurrSeason(@Param("eventLevel") String eventLevel,
                                                                @Param("season") int season);
}