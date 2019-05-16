package com.bideris.dbservice.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class UserAuction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //User_fk
    @ManyToOne(fetch = FetchType.EAGER, optional = false,targetEntity = User.class)
    @JoinColumn(name="userFk")
    private User user;

    @Column(name = "userFk", insertable = false, updatable = false)
    private Integer userFk;

    //Auction_fk
    @ManyToOne(fetch = FetchType.EAGER, optional = false,targetEntity = Auction.class)
    @JoinColumn(name="auctionFk")
    private Auction auction;

    @Column(name = "auctionFk", insertable = false, updatable = false)
    private Integer auctionFk;
}
