package com.cjk.mapper;

import com.cjk.entity.SysArea;
import org.apache.ibatis.annotations.Param;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

public class SysAreaProvider {


    /**
     * 批量插入
     */
    public String insertBath(@Param("areas") List<SysArea> areas){
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO `guguanjia`.`sys_area`( `parent_id`, `parent_ids`, `code`, `name`, `type`, `create_by`, " +
                "`create_date`, `update_by`, `update_date`, `remarks`, `del_flag`, `icon`) VALUES ");
        for (int i = 0; i < areas.size(); i++) {
            sb.append("(");

            sb.append("#{areas["+i+"].parentId}, #{areas["+i+"].parentIds}, #{areas["+i+"].code}, #{areas["+i+"].name}, " +
                    "#{areas["+i+"].type}, #{areas["+i+"].createBy}, #{areas["+i+"].createDate}, #{areas["+i+"].updateBy}, #{areas["+i+"].updateDate}, #{areas["+i+"].remarks}, #{areas["+i+"].delFlag}, #{areas["+i+"].icon}");

            sb.append("),");
        }
        sb.deleteCharAt(sb.length()-1);
        return sb.toString();

    }

    public String selectAllByName(@Param("name") Map<String,Object> params) {
        StringBuilder sb = new StringBuilder();
        sb.append("select sa.*,sp.name parentName FROM sys_area sa LEFT JOIN sys_area sp on sa.parent_id=sp.id WHERE 1=1 ");
        if(params.containsKey("name")&&!StringUtils.isEmpty(params.get("name"))&&params.get("name").toString().trim().length()>0){
            sb.append(" and sa.name like '%"+params.get("name").toString().trim()+"%'");
        }
        return sb.toString();
    }
}
