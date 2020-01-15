
import com.cjk.config.SpringMybatisConfig;
import com.cjk.entity.SysOffice;
import com.cjk.mapper.SysOfficeMapper;
import com.cjk.service.SysOfficeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringMybatisConfig.class)
public class TestSysOffice {

    @Autowired
    SysOfficeMapper mapper;

    @Autowired
    SysOfficeService service;

    /**
     * 测试
     */
    @Test
    public void test1(){
        List<SysOffice> sysOffices = service.selectAll();
//        System.out.println(sysOffices);

        List<SysOffice> sysOffices1 = service.selectAll();

    }

    @Test
    public  void test2(){
        SysOffice sysOffice = service.selectByPrimaryKey(2);
        sysOffice.setName("新用户默认机构test");
        int i = service.updateByPrimaryKeySelective(sysOffice);
    }


}
