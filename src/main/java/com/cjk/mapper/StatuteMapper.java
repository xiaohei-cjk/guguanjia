package com.cjk.mapper;

import com.cjk.entity.Statute;
import org.apache.ibatis.annotations.SelectProvider;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface StatuteMapper extends Mapper<Statute> {

    /**
     * 动态sql实现
     * @param
     * @return
     */
    @SelectProvider(type = StatuteSqlProvider.class,method = "selectByCondition")
    List<Statute> selectByCondition(Map<String, Object> condition);
}