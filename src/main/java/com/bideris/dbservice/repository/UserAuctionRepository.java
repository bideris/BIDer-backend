package com.bideris.dbservice.repository;

import com.bideris.dbservice.model.UserAuction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserAuctionRepository extends JpaRepository<UserAuction,Integer >{

    UserAuction findUserAuctionById(Integer id);

    List<UserAuction> findUserAuctionsByUserFk(Integer id);

    UserAuction  findUserAuctionsByAuctionFk(Integer id);

    UserAuction findUserAuctionByUserFkAndAuctionFk(Integer user, Integer auction);
}
