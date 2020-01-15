package com.cjk.Inteceptor;

import com.cjk.entity.SysResource;
import com.cjk.service.SysResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


/**
 * 权限认证拦截功能:
 * 1.获取系统所有权限 ，判断  用户请求权限是否在系统所有权限的控制范围，如果不在则放行
 * 2.如果在则需要进一步判断是否用户拥有的权限：
 * a.获取用户所有的权限，判断请求是否用户拥有的权限，如果是则放行
 * b.如果不是则返回到index页
 */
public class ResourcesInteceptor implements HandlerInterceptor {

    @Autowired
    SysResourceService resourceService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取系统所有权限  将查询数据放入缓存
        List<SysResource> sysResources = resourceService.selectAll();
        String uri = request.getRequestURI();//获取用户访问的uri   应用名开始的请求地址
        boolean flag = false;//是否需要进一步授权判断标记
        for (SysResource sysResource : sysResources) {
            if(!StringUtils.isEmpty(sysResource.getUrl())&&uri.contains(sysResource.getUrl())){
                flag = true;//找到匹配资源，需要进一步授权判断
                break;
            }
        }

        if(!flag){
            return true;//在sysResources中没有匹配到，不需要授权，放行
        }else{
            boolean hasResource = false;//标记是否在用户拥有的资源权限范围
            /*
            a.获取用户所有的权限，判断请求是否用户拥有的权限，如果是则放行
            b.如果不是则返回到index页
            */
            List<SysResource> resources = (List<SysResource>) request.getSession().getAttribute("resources");
            for (SysResource resource : resources) {
                if(!StringUtils.isEmpty(resource.getUrl())&&uri.contains(resource.getUrl())){
                    hasResource = true;
                    return true;
                }
            }
            request.getRequestDispatcher("/index").forward(request,response);//没有权限，跳转到index
        }
        return false;
    }
}
