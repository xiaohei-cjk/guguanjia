package com.cjk.mapper;

import org.springframework.util.StringUtils;

import java.util.Map;

public class StatuteSqlProvider {


    public String selectByCondition(Map<String,Object> condition){
        StringBuilder sb = new StringBuilder();
        sb.append("select * from  " +
                " statute s " +
                "where " +
                " s.del_flag=0 ");
        if(!StringUtils.isEmpty(condition.get("type"))){
            sb.append(" and s.type=#{type} ");
        }

        System.out.println(sb.toString());
        return sb.toString();
    }
}
