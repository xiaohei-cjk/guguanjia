package com.cjk.controller;

import com.cjk.entity.Result;
import com.cjk.entity.SysArea;
import com.cjk.service.SysAreaService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("manager/area")
public class SysAreaController {

    @Autowired
    SysAreaService service;


    /**
     * 跳转到查询页
     * @return
     */
    @RequestMapping("")
    public ModelAndView toIndex(){
        return new ModelAndView("/area/area");
    }

    /**
     * Excel下载操作:
     * 1.设置响应头
     * 2.设置文件乱码处理
     * 3.获取响应数据流
     * 4.写出到页面
     */
    @RequestMapping("download")
    public void download(HttpServletResponse response) throws IOException {

        response.setHeader("Content-Disposition","attachment;filename=sysArea.xls");
//        FileOutputStream fileOutputStream = new FileOutputStream("");
        OutputStream outputStream = response.getOutputStream();
        outputStream = service.writeExcel(outputStream);//响应流数据已经有文件信息

    }


    @RequestMapping("upload")
    public Result upload(MultipartFile upFile) throws IOException {
        Result result = new Result();
        int i = service.readExcel(upFile.getInputStream());
        if(i>0){
            result.setMsg("导入数据成功");
            result.setSuccess(true);
        }
        return result;
    }



    @RequestMapping("selectPage")
    public PageInfo<SysArea> selectPage(@RequestBody Map<String,Object> params){
        return  service.selectByPage(params);
    }
    @RequestMapping("selectAll")
    public List<SysArea> selectAll(){
        return  service.selectAll();
    }

    @RequestMapping("toUpdate")
    public SysArea toUpdate(long id){
        return service.selectByAid(id);
    }

    @RequestMapping("toUpdatePage")
    public ModelAndView toUpdatePage(){
        return new ModelAndView("/area/save");
    }

    @RequestMapping("awesome")
    public ModelAndView awesome(){
        return new ModelAndView("/modules/font-awesome");
    }

    @RequestMapping("doUpdate")
    public Result doUpdate(@RequestBody SysArea area){
        Result result = new Result();

        int i = service.updateArea(area);
        if(i>0){
            result.setSuccess(true);
            result.setMsg("更新成功");
        }
        return result;
    }


    @RequestMapping("selectAllByName")
    public PageInfo<SysArea> selectAllByName(@RequestBody Map<String,Object> params){
        return service.selectAllByName(params);
    }


}
