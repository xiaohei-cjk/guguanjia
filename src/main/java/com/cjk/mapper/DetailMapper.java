package com.cjk.mapper;

import com.cjk.entity.Detail;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface DetailMapper extends Mapper<Detail> {

    @Select("select " +
            " de.*,wt.name wasteTypeName,wt.code wasteTypeCode,wa.code wasteCode " +
            "from " +
            " detail de,waste_type  wt,waste wa " +
            "where " +
            " de.work_order_id=#{oid} " +
            "and " +
            " de.del_flag=0 " +
            "and " +
            " de.waste_type_id=wt.id " +
            "and " +
            " de.waste_id=wa.id")
    List<Detail> selectByOid(long oid);
}