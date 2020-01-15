package com.cjk.mapper;

import org.springframework.util.StringUtils;

import java.util.Map;

public class QualificationProvider {


    /**
     *
     * @param params  start:'' ,  end:'' ,type:'', check:''
     * @return
     */
    public String selectByCondition(Map<String,Object> params){
        System.out.println(params);
        StringBuilder sb = new StringBuilder();
        sb.append("select qu.*,uu.name uploadUser,cu.name checkUser from  qualification qu LEFT JOIN  sys_user uu on  qu.upload_user_id=uu.id " +
                "LEFT JOIN  sys_user cu on  qu.check_user_id=cu.id where  qu.del_flag=0 ");
        if(params.containsKey("type")&& !StringUtils.isEmpty(params.get("type"))){
            sb.append(" and type=#{type}");
        }
        if(params.containsKey("check")&&!StringUtils.isEmpty(params.get("check"))){
            sb.append(" and `check`=#{check}");
        }
        if(params.containsKey("start")&&!StringUtils.isEmpty(params.get("start"))){
            sb.append(" and qu.create_date>#{start} ");
        }
        if(params.containsKey("end")&&!StringUtils.isEmpty(params.get("end"))){
            sb.append(" and qu.create_date<#{end} ");
        }
        return sb.toString();
    }
}
