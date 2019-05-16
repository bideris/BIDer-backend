package com.bideris.dbservice.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    String url;

    @ManyToOne(fetch = FetchType.EAGER, optional = false,targetEntity = Auction.class)
    @JoinColumn(name="auctionFk")
    private Auction auction;

    @Column(name = "auctionFk", insertable = false, updatable = false)
    private Integer auctionFk;
}
