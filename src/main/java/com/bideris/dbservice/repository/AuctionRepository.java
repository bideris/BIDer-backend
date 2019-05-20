package com.bideris.dbservice.repository;

import com.bideris.dbservice.model.Auction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuctionRepository extends JpaRepository<Auction,Integer> {

    Auction findAuctionById(Integer id);

    Auction findAuctionByPostFk(Integer id);

}
