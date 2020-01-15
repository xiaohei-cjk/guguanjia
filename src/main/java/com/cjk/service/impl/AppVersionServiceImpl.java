package com.cjk.service.impl;

import com.cjk.entity.AppVersion;
import com.cjk.service.AppVersionService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 陈俊奎
 * @version 1.0.1
 * @company 东方标准
 * @date 2019/12/25
 * @description
 */
@Service
@Transactional
public class AppVersionServiceImpl extends IServiceImpl<AppVersion> implements AppVersionService {
    @Override
    public PageInfo<AppVersion> selectPage(int pageNum, int pageSize) {
        //开启分页拦截  分页插件会自动在最近一个sql执行前，自动添加分页的sql代码 limit x,x
        PageHelper.startPage(pageNum,pageSize);
        List<AppVersion> list = mapper.selectAll();//当前方法返回值已经被替换成Page对象类型
        return new PageInfo<>(list);
    }
}
