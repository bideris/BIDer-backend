package com.bideris.dbservice.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    //time
    @JsonFormat(pattern="yyyy/MM/dd HH:mm:ss")
    private Date date;

    private String text;


    //Auction_fk
    @ManyToOne(fetch = FetchType.EAGER, optional = false,targetEntity = Auction.class)
    @JoinColumn(name="auctionFk")
    private Auction auction;

    @Column(name = "auctionFk", insertable = false, updatable = false)
    private Integer auctionFk;


    //User_fk
    @ManyToOne(fetch = FetchType.EAGER, optional = false,targetEntity = User.class)
    @JoinColumn(name="userFk")
    private User user;

    @Column(name = "userFk", insertable = false, updatable = false)
    private Integer userFk;
}
