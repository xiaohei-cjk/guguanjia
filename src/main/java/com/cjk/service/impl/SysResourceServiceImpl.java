package com.cjk.service.impl;

import com.cjk.entity.SysResource;
import com.cjk.mapper.SysResourceMapper;
import com.cjk.service.SysResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SysResourceServiceImpl extends IServiceImpl<SysResource> implements SysResourceService {


    @Autowired
    SysResourceMapper resourceMapper;


    @Override
    public List<SysResource> selectByRid(long rid){
        return resourceMapper.selectByRid(rid);
    }


    /**
     * 根据用户id查询其所有资源，并且去重
     * @param uid
     * @return
     */
    @Override
    public List<SysResource> selectByUid(long uid) {
        List<SysResource> sysResources = resourceMapper.selectByUid(uid);
//        HashSet<SysResource> resourceSet = new HashSet<>();
//        resourceSet.addAll(sysResources);//必须重写equals才能保证去重
        return sysResources;
    }

    @Override
//    @Cacheable(cacheNames = "resourceCache",key = "'com.cjk.service.impl.SysResourceServiceImpl:selectAll'")
    public List<SysResource> selectAll() {
        return super.selectAll();
    }
}
