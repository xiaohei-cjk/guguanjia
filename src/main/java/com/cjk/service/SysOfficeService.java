package com.cjk.service;


import com.cjk.entity.SysOffice;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface SysOfficeService extends IService<SysOffice> {


    PageInfo<SysOffice> selectByCondition(Map<String, Object> params);

    SysOffice selectByOid(long oid);

    int update(SysOffice sysOffice);

    List<SysOffice> selectByRid(long rid);
}
