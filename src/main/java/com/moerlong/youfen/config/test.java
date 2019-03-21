package com.moerlong.youfen.config;

import com.moerlong.youfen.Utils.ApplicationContextUtil;
import com.moerlong.youfen.service.RemoteInvokeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class test {
    @Test
    public void getStudentList() throws Exception {
        RemoteInvokeService service = (RemoteInvokeService) ApplicationContextUtil.getApplicationContext().getBean("personalCreditFraudImpl");
        System.out.println(service);
    }
}
