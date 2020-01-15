package com.cjk.service.impl;

import com.cjk.entity.Examine;
import com.cjk.mapper.ExamineMapper;
import com.cjk.service.ExamineService;
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
public class ExamineServiceImpl extends IServiceImpl<Examine> implements ExamineService {

    @Autowired
    ExamineMapper examineMapper;

    @Override
    public PageInfo<Examine> selectAll(Map<String, Object> params) {
        //默认值设置
        if(StringUtils.isEmpty(params.get("pageNum"))){
            params.put("pageNum",1);
        }
        if(StringUtils.isEmpty(params.get("pageSize"))){
            params.put("pageSize",5);
        }
        PageHelper.startPage((Integer) params.get("pageNum"),(Integer) params.get("pageSize"));

        List<Examine> examines = examineMapper.selectByCondition(params);

        PageInfo<Examine> pageInfo = new PageInfo<>(examines);//生成分页对象

        return pageInfo;
    }



}
