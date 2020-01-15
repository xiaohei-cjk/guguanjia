package com.cjk.mapper;

import com.cjk.entity.SysRole;
import org.apache.ibatis.annotations.*;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface SysRoleMapper extends Mapper<SysRole> {

    @Select("select   " +
            "  sr.*  " +
            "from  " +
            "  sys_user su  " +
            "LEFT JOIN  " +
            "  sys_user_role sur  " +
            "ON  " +
            "  su.id=sur.user_id  " +
            "LEFT JOIN  " +
            "  sys_role sr  " +
            "ON  " +
            "  sr.id=sur.role_id  " +
            "where  " +
            "  su.id=#{uid}  ")
    List<SysRole> selectRoleByUid(long uid);


    @SelectProvider(type = SysRoleProvider.class,method = "selectByCondition")
    List<SysRole> selectByCondition(Map<String, Object> params);


    @Select("select " +
            " sr.*,so.name officeName " +
            "from " +
            " sys_role sr,sys_office so " +
            "where " +
            " sr.id=#{rid} " +
            "and " +
            " sr.office_id=so.id " )
    SysRole selectOneByCondition(long rid);


    /**
     * 根据角色rid和uids进行批量删除sys_user_role
     */
    @DeleteProvider(type = SysRoleProvider.class,method ="deleteBatch" )
    int deleteBatch(@Param("rid")long rid, @Param("uids")long[] uids);

    /**
     * 根据角色rids和cids进行批量插入sys_user_role
     */
    @InsertProvider(type = SysRoleProvider.class,method ="insertBatch")
    int insertBatch(@Param("rid")long rid, @Param("cids")long[] cids);


    @InsertProvider(type = SysRoleProvider.class,method = "insertRoleResource")
    int insertRoleResource(@Param("resIds") long[] resIds,@Param("rid")long rid);

    @InsertProvider(type = SysRoleProvider.class,method = "insertRoleOffice")
    int insertRoleOffice(@Param("oids") long[] oids,@Param("rid")long rid);

    @Delete("delete from sys_role_resource where role_id=#{rid}")
    int deleteRoleResource(long rid);

    @Delete("delete from sys_role_office where role_id=#{rid}")
    int deleteRoleOffice(long rid);
}