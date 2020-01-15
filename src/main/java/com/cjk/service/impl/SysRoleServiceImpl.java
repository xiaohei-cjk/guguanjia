package com.cjk.service.impl;

import com.cjk.entity.SysRole;
import com.cjk.mapper.SysRoleMapper;
import com.cjk.service.SysRoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class SysRoleServiceImpl extends IServiceImpl<SysRole> implements SysRoleService {


    @Autowired
    SysRoleMapper roleMapper;


    @Override
    public PageInfo<SysRole> selectByCondition(Map<String, Object> params) {
        //默认值设置
        if(StringUtils.isEmpty(params.get("pageNum"))){
            params.put("pageNum",1);
        }
        if(StringUtils.isEmpty(params.get("pageSize"))){
            params.put("pageSize",5);
        }
        PageHelper.startPage((Integer) params.get("pageNum"),(Integer) params.get("pageSize"));

        List<SysRole> sysUsers = roleMapper.selectByCondition(params);
        PageInfo<SysRole> pageInfo = new PageInfo<>(sysUsers);//生成分页对象

        return pageInfo;
    }

    @Override
    public SysRole selectOneByCondition(long id) {
        return roleMapper.selectOneByCondition(id);
    }


    @Override
    public int deleteBatch(long rid, long[] uids){
        return roleMapper.deleteBatch(rid,uids);
    }

    @Override
    public int insertBatch(long rid, long[] cids) {
        return roleMapper.insertBatch(rid,cids);
    }

    @Override
    public int update(Map<String, Object> params){
        long rid = (long) params.get("rid");
        long[] resIds = (long[]) params.get("resIds");
        long[] oids = (long[]) params.get("oids");
        SysRole sysRole = (SysRole) params.get("role");
        int result=0;
        result+=roleMapper.updateByPrimaryKey(sysRole);
        result+=roleMapper.deleteRoleResource(rid);
        result+=roleMapper.insertRoleResource(resIds,rid);
        result+=roleMapper.deleteRoleOffice(rid);
        result+=roleMapper.insertRoleOffice(oids,rid);
        return result;
    }
}
