import com.alibaba.druid.pool.DruidDataSource;
import com.cjk.config.SpringMybatisConfig;
import com.cjk.entity.AppVersion;
import com.cjk.entity.SysArea;
import com.cjk.mapper.AppVersionMapper;
import com.cjk.mapper.SysAreaMapper;
import com.cjk.service.AppVersionService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 陈俊奎
 * @version 1.0.1
 * @company 东方标准
 * @date 2019/12/25
 * @description
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringMybatisConfig.class)
public class Test01 {

    @Autowired
    DruidDataSource druidDataSource;

    @Autowired
    AppVersionMapper mapper;

    @Autowired
    AppVersionService service;

    @Autowired
    SysAreaMapper sysAreaMapper;

    @Test
    public void test01(){
        try {
            System.out.println(druidDataSource.getConnection());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testMapper(){

        AppVersion appVersion = mapper.selectByPrimaryKey((long) 1);
        System.out.println(appVersion);
    }



    @Test
    public void testPageInfo(){
        PageHelper.startPage(1,3);
        List<AppVersion> list = mapper.selectAll();//当前方法返回值已经被替换成Page对象类型
        PageInfo<AppVersion> pageInfo = new PageInfo<>(list);
        System.out.println(pageInfo);

    }

    @Test
    public void testArea(){
        HashMap<String,Object> params=new HashMap<>();
        params.put("name","市");
        List<SysArea> sysAreas = sysAreaMapper.selectAllByName(params);
        for (SysArea sysArea : sysAreas) {
            System.out.println(sysArea);
        }
    }



}
