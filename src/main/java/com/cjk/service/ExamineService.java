package com.cjk.service;

import com.cjk.entity.Examine;
import com.github.pagehelper.PageInfo;

import java.util.Map;

public interface ExamineService extends IService<Examine> {

//    List<AppVersion> selectAll();

    PageInfo<Examine> selectAll(Map<String, Object> params);
}
