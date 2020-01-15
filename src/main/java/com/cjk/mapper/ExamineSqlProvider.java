package com.cjk.mapper;

import org.springframework.util.StringUtils;

import java.util.Map;

public class ExamineSqlProvider {


     public String selectByCondition(Map<String,Object> condition){
        StringBuilder sb = new StringBuilder();
        sb.append("select ex.*,su.name userName,so.name officeName from  " +
                " examine ex , sys_user su , sys_office so  " +
                "where " +
                " ex.del_flag=0" +
                " and " +
                " ex.examine_user_id=su.id " +
                "and " +
                " su.office_id=so.id ");
        if(condition.containsKey("officeId")&&!StringUtils.isEmpty(condition.get("officeId"))&&Integer.valueOf(condition.get("officeId").toString())!=0){
            sb.append(" and so.id=#{officeId} ");
        }
        if(condition.containsKey("userName")&&!StringUtils.isEmpty(condition.get("userName"))){
            sb.append(" and su.name like concat('%',#{userName},'%') ");
        }
        if(condition.containsKey("type")&&!StringUtils.isEmpty(condition.get("type"))){
            sb.append(" and ex.type=#{type} ");
        }
        System.out.println(sb.toString());
        return sb.toString();
    }
}
