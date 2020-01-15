package com.cjk.service;

import com.cjk.entity.SysUser;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface SysUserService extends IService<SysUser> {


    PageInfo<SysUser> selectByCondition(Map<String, Object> params);


    SysUser selectOneByCondition(long uid);

    SysUser checkSysUser(SysUser record);

    List<SysUser> selectByRid(long rid);

    List<SysUser> selectNoRole(long rid, long oid);
}
