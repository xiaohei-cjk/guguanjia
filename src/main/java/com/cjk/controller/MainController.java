package com.cjk.controller;

import com.cjk.entity.Result;
import com.cjk.entity.SysUser;
import com.cjk.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class MainController {

    @Autowired
    SysUserService service;
    /**
     * 登录页
     * @return
     */
    @RequestMapping("toLogin")
    public String toLogin(){
        return "/login";
    }


    /**
     * 登录处理
     * 1.校验验证码是否正确
     * 2.正确验证码，则校验登录
     */
    @RequestMapping("doLogin")
    @ResponseBody
    public Result doLogin(@RequestBody Map<String,Object> params, HttpSession session){
        Result result = new Result();
//获取session中的vcode
        String vcode = (String) session.getAttribute("vcode");

        if(vcode.equals(params.get("code"))){//校验验证码正确
            SysUser sysUser = new SysUser();
            sysUser.setUsername((String) params.get("username"));
            sysUser.setPassword((String) params.get("password"));
            SysUser chechUser = service.checkSysUser(sysUser);
            if(chechUser!=null){//登录成功
                result.setSuccess(true);
                result.setMsg("登录成功");
                result.setObj(chechUser);
                //将用户信息放入session
                session.setAttribute("userInfo",chechUser);
            }
        }
        return result;

//
    }

    /**
     * 登出
     * @param session
     * @return
     */
    @RequestMapping("logout")
    @ResponseBody
    public Result logout(HttpSession session){
        Result result = new Result();
        session.invalidate();
        result.setSuccess(true);
        return result;
    }

    @RequestMapping("index")
    public String index(){
        return "/index";
    }
}
