package com.bideris.dbservice.resource;

import com.bideris.dbservice.helpers.EmailHelper;
import com.bideris.dbservice.model.Auction;
import com.bideris.dbservice.model.User;
import com.bideris.dbservice.model.UserAuction;
import com.bideris.dbservice.repository.AuctionRepository;
import com.bideris.dbservice.repository.UserAuctionRepository;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;


@Slf4j
@Component
public class TimerServiceResource {

    private EmailHelper emailHelper = new EmailHelper();

    @Autowired
    private AuctionRepository auctionRepository;

    @Autowired
    private UserAuctionRepository userAuctionRepository;


    @Scheduled(cron = "5/10 * * * * *")
    public void beginAuctions(){
        log.info("Timeris kviečia beginAuctions");
        List<Auction> auctions = auctionRepository.findAll();
        List<UserAuction> userAuctions = userAuctionRepository.findAll();
        Date now = new Date();
        for (Auction a: auctions) {

            int time = (int)( (a.getStartDate().getTime() - now.getTime()) / (1000 * 60 * 60 * 24));
            if(time < 1){
                log.info("Pakeite statusa");
                a.setStatus("Started");
                auctionRepository.save(a);
                for (UserAuction ua: userAuctions) {
                    if(a.getPostFk() == ua.getAuction().getPostFk()){
                        User u = ua.getUser();
                        EmailHelper.Notification notification = new EmailHelper.Notification(u.getFirstName(),u.getEmail(),
                                "buvo pradėtas naujas aukcijonas " +a.getPost().getName(),
                                "Prasidejo naujas auckionas");
                        emailHelper.SendNotification(notification);
                    }
                }


            }
            log.info("time {}",time);
        }

    }
//
    //@Scheduled(cron = "5/10 * * * * *")
    public void endAuctions() {
        log.info("Timeris kviečia endAuctions");

        List<Auction> auctions = auctionRepository.findAll();
        List<UserAuction> userAuctions = userAuctionRepository.findAll();
        Date now = new Date();
        for (Auction a : auctions) {

            int time = (int) ((a.getStartDate().getTime() - now.getTime()) / (1000 * 60 * 60 * 24));
            log.info("Pakeite statusa - liko {}", time + a.getDuration());

            if (time + a.getDuration() < 1) {
                log.info("Paktas statusa");
                a.setStatus("Ended");
                for (UserAuction ua: userAuctions) {
                    if(a.getPostFk() == ua.getAuction().getPostFk()){
                        User u = ua.getUser();
                        EmailHelper.Notification notification = new EmailHelper.Notification(u.getFirstName(),u.getEmail(),
                                "pas naujas aukcijonas " +a.getPost().getName(),
                                "Prasidejo naujas auckionas");
                        emailHelper.SendNotification(notification);
                    }
                }
                auctionRepository.save(a);
            }
        }
    }
//
    //@Scheduled(cron = "5/10 * * * * *")
    public void inform(){
        log.info("Timeris kviečia inform");
        List<Auction> auctions = auctionRepository.findAll();
        Date now = new Date();
        log.info(now.toString());
        for (Auction a: auctions) {

            int time = (int)( (a.getStartDate().getTime() - now.getTime()) / (1000 * 60 * 60 * 24));
            if(Math.abs(time) < 10){
            }
            log.info("time {}",time);
        }
    }
}
