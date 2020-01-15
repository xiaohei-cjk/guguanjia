package com.cjk.service;

import com.cjk.entity.AppVersion;
import com.github.pagehelper.PageInfo;

/**
 * @author 陈俊奎
 * @version 1.0.1
 * @company 东方标准
 * @date 2019/12/25
 * @description
 */
public interface AppVersionService extends IService<AppVersion> {

    PageInfo<AppVersion> selectPage(int pageNum, int pageSize);
}
