package com.bideris.dbservice.resource;

import com.bideris.dbservice.model.*;
import com.bideris.dbservice.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/auction")
public class AuctionServiceResource {

    @Autowired
    AuctionRepository auctionRepository;

    @Autowired
    UserAuctionRepository userAuctionRepository;

    @Autowired
    BidRepository bidRepository;

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    UsersRepository usersRepository;

    @GetMapping("/{id}")
    public Auction getByid(@PathVariable("id") final Integer id)
    {
        return auctionRepository.findAuctionById(id);
    }


    @GetMapping("/post/{id}")
    public Auction getByPost(@PathVariable("id") final Integer id)
    {
        return auctionRepository.findAuctionByPostFk(id);
    }

    @PostMapping("/all")
    public List<Auction> getAllAuctions(){

        return auctionRepository.findAll();
    }

    @GetMapping("/user/{userId}")
    public List<Auction> getDoneAuctionList(@PathVariable("userId") final Integer userId){
        List<UserAuction> userAuctions = userAuctionRepository.findUserAuctionsByUserFk(userId);
        List<Auction> auctions = new ArrayList<>();
        for (UserAuction userAuction:userAuctions) {
            log.info("User Action - {}",userAuction);
            auctions.add(userAuction.getAuction());
        }

        return auctions;

    }

    @PostMapping("/accept/{userId}/{actionId}")
    public Auction Accept(@PathVariable("userId") final Integer userId, @PathVariable("actionId") final Integer actionId){
        UserAuction userAuction = userAuctionRepository.findUserAuctionByUserFkAndAuctionFk(userId,actionId);
        if(!userAuction.getAuction().getStatus().equals("Done") && userAuction.getAuction().getWinner() == null) {
            log.info("pazymim");
            userAuction.getAuction().setStatus("Done");
            userAuction.getAuction().setWinnerFk(userId);
            userAuction.getAuction().setWinner(userAuction.getUser());
            auctionRepository.save(userAuction.getAuction());
        }

        return userAuctionRepository.findUserAuctionByUserFkAndAuctionFk(userId,actionId).getAuction();

    }


    @GetMapping("/messages/{auctionid}")
    public List<Message> getMessages(@PathVariable("auctionid") final Integer auctionid){

        return messageRepository.findMessagesByAuctionFk(auctionid);
    }

    @GetMapping("/message/{id}")
    public Message getMessageByid(@PathVariable("id") final Integer id){
        return messageRepository.findMessageById(id);
    }

    @PostMapping("/message/add")
    public List<Message> addMessage(@RequestBody final Message message){
        //DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        log.info("Message - {}",message);
        Date date = new Date();
        message.setDate(date);
        message.setAuction(auctionRepository.findAuctionById(message.getAuctionFk()));
        message.setUser(usersRepository.findUserById(message.getUserFk()));
        if(validateMessage(message)){
            messageRepository.save(message);
        }
        return messageRepository.findAll();
    }

    public Boolean validateMessage(Message message){

        return true;
    }

    @GetMapping("/bids/{auctionid}")
    public List<Bid> getBids(@PathVariable("auctionid") final Integer auctionid){

        return bidRepository.findBidsByAuctionFk(auctionid);
    }


    @GetMapping("/bid/{id}")
    public Bid getBidById(@PathVariable("id") final Integer id){
        return bidRepository.findBidById(id);
    }

    @PostMapping("/bid/add")
    public List<Bid> addBid(@RequestBody final Bid bid){
        log.info("Bid - {}",bid);
        Date date = new Date();
        bid.setDate(date);
        bid.setAccepted(false);
        bid.setAuction(auctionRepository.findAuctionById(bid.getAuctionFk()));
        bid.setUser(usersRepository.findUserById(bid.getUserFk()));
        if(validateBid(bid)){
            bidRepository.save(bid);
        }
        return bidRepository.findAll();

    }

    public Boolean validateBid(Bid bid){

        return true;

    }



}

