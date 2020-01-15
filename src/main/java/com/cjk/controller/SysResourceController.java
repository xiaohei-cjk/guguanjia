package com.cjk.controller;

import com.cjk.entity.SysResource;
import com.cjk.service.SysResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

//替代Controller   自动添加@ResponseBody转换
@RestController
@RequestMapping("manager/menu")
public class SysResourceController {

    @Autowired
    SysResourceService service;

    

    @RequestMapping("list")
    public List<SysResource> list (){
        return service.selectAll();
    }

    @RequestMapping("selectByRid")
    public List<SysResource> selectByRid(long rid){
        return service.selectByRid(rid);
    }

    @RequestMapping("selectByUid")
    public List<SysResource> selectByUid(long uid, HttpSession session){
        //获取用户的所有权限，并且放入状态管理
        List<SysResource> sysResources = service.selectByUid(uid);
        session.setAttribute("resources",sysResources);
        return sysResources;
    }
}
