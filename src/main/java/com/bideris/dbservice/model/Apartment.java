package com.bideris.dbservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.Date;

@Entity
@Table(name = "apartments", catalog = "bideris")
public class Apartment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    private Landlord landlord;

    @Column(name = "price")
    private double price;

    @Column(name = "name")
    private String name;

    @JsonFormat(pattern="yyyy/MM/dd")
    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "duration")
    private Integer duration;


    public Apartment() {
    }

    public Apartment(Landlord landlord, double price, String name, Date startDate, Integer duration) {
        this.landlord = landlord;
        this.price = price;
        this.name = name;
        this.startDate = startDate;
        this.duration = duration;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Landlord getLandlord() {
        return landlord;
    }

    public void setLandlord(Landlord landlord) {
        this.landlord = landlord;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }
}
