package com.cjk.controller;

import com.cjk.entity.Result;
import com.cjk.entity.SysOffice;
import com.cjk.entity.Waste;
import com.cjk.entity.WasteType;
import com.cjk.service.SysOfficeService;
import com.cjk.service.WasteService;
import com.cjk.service.WasteTypeService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

//替代Controller   自动添加@ResponseBody转换
@RestController
@RequestMapping("manager/office")
public class SysOfficeController {

    @Autowired
    SysOfficeService service;

    @Autowired
    WasteService wasteService;

    @Autowired
    WasteTypeService wasteTypeService;


    @RequestMapping("list")
    public List<SysOffice> list (){
        return service.selectAll();
    }

    @RequestMapping("")
    public ModelAndView toIndex(){
        return new ModelAndView("/office/office");
    }

    @RequestMapping("selectPage")
    public PageInfo<SysOffice> selectPage(@RequestBody Map<String,Object> params){
        return service.selectByCondition(params);
    }

    @RequestMapping("toUpdatePage")
    public ModelAndView toUpdate(){
        return new ModelAndView("/office/update");
    }

    @RequestMapping("toUpdate")
    public SysOffice toUpdate(long oid){
        return service.selectByOid(oid);
    }

    @RequestMapping("selectWaste")
    public List<Waste> selectWaste(long selected ){
        Waste waste = new Waste();
        waste.setParentId(selected);
        return wasteService.select(waste);
    }

    @RequestMapping("selectWasteType")
    public List<WasteType> selectWasteType(){
        return wasteTypeService.selectAll();
    }

    @RequestMapping("doUpdate")
    public Result doUpdate(@RequestBody SysOffice sysOffice){
        int update = service.update(sysOffice);
        Result result = new Result();
        if(update>0){
            result.setMsg("更新成功");
            result.setSuccess(true);
        }
        return result;
    }


    @RequestMapping("selectByRid")
    public List<SysOffice> selectByRid(long rid){
        return service.selectByRid(rid);
    }
}
