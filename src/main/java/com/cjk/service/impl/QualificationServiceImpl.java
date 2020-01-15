package com.cjk.service.impl;

import com.cjk.entity.Qualification;
import com.cjk.mapper.QualificationMapper;
import com.cjk.service.QualificationService;
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
public class QualificationServiceImpl extends IServiceImpl<Qualification> implements QualificationService {

    @Autowired
    QualificationMapper qualificationMapper;

    @Override
    public PageInfo<Qualification> selectByPage(Map<String, Object> params){
        //对pageNum、pageSize进行判断   给默认值
        if(!params.containsKey("pageNum")|| StringUtils.isEmpty(params.get("pageNum"))){
            params.put("pageNum",1);
        }

        if(!params.containsKey("pageSize")|| StringUtils.isEmpty(params.get("pageSize"))){
            params.put("pageSize",5);
        }

        //开启分页拦截  分页插件会自动在最近一个sql执行前，自动添加分页的sql代码 limit x,x
        PageHelper.startPage((int)params.get("pageNum"),(int)params.get("pageSize"));
        List<Qualification> list = qualificationMapper.selectByCondition(params);
        return new PageInfo<>(list);

    }

    @Override
    public long selectOfficeId(long qid){
        return qualificationMapper.selectOfficeId(qid);
    }

}
