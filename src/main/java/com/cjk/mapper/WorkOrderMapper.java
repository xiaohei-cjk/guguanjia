package com.cjk.mapper;

import com.cjk.entity.WorkOrder;
import org.apache.ibatis.annotations.SelectProvider;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface WorkOrderMapper extends Mapper<WorkOrder> {

    @SelectProvider(type = WorkOrderProvider.class,method ="selectByCondition" )
    List<WorkOrder> selectByCondition(Map<String, Object> params);
}