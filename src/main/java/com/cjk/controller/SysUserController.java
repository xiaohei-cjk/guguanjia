package com.cjk.controller;

import com.cjk.entity.SysUser;
import com.cjk.service.SysUserService;
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
@RequestMapping("manager/sysuser")
public class SysUserController {

    @Autowired
    SysUserService service;

    @RequestMapping("")
    public ModelAndView toIndex(){
        return new ModelAndView("/user/user");
    }

    @RequestMapping("selectPage")
//    @ResponseBody
    public PageInfo<SysUser> index(@RequestBody Map<String,Object> params){
        PageInfo<SysUser> sysUserPageInfo = service.selectByCondition(params);
        return sysUserPageInfo;
    }


//

    @RequestMapping("detail")
    public SysUser detail(long id){
        return service.selectOneByCondition(id);
    }



    /**
     * 根据传入的角色id查询已经分配该角色的用户信息
     * @param rid
     * @return
     */
    @RequestMapping("selectByRid")
    public List<SysUser> selectByRid(long rid){
        return service.selectByRid(rid);
    }

    @RequestMapping("selectNoRole")
    public List<SysUser> selectNoRole(long rid,long oid){
        return service.selectNoRole(rid,oid);
    }


}
