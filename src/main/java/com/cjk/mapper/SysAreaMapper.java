package com.cjk.mapper;

import com.cjk.entity.SysArea;
import org.apache.ibatis.annotations.*;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface SysAreaMapper extends Mapper<SysArea> {
    @InsertProvider(type = SysAreaProvider.class,method = "insertBath")
    int insertBath(@Param("areas") List<SysArea> areas);



    /**
     * 根据父区域id查找所有区域
     * @return
     */
    @Select("select sub.*,parent.name parentName " +
            "from " +
            " sys_area sub,sys_area parent " +
            "where " +
            " sub.parent_ids like CONCAT('%',#{aid},'%') " +
            "and " +
            " sub.parent_id=parent.id")
    List<SysArea> selectByPid(long aid);

    @Select("select  " +
            " sub.*,parent.name parentName " +
            "from " +
            " sys_area sub,sys_area parent " +
            "where " +
            " sub.parent_id=parent.id " +
            "and " +
            " sub.id=#{id}")
    SysArea selectByAid(long id);

    /**
     * 根据父区域更新所有的子区域的parentIds
     * @param sysArea
     * @return
     */
    @Update("update " +
            " sys_area " +
            "set " +
            " parent_ids=REPLACE(parent_ids,#{oldParentIds},#{parentIds}) " +
            "where " +
            " parent_ids like concat('%,',#{id},',%') ")
    int updateParentIds(SysArea sysArea);

    @SelectProvider(type = SysAreaProvider.class,method = "selectAllByName")
    List<SysArea> selectAllByName(Map<String,Object> params);
}