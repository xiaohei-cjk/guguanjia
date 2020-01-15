package com.cjk.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.util.StringUtils;

import java.util.Map;

public class SysOfficeProvider {

    public String selectByCondition(Map<String, Object> params){

            StringBuilder sb = new StringBuilder();
            sb.append("select so.*,sa.name areaName from  " +
                    " sys_office so,sys_area sa " +
                    "where " +
                    " so.del_flag=0 " +
                    "and " +
                    " so.area_id=sa.id ");
            if(params.containsKey("name")&&!StringUtils.isEmpty(params.get("name"))){
                sb.append(" and so.name=#{name} ");
            }

            System.out.println(sb.toString());
            return sb.toString();
    }

    public String insertBathOfficeWaste(@Param("id") long id, @Param("wasteIds") long[] wasteIds){
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO `guguanjia`.`office_waste`(`waste_id`, `office_id`, `del_flag`, `create_date`, `update_date`, `create_by`) VALUES ");
        for (int i = 0; i < wasteIds.length; i++) {
            sb.append("(");
            sb.append("#{wasteIds["+i+"]},#{id},'0',now(),now(),null");
            sb.append("),");
        }
        sb.deleteCharAt(sb.length()-1);
        return sb.toString();

    }
}
