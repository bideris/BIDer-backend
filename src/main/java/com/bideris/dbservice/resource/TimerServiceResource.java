package com.bideris.dbservice.resource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;


@Slf4j
@EnableScheduling
public class TimerServiceResource {

    @Scheduled(cron = "* * * * * *")
    public void hi(){
        log.info("Timeris kviečia");
    }
}
