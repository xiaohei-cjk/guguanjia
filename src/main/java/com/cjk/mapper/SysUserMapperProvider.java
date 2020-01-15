package com.cjk.mapper;

import org.springframework.util.StringUtils;

import java.util.Map;

public class SysUserMapperProvider {


    /**
     * 根据条件，动态生成sql语句
     * @param params
     * @return
     */
    public String selectByCondition(Map<String,Object> params){
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT " +
                " su.*,so.name officeName " +
                "FROM " +
                " sys_user su " +
                "LEFT JOIN " +
                " sys_office so " +
                "ON " +
                " su.office_id=so.id "
               );

        if(params.containsKey("rid")&&!StringUtils.isEmpty(params.get("rid"))){
            sb.append(" LEFT JOIN " +
                    " sys_user_role sur " +
                    " ON " +
                    " sur.user_id=su.id " +
                    " LEFT JOIN " +
                    " sys_role sr " +
                    " ON " +
                    " sur.role_id=sr.id " +
                    " where " +
                    " su.del_flag=0 ");
            sb.append(" and sr.id=#{rid} ");
        }else{
            sb.append(" where su.del_flag=0  ");
        }
        if(params.containsKey("uid")&&!StringUtils.isEmpty(params.get("uid"))){
            sb.append(" and su.id=#{uid} ");
        }

        if(params.containsKey("oid")&&!StringUtils.isEmpty(params.get("oid"))){
            sb.append(" and so.id=#{oid} ");
        }

        if(params.containsKey("name")&&!StringUtils.isEmpty(params.get("name"))){
            sb.append(" AND su.name like CONCAT('%',#{name},'%') ");
        }
        return sb.toString();
    }
}
