package com.ita.rank.dao;

import com.ita.rank.pojo.EventLevelPojo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface EventLevelMapper {

    String TABLE_NAME = "event_level";

    @Select("select * from " + TABLE_NAME + " where code = #{code}")
    EventLevelPojo selectByCode(@Param("code") String code);

    @Select("select code from " + TABLE_NAME)
    List<String> selectAllCode();
}