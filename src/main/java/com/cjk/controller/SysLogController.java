package com.cjk.controller;

import com.cjk.entity.SysLog;
import com.cjk.service.SysLogService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

//替代Controller   自动添加@ResponseBody转换
@RestController
@RequestMapping("manager/syslog")
public class SysLogController {

    @Autowired
    SysLogService service;





    @RequestMapping("")
    public ModelAndView toIndex(){
        return new ModelAndView("/log/log");
    }

    @RequestMapping("selectPage")
    public PageInfo<SysLog> selectPage(@RequestBody Map<String,Object> params){
        return service.selectByCondition(params);
    }



}
