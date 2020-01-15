package com.cjk.mapper;

import com.cjk.entity.SysUser;
import org.apache.ibatis.annotations.*;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface SysUserMapper extends Mapper<SysUser> {


    /**
     * 根据用户id查询 用户信息和公司信息
     */
    @Select("select  " +
            " su.*,so.name officeName,so.id officeId " +
            "from " +
            " sys_user su,sys_office so " +
            "where " +
            " su.id=#{uid} " +
            "and " +
            " su.office_id=so.id")
    @Results({
            @Result(property = "id",column = "su.id"),
            @Result(property = "sysOffice.id",column = "officeId"),
            @Result(property = "sysOffice.name",column = "officeName")
    })
    SysUser selectById(long uid);


    @SelectProvider(type = SysUserMapperProvider.class,method = "selectByCondition")
    //设置关联查询，将用户id对应的所有的roles查询出来
    @Results({
            @Result(column = "id",property = "id"),
            @Result(property = "sysOffice",column = "id",one = @One(select = "com.cjk.mapper.SysOfficeMapper.selectByUid")),
            @Result(property = "roles",column = "id",many = @Many(select = "com.cjk.mapper.SysRoleMapper.selectRoleByUid"))
    })
    List<SysUser> selectByCondition(Map<String,Object> params);


    @Select("SELECT " +
            " su.*,so.name officeName " +
            "FROM " +
            " sys_user su " +
            "LEFT JOIN " +
            " sys_office so " +
            "ON " +
            " su.office_id=so.id " +
            "WHERE " +
            " su.id=#{uid}")
    //设置关联查询，将用户id对应的所有的roles查询出来
    @Results({
            @Result(column = "id",property = "id"),
            @Result(property = "roles",column = "id",many = @Many(select = "com.cjk.mapper.SysRoleMapper.selectRoleByUid"))
    })
    SysUser selectOneByCondition(long uid);

    /**
     * 根据角色id，查询用户信息
     * @param rid
     * @return
     */
    @Select("select " +
            " su.* " +
            "from " +
            " sys_role sr,sys_user_role sur,sys_user su " +
            "where " +
            " sr.id=#{rid} " +
            "and " +
            " sr.id=sur.role_id " +
            "and " +
            " sur.user_id=su.id")
    List<SysUser> selectByRid(long rid);

    @Select("select " +
            " * " +
            "from " +
            " sys_user " +
            "where " +
            " office_id=#{oid} " +
            "and " +
            " id  " +
            "not in " +
            "( " +
            "select " +
            " sur.user_id " +
            "from " +
            " sys_role sr,sys_user_role sur " +
            "where " +
            " sr.id=#{rid} " +
            "and " +
            " sr.id=sur.role_id " +
            ")")
    List<SysUser> selectNoRole(@Param("rid") long rid,@Param("oid")long oid);
}