package com.cjk.controller;

import com.cjk.entity.WorkOrder;
import com.cjk.service.WorkOrderService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

//替代Controller   自动添加@ResponseBody转换
@RestController
@RequestMapping("manager/work/admin")
public class WorkOrderController {

    @Autowired
    WorkOrderService service;


    @RequestMapping("")
    public ModelAndView index(){
        return new ModelAndView("/work/admin/work");
    }

    @RequestMapping("toList")
    public PageInfo<WorkOrder> toList(@RequestBody Map<String,Object> params){
        return service.selectAll(params);
    }


    @RequestMapping("toUpdate")
    @ResponseBody
    public WorkOrder toUpdate(Long id){
        return service.selectByPrimaryKey(id);
    }


    @RequestMapping("selectByOid")
    public Map<String,Object> selectByOid(long oid){
        return service.selectByOid(oid);
    }

    @RequestMapping("toDetail")
    public ModelAndView toDetail(){
        return new ModelAndView("/work/work-detail");
    }
}
