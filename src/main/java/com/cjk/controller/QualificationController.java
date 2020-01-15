package com.cjk.controller;

import com.cjk.entity.Qualification;
import com.cjk.service.QualificationService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 陈俊奎
 * @version 1.0.1
 * @company 东方标准
 * @date 2019/12/25
 * @description
 */
@RestController
@RequestMapping("manager/qualification")
public class QualificationController {

    @Autowired
    QualificationService service;

    /**
     * 跳转到资质审核模块主列表显示页面
     * @return
     */
    @RequestMapping("index")
    public ModelAndView index(){
        return new ModelAndView("/qualification/index");
    }


    @RequestMapping("toList")
    public PageInfo<Qualification> toList(@RequestBody Map<String,Object> params){
        return service.selectByPage(params);
    }

    /**
     * 根据Qualification的id返回资质对象的公司id和Qualification对象
     * @param id
     * @return
     */
    @RequestMapping("toUpdate")
    public Map<String,Object> toUpdate(Long id){
        HashMap<String, Object> map = new HashMap<>();
        map.put("qualication",service.selectByPrimaryKey(id));
        map.put("oid",service.selectOfficeId(id));
        return map ;
    }

    @RequestMapping("toUpdatePage")
    public ModelAndView toUpdatePage(){
        return new ModelAndView("/qualification/update");
    }
}
