package com.bideris.dbservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.Date;

@Data
@Entity
@Table(name = "apartments", catalog = "bideris")
public class Apartment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    private User user;

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
    private Integer builtYear;



    @JsonFormat(pattern="yyyy/MM/dd")
    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "duration")
    private Integer duration;


    public Apartment() {
    }

    public Apartment(User landlord, double price, String name, String about, String country, String city, String houseNumber, String apartmentNumber, Double area, Double rooms, Double floor, Double floorCount, Integer builtYear, Date startDate, Integer duration) {
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
