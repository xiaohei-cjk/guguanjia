package com.cjk.service.impl;

import com.cjk.entity.SysLog;
import com.cjk.mapper.SysLogMapper;
import com.cjk.service.SysLogService;
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
public class SysLogServiceImpl extends IServiceImpl<SysLog> implements SysLogService {


    @Autowired
    SysLogMapper roleMapper;


    @Override
    public PageInfo<SysLog> selectByCondition(Map<String, Object> params) {
        //默认值设置
        if(StringUtils.isEmpty(params.get("pageNum"))){
            params.put("pageNum",1);
        }
        if(StringUtils.isEmpty(params.get("pageSize"))){
            params.put("pageSize",10);
        }
        PageHelper.startPage((Integer) params.get("pageNum"),(Integer) params.get("pageSize"));

        List<SysLog> sysLogs = roleMapper.selectByCondition(params);
        PageInfo<SysLog> pageInfo = new PageInfo<>(sysLogs);//生成分页对象

        return pageInfo;
    }


}
