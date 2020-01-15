package com.cjk.service;

import com.cjk.entity.SysLog;
import com.github.pagehelper.PageInfo;

import java.util.Map;

public interface SysLogService extends  IService<SysLog>{
    PageInfo<SysLog> selectByCondition(Map<String, Object> params);
}
