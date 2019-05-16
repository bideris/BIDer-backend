package com.bideris.dbservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.Date;

@Data
@Entity
@Table(name = "posts", catalog = "bideris")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    //User_fk
    @ManyToOne(fetch = FetchType.EAGER, optional = false,targetEntity = User.class)
    @JoinColumn(name="userFk")
    private User user;

    @Column(name = "userFk", insertable = false, updatable = false)
    private Integer userFk;

    @Column(name = "price")
    private double price;

    @Column(name = "name")
    private String name;

    @Column(name = "about")
    private String about;

    @Column(name = "country")
    private String country;

    @Column(name = "city")
    private String city;

    @Column(name = "house_number")
    private String houseNumber;

    @Column(name = "apartment_number")
    private String apartmentNumber;

    @Column(name = "area")
    private Double area;

    @Column(name = "rooms")
    private Double rooms;

    @Column(name = "floor")
    private Double floor;


    @Column(name = "floor_count")
    private Double floorCount;

    @JsonFormat(pattern="yyyy/MM/dd")
    @Column(name = "built_year")
    private Date builtYear;



    @JsonFormat(pattern="yyyy/MM/dd")
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
