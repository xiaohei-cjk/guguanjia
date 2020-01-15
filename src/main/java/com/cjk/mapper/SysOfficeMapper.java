package com.cjk.mapper;

import com.cjk.entity.SysOffice;
import org.apache.ibatis.annotations.*;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface SysOfficeMapper extends Mapper<SysOffice> {

    @SelectProvider(type = SysOfficeProvider.class,method = "selectByCondition")
    List<SysOffice> selectByCondition(Map<String, Object> params);

    //关联映射
    @Select("select so.*,sa.name areaName from  " +
            " sys_office so,sys_area sa " +
            " where " +
            " so.id=#{oid} " +
            " and " +
            " so.del_flag=0 " +
            " and " +
            " so.area_id=sa.id ")
    @Results({
            @Result(column = "id",property = "id"),
            @Result(column = "areaName",property = "areaName"),
            @Result(column = "id",property = "wastes",many = @Many(select = "com.cjk.mapper.WasteMapper.selectByOid"))
    })
    SysOffice selectByOid(long oid);


    @Delete("delete from office_waste where office_id=#{id}")
    int deleteOfficeWaste(long id);


    @InsertProvider(type = SysOfficeProvider.class,method = "insertBathOfficeWaste")
    int insertBathOfficeWaste(@Param("id") long id,@Param("wasteIds") long[] wasteIds);

    @Select("select * from sys_office where id=#{id}")
    SysOffice selectByUid(long id);

    @Select("select " +
            " sof.* " +
            "from " +
            " sys_role_office sro,sys_office sof " +
            "where " +
            " sro.role_id=#{rid} " +
            "and " +
            " sro.office_id=sof.id")
    List<SysOffice> selectByRid(long rid);
}