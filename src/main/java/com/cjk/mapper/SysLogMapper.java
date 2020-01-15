package com.cjk.mapper;

import com.cjk.entity.SysLog;
import org.apache.ibatis.annotations.SelectProvider;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface SysLogMapper extends Mapper<SysLog> {

    @SelectProvider(type = SysLogProvider.class,method ="selectByCondition" )
    List<SysLog> selectByCondition(Map<String, Object> params);
}