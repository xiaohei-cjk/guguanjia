package com.cjk.entity;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.cjk.mapper.SysAreaMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * 官方文档提到： 该监听器不要被spring容器管理，如果需要使用spring容器中的对象，需要通过构造方法注入方式注入即可
 */
public class SysAreaListener extends AnalysisEventListener<SysArea> {



    private SysAreaMapper mapper;
    private List<SysArea> list=new ArrayList<>();

    public SysAreaListener() {
    }

    public SysAreaListener(SysAreaMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void invoke(SysArea sysArea, AnalysisContext analysisContext) {
        list.add(sysArea);
        if(list.size()==10){
//            System.out.println("模拟保存操作到数据库.....");
//            System.out.println(list);
            mapper.insertBath(list);
            list.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
//        System.out.println(list.size());
//        System.out.println("模拟最后一次保存操作。。。。。。");
        if(list.size()>0){
            mapper.insertBath(list);
        }
    }
}
