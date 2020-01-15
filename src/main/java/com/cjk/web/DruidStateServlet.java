package com.cjk.web;

import com.alibaba.druid.support.http.StatViewServlet;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

/**
 * 性能监控页面是由druid通过一个servlet 自动生成的
 * 需要编写一个servlet类继承StatViewServlet
 */
@WebServlet(urlPatterns = "/druid/*",initParams = {
        @WebInitParam(name="loginUsername",value = "druid"),//druid性能监控登录页面账号
        @WebInitParam(name="loginPassword",value = "123456"),
        @WebInitParam(name="allow",value = ""),//允许进入性能监控页的ip
        @WebInitParam(name="deny",value = "")//黑名单
})
public class DruidStateServlet extends StatViewServlet {
}
