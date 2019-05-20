package com.bideris.dbservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class Bid {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private Double sum;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date date;

    private boolean accepted;

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
