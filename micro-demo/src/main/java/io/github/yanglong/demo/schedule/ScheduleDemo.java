package io.github.yanglong.demo.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * functional describe:
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    16-7-28
 */
@Component
public class ScheduleDemo {
    private static final Logger log = LoggerFactory.getLogger(ScheduleDemo.class);
    @Scheduled(cron = "0/5 * * * * ?")
    public void print(){
        log.error("test schedule success!");
    }
}
