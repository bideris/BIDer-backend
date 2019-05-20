package com.bideris.dbservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.Date;

@Data
@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    //Owner_fk
    @ManyToOne(fetch = FetchType.EAGER, optional = false,targetEntity = User.class)
    @JoinColumn(name="userFk")
    private User user;

    @Column(name = "userFk", insertable = false, updatable = false)
    private Integer userFk;

    private double price;

    private String name;

    private String about;

    private String country;

    private String city;

    private String houseNumber;

    private String apartmentNumber;

    private Double area;

    private Double rooms;

    private Double floor;


    private Double floorCount;

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date builtYear;



    @JsonFormat(pattern="yyyy-MM-dd")
    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "duration")
    private Integer duration;


    public Post() {
    }

    public Post(User landlord, double price, String name, String about, String country, String city, String houseNumber, String apartmentNumber, Double area, Double rooms, Double floor, Double floorCount, Date builtYear, Date startDate, Integer duration) {
        this.user = landlord;
        this.price = price;
        this.name = name;
        this.about = about;
        this.country = country;
        this.city = city;
        this.houseNumber = houseNumber;
        this.apartmentNumber = apartmentNumber;
        this.area = area;
        this.rooms = rooms;
        this.floor = floor;
        this.floorCount = floorCount;
        this.builtYear = builtYear;
        this.startDate = startDate;
        this.duration = duration;
    }


}
