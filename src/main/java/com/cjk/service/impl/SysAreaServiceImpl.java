package com.cjk.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.cjk.entity.SysArea;
import com.cjk.entity.SysAreaListener;
import com.cjk.mapper.SysAreaMapper;
import com.cjk.service.SysAreaService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class SysAreaServiceImpl extends IServiceImpl<SysArea> implements SysAreaService {

    @Autowired
    SysAreaMapper sysAreaMapper;


    
//    @Override
//    public PageInfo<SysArea> selectByCondition(Map<String, Object> params) {
//        //默认值设置
//        if(StringUtils.isEmpty(params.get("pageNum"))){
//            params.put("pageNum",1);
//        }
//        if(StringUtils.isEmpty(params.get("pageSize"))){
//            params.put("pageSize",5);
//        }
//        PageHelper.startPage((Integer) params.get("pageNum"),(Integer) params.get("pageSize"));
//
//        List<SysArea> statutes = statuteMapper.selectByCondition(params);
//
//        PageInfo<SysArea> pageInfo = new PageInfo<>(statutes);//生成分页对象
//
//        return pageInfo;
//    }

    /**
     * 根据传入的输出流对象，进行excel写入操作，并返回该输出流对象
     * @param outputStream
     * @return
     */
    @Override
    public OutputStream writeExcel(OutputStream outputStream){
        //获取excel写出对象
        ExcelWriter writer = EasyExcel.write(outputStream, SysArea.class).build();
        //获取sheet对象
        WriteSheet sheet = EasyExcel.writerSheet("sysarea数据").build();
        List<SysArea> sysAreas = mapper.selectAll();
        writer.write(sysAreas,sheet);//将数据写出到excel表的对应sheet位置

        //关闭资源
        writer.finish();
        return outputStream;
    }

    /**
     * 将传入的excel文件的组成的inputStream流读取，转换成java对象，并且进行批量插入到数据库
     * @param inputStream
     * @return
     */
    @Override
    public int readExcel(InputStream inputStream){
        int result = 0;
        ExcelReader excelReader = EasyExcel.read(inputStream,
                SysArea.class, new SysAreaListener(sysAreaMapper)).build();
        ReadSheet sheet = EasyExcel.readSheet(0).build();
        excelReader.read(sheet);//读资源
        excelReader.finish();
        result+=1;
        return result;
    }


    /**
     * 根据父区域id或者区域名或者不带条件查找所有区域
     * @return
     *
     *
     */
//    @Cacheable(value = "areaCache",key = "'SysAreaServiceImpl:selectByPage:'+#params['pageNum']+':'+#params['pageSize']")
    @Override
    public PageInfo<SysArea> selectByPage(Map<String, Object> params) {
        //{"aid":''}        {}
        //默认值设置
        if(StringUtils.isEmpty(params.get("pageNum"))){
            params.put("pageNum",1);
        }
        if(StringUtils.isEmpty(params.get("pageSize"))){
            params.put("pageSize",5);
        }
        PageInfo<SysArea> pageInfo = null;
        PageHelper.startPage((Integer) params.get("pageNum"),(Integer) params.get("pageSize"));
        //根据父区域id的查询
        if(params.containsKey("aid")&&!StringUtils.isEmpty(params.get("aid"))){
            int id =  (Integer) params.get("aid");
            List<SysArea> list = sysAreaMapper.selectByPid((long) id);
            pageInfo = new PageInfo<>(list);
        }
        return pageInfo;
    }

    @Override
    public SysArea selectByAid(long id) {
        SysArea sysArea = sysAreaMapper.selectByAid(id);
        sysArea.setOldParentIds(sysArea.getParentIds());//给旧parentIds绑定数据
        return sysArea;
    }


    /**
     * 1.更新区域的信息
     * 2.根据当前区域是否有更新parentIds来判断是否要更新所有的子区域的parentIds
     * @param sysArea
     * @return
     */
    @Override
    public int updateArea(SysArea sysArea){
        int i = 0;
        i += sysAreaMapper.updateByPrimaryKey(sysArea);

        if(!sysArea.getOldParentIds().equals(sysArea.getParentIds())){
            i+= sysAreaMapper.updateParentIds(sysArea);//更新所有的子区域的parentIds
        }
        return i;
    }

    /**
     * 根据区域名查询区域列表
     * @param params
     * @return
     */
    public PageInfo<SysArea> selectAllByName(Map<String,Object> params) {

        if (StringUtils.isEmpty(params.get("pageNum"))) {
            params.put("pageNum", 1);
        }
        if (StringUtils.isEmpty(params.get("pageSize"))) {
            params.put("pageSize", 5);
        }
        PageInfo<SysArea> pageInfo = null;
        PageHelper.startPage((Integer) params.get("pageNum"), (Integer) params.get("pageSize"));
        //根据父区域id的查询
        if (params.containsKey("name") && !StringUtils.isEmpty(params.get("aid"))) {
            List<SysArea> sysAreas = sysAreaMapper.selectAllByName(params);
            pageInfo = new PageInfo<>(sysAreas);
        }
        return pageInfo;

    }
}