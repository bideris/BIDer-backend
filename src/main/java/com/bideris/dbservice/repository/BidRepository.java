package com.bideris.dbservice.repository;

import com.bideris.dbservice.model.Bid;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BidRepository extends JpaRepository<Bid,Integer> {

    List<Bid> findBidsByAuctionFk(Integer id);
    Bid findBidById(Integer id);
}
