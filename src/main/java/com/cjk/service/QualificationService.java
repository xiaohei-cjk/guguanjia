package com.cjk.service;

import com.cjk.entity.Qualification;
import com.github.pagehelper.PageInfo;

import java.util.Map;

public interface QualificationService extends IService<Qualification> {
    PageInfo<Qualification> selectByPage(Map<String, Object> params);

    long selectOfficeId(long qid);
}
