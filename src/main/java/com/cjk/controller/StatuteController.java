package com.cjk.controller;

import com.cjk.entity.Result;
import com.cjk.entity.Statute;
import com.cjk.service.StatuteService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

//替代Controller   自动添加@ResponseBody转换
@RestController
@RequestMapping("manager/statute")
public class StatuteController {

    @Autowired
    StatuteService service;

    @RequestMapping("index")
    public ModelAndView toIndex(){
        return new ModelAndView("/statute/index");
    }

    @RequestMapping("toUpdatePage")
    public ModelAndView toUpdatePage(){
        return new ModelAndView("/statute/update");
    }

    @RequestMapping("toList")
    public PageInfo<Statute> index(@RequestBody Map<String,Object> params){
        return service.selectByCondition(params);
    }


    @RequestMapping("toUpdate")

    public Statute toUpdate(Long id){
        return service.selectByPrimaryKey(id);
    }

    @RequestMapping("update")
    public Result update(@RequestBody Statute statute){
        int i = service.updateByPrimaryKeySelective(statute);
        Result result = new Result();
        if(i>0){
            result.setMsg("操作成功");
            result.setSuccess(true);
        }
        return result;

    }
}
