package com.cjk.service.impl;

import com.cjk.entity.SysUser;
import com.cjk.mapper.SysUserMapper;
import com.cjk.service.SysUserService;
import com.cjk.utils.EncryptUtils;
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
public class SysUserServiceImpl extends IServiceImpl<SysUser> implements SysUserService {


    @Autowired
    SysUserMapper userMapper;


    @Override
    public PageInfo<SysUser> selectByCondition(Map<String, Object> params) {
        //默认值设置
        if(StringUtils.isEmpty(params.get("pageNum"))){
            params.put("pageNum",1);
        }
        if(StringUtils.isEmpty(params.get("pageSize"))){
            params.put("pageSize",5);
        }
        PageHelper.startPage((Integer) params.get("pageNum"),(Integer) params.get("pageSize"));
//        SysUserMapper sysUserMapper= (SysUserMapper) mapper;
        List<SysUser> sysUsers = userMapper.selectByCondition(params);
        PageInfo<SysUser> pageInfo = new PageInfo<>(sysUsers);//生成分页对象

        return pageInfo;
    }

    @Override
    public SysUser selectOneByCondition(long uid){
//        SysUserMapper sysUserMapper= (SysUserMapper) mapper;
        SysUser sysUser = userMapper.selectOneByCondition(uid);
        sysUser.setPassword(null);
        return sysUser;
    }

    @Override
    public SysUser checkSysUser(SysUser record) {
        record.setPassword(EncryptUtils.MD5_HEX(EncryptUtils.MD5_HEX(record.getPassword())+record.getUsername()));
        List<SysUser> select = userMapper.select(record);
        if (select.size()>0){
            return select.get(0);
        }
        return null;
    }

    @Override
    public List<SysUser> selectByRid(long rid){
        return userMapper.selectByRid(rid);
    }

    @Override
    public List<SysUser> selectNoRole(long rid, long oid){
        return userMapper.selectNoRole(rid,oid);
    }
}
