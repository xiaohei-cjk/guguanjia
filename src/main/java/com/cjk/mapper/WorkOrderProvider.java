package com.cjk.mapper;

import org.springframework.util.StringUtils;

import java.util.Map;

public class WorkOrderProvider {


    public String selectByCondition(Map<String,Object> params){
        StringBuilder sb = new StringBuilder();
        sb.append("select " +
                " wo.*,cu.name createUserName,tu.name transportUserName,ru.name recipientUserName,co.name createOfficeName " +
                "from " +
                " work_order wo        " +
                "LEFT JOIN " +
                " sys_user cu         " +
                "on " +
                " wo.create_user_id=cu.id " +
                "LEFT JOIN " +
                " sys_user tu         " +
                "on " +
                " wo.transport_user_id=tu.id " +
                "LEFT JOIN " +
                " sys_user ru           " +
                "on " +
                " wo.recipient_user_id=ru.id " +
                "LEFT JOIN " +
                " sys_office co       " +
                "on " +
                " cu.office_id=co.id " +
                "LEFT JOIN " +
                " sys_office tro       " +
                "on " +
                " tu.office_id=tro.id " +
                "LEFT JOIN " +
                " sys_office ro       " +
                "on " +
                " ru.office_id=ro.id " +
                "where " +
                " wo.del_flag=0 ");
        if(params.containsKey("status")&& !StringUtils.isEmpty(params.get("status"))){
            sb.append(" and wo.`status`=#{status} ");
        }

        if(params.containsKey("start")&& !StringUtils.isEmpty(params.get("start"))){
            sb.append(" and  wo.create_date>#{start}");
        }
        if(params.containsKey("end")&& !StringUtils.isEmpty(params.get("end"))){
            sb.append(" and wo.create_date<#{end} ");
        }
        if(params.containsKey("officeId")&& !StringUtils.isEmpty(params.get("officeId"))){
            sb.append(" and (co.id=#{officeId}  or    tro.id=#{officeId}  or   ro.id=#{officeId}  ) ");
        }
        System.out.println(sb.toString());
        return sb.toString();
    }
}
