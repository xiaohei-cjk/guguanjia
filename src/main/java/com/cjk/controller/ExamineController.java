package com.cjk.controller;

import com.cjk.entity.Examine;
import com.cjk.service.ExamineService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * @author 陈俊奎
 * @version 1.0.1
 * @company 东方标准
 * @date 2019/12/26
 * @description
 */
@RestController
@RequestMapping("manager/examine")
public class ExamineController {

    @Autowired
    ExamineService service;

    /**
     * 跳转页面
     * @return
     */
    @RequestMapping("index")
    public ModelAndView index(){
        return new ModelAndView("/examine/index");
    }

    /**
     * 根据条件查询数据
     * @param params
     * @return
     */
    @RequestMapping("toList")
    public PageInfo<Examine> toList(@RequestBody  Map<String,Object> params){
        return service.selectAll(params);
    }

    /**
     * 根据id查询数据并返回给修改页面
     * @param id
     * @return
     */
    @RequestMapping("toUpdate")
    @ResponseBody
    public Examine toUpdate(Integer id){
        return service.selectByPrimaryKey(id);
    }
}
