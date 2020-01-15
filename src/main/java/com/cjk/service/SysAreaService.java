package com.cjk.service;


import com.cjk.entity.SysArea;
import com.github.pagehelper.PageInfo;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

public interface SysAreaService extends IService<SysArea> {
    OutputStream writeExcel(OutputStream outputStream);

    //    PageInfo<SysArea> selectByCondition(Map<String, Object> params);
    int readExcel(InputStream inputStream);

    PageInfo<SysArea> selectByPage(Map<String, Object> params);

    SysArea selectByAid(long id);

    int updateArea(SysArea sysArea);

    public PageInfo<SysArea> selectAllByName(Map<String,Object> params);
}
