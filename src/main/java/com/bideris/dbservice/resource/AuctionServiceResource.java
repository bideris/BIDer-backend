package com.bideris.dbservice.resource;

import com.bideris.dbservice.model.Auction;
import com.bideris.dbservice.model.Post;
import com.bideris.dbservice.model.UserAuction;
import com.bideris.dbservice.repository.AuctionRepository;
import com.bideris.dbservice.repository.UserAuctionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

    @GetMapping("/all")
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
}

