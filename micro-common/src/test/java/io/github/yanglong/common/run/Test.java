package io.github.yanglong.common.run;

import io.github.yanglong.common.Application;
import io.github.yanglong.common.client.ZkDao;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * package: micro.service.yanglong.common.test <br/>
 * functional describe:
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    2016/5/17 13:59
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
//@WebIntegrationTest({"server.port=8888", "management.port=0"})
@WebIntegrationTest
public class Test {
    @Autowired
    private ZkDao dao;

    @org.junit.Test
    public void zkTest(){
        byte[] date=dao.readData("/test");
        System.out.println("=======================================");
        System.out.println(new String(date));
        System.out.println("==========================================");
    }
}
