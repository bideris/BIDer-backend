package com.bideris.dbservice.resource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class TimerServiceResource {

    @Scheduled(cron = "5/10 * * * * *")
    public void hi(){
        log.info("Timeris kviečia");
    }
//
//    @Scheduled(cron = "5/10 * * * * *")
//    public void hi(){
//        log.info("Timeris kviečia");
//    }
//
//    @Scheduled(cron = "5/10 * * * * *")
//    public void hi(){
//        log.info("Timeris kviečia");
//    }
}
