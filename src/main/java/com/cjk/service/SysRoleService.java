package com.cjk.service;

import com.cjk.entity.SysRole;
import com.github.pagehelper.PageInfo;

import java.util.Map;

public interface SysRoleService extends IService<SysRole> {


    PageInfo<SysRole> selectByCondition(Map<String, Object> params);


    SysRole selectOneByCondition(long id);

    int deleteBatch(long rid, long[] uids);

    int insertBatch(long rid, long[] cids);

    int update(Map<String, Object> params);
}
