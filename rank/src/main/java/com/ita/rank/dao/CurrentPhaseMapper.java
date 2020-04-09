package com.ita.rank.dao;

import com.ita.rank.pojo.CurrentPhasePojo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface CurrentPhaseMapper {

    String TABLE_NAME = "current_phase";

    @Select("select * from " + TABLE_NAME + " where id = 1")
    CurrentPhasePojo selectCurrentPhase();

    @Update("update " + TABLE_NAME + " set current_week = #{week} where id = 1")
    void updateWeek(@Param("week") int week);

    @Update("update " + TABLE_NAME + " set current_season = #{season} where id = 1")
    void updateSeason(@Param("season") int season);
}