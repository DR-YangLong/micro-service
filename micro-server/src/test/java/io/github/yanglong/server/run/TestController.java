package io.github.yanglong.server.run;

import io.github.yanglong.server.annotation.MicroService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * package: io.github.yanglong.server.run <br/>
 * functional describe:
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    2016/5/21 12:59
 */
@RestController
@MicroService
@RequestMapping({"/test", "/test1"})
public class TestController {

    @RequestMapping("/hello")
    public String hello() {
        return "hello";
    }

    @RequestMapping("/world")
    public String world() {
        return "world";
    }
}
