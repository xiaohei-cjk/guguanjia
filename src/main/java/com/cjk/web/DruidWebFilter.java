package com.cjk.web;

import com.alibaba.druid.support.http.WebStatFilter;

import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;

/**
 * 开启web功能监控的过滤器
 */
@WebFilter(urlPatterns = "/",initParams = {
        @WebInitParam(name = "exclusions",value = "*.js,*.jpg,*.png,*.css,/druid/*"),//设置忽略规则
        @WebInitParam(name = "profileEnable",value = "true"),//设置单个url的调用sql列表
        @WebInitParam(name = "principalSessionName",value="userInfo")
})
public class DruidWebFilter extends WebStatFilter {
}
