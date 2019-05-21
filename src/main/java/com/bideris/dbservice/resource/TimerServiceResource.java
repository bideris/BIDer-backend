package com.bideris.dbservice.resource;

import com.bideris.dbservice.helpers.EmailHelper;
import com.bideris.dbservice.model.Auction;
import com.bideris.dbservice.model.Bid;
import com.bideris.dbservice.model.User;
import com.bideris.dbservice.model.UserAuction;
import com.bideris.dbservice.repository.AuctionRepository;
import com.bideris.dbservice.repository.BidRepository;
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

    @Autowired
    private BidRepository bidRepository;

    @Scheduled(cron = "0 */2 * * * *")
    public void beginAuctions(){
        log.info("Timeris kviečia beginAuctions");
        List<Auction> auctions = auctionRepository.findAll();
        List<UserAuction> userAuctions = userAuctionRepository.findAll();
        Date now = new Date();
        for (Auction a: auctions) {

            int time = (int)( (a.getStartDate().getTime() -now.getTime()  ) / (1000 * 60 * 60 * 24));
            log.info("IKI STARTO LIKO {} ",time);
            log.info("iki aukciojo {}  \n pradzios liko {}",a.getStartDate(), time);
            if(time < 1 && !a.getStatus().equals("Ended")){
                log.info("Pakeite statusa");
                a.setStatus("Started");
                auctionRepository.save(a);
                for (UserAuction ua: userAuctions) {
                    if(a.getPostFk() == ua.getAuction().getPostFk()){
                        User u = ua.getUser();

                        EmailHelper.Notification notification = new EmailHelper.Notification(u.getFirstName(),u.getEmail(),
                                "buvo pradėtas naujas aukcionas " +a.getPost().getName(),
                                "Prasidejo naujas auckionas");
                        emailHelper.SendNotification(notification);
                    }
                }


            }
            log.info("time {}",time);
        }

    }
//
    @Scheduled(cron = "0 */2 * * * *")
    public void endAuctions() {
        log.info("Timeris kviečia endAuctions");

        List<Auction> auctions = auctionRepository.findAll();
        List<UserAuction> userAuctions = userAuctionRepository.findAll();
        Date now = new Date();
        for (Auction a : auctions) {

            int time = (int)( (a.getStartDate().getTime() -now.getTime()  )  / (1000 * 60 * 60 * 24));
            log.info("iki aukciono {} \n pabaigos liko {}",a.getStartDate(), time + a.getDuration());

            if (time + a.getDuration() < 1) {

                if(Setwinner(a)) {
                    log.info("STATUSA PAKEITE I ENDED IR PRIDEJO LAIMETOJA");
                    a.setStatus("Ended");
                }
                for (UserAuction ua: userAuctions) {
                    if(a.getPostFk() == ua.getAuction().getPostFk()){
                        User u = ua.getUser();
                        EmailHelper.Notification notification = new EmailHelper.Notification(u.getFirstName(),u.getEmail(),
                                "pasibaigė  aukcionas " +a.getPost().getName(),
                                "pasibaigė  aukcionas");
                        emailHelper.SendNotification(notification);
                    }
                }
                auctionRepository.save(a);
            }
        }
    }
//
    @Scheduled(cron = "0 */5 * * * *")
    public void inform(){
        log.info("Timeris kviečia inform");
        List<Auction> auctions = auctionRepository.findAll();
        List<UserAuction> userAuctions = userAuctionRepository.findAll();

        Date now = new Date();
        log.info(now.toString());
        for (Auction a: auctions) {

            int time = (int)( ( a.getStartDate().getTime() -now.getTime()  )  / (1000 * 60 * 60 * 24));
            log.info("iki aukciojo {} \n pradzios  liko {}", a.getStartDate() ,time );

            if(time < 10){
                for (UserAuction ua: userAuctions) {
                    if(a.getPostFk() == ua.getAuction().getPostFk()){
                        User u = ua.getUser();
                        EmailHelper.Notification notification = new EmailHelper.Notification(u.getFirstName(),u.getEmail(),
                                "iki aukciono " +a.getPost().getName() + " pradžios liko " +time,
                                "Informacija apie aukciono pradžia");
                        emailHelper.SendNotification(notification);
                    }
                }
            }
            log.info("time {}",time);
        }
    }

    private boolean Setwinner(Auction Auction){
        double max = 0.0;
        Bid maxbid = null;
        List<Bid> bids = bidRepository.findBidsByAuctionFk(Auction.getId());
        for (Bid b: bids) {
            if (max < b.getSum()) {
                max = b.getSum();
                maxbid = b;
            }
        }
        if(maxbid != null) {
            Auction.setWinner(maxbid.getUser());
            Auction.setWinnerFk(maxbid.getUserFk());
            return true;
        }
        return false;
    }
}
