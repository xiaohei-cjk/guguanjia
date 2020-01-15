package com.cjk.controller;



import com.cjk.entity.AppVersion;
import com.cjk.entity.Result;
import com.cjk.service.AppVersionService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

@RestController
@RequestMapping("manager/app")
public class AppVersionController {

    @Autowired
    AppVersionService service;

    /**
     * RestController返回视图到默认视图解析器处理通过ModelAndView返回默认的视图解析器
     * @return
     */
    @RequestMapping("index")
    public ModelAndView toIndex(){

        return new ModelAndView("/app/index");
    }



    @RequestMapping("toList")
    public PageInfo<AppVersion> toList(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "5") Integer pageSize){
        return service.selectPage(pageNum,pageSize);
    }


    /**
     * 到更新页面的查找app对象功能
     * @param id
     * @return
     */
    @RequestMapping("toUpdate")
    public AppVersion toUpdate(Integer id){
        return service.selectByPrimaryKey(id);
    }

    /**
     * 跳转到更新页面
     * @return
     */
    @RequestMapping("toUpdatePage")
    public ModelAndView toUpdatePage(){
        return new ModelAndView("/app/update");
    }

    /**
     * 更新app功能
     * @param appVersion
     * @return
     */
    @RequestMapping("doUpdate")
    public Result doUpdate(@RequestBody AppVersion appVersion){
        appVersion.setUpdateDate(new Date());
        Result result = new Result();
        int i = service.updateByPrimaryKeySelective(appVersion);
        if(i>0){
            result.setSuccess(true);
            result.setMsg("更新成功");
        }
        return result;
    }

    @RequestMapping("insert")
    public Result insert(@RequestBody AppVersion appVersion){
        appVersion.setUpdateDate(new Date());
        appVersion.setCreateDate(new Date());
        appVersion.setDelFlag("0");
        //TODO  给创建者属性赋值
        Result result = new Result();
        int i = service.insertSelective(appVersion);
        if(i>0){
            result.setSuccess(true);
            result.setMsg("更新成功");
        }
        return result;
    }

    @RequestMapping("delete")
    public Result delete(Integer id){
        int i = service.deleteByPrimaryKey(id);
        Result result = new Result();
        if(i>0){
            result.setSuccess(true);
            result.setMsg("删除成功");
        }
        return result;
    }
}
