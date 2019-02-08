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

    public Apartment(Landlord landlord, double price, String name, String about, String country, String city, String houseNumber, String apartmentNumber, Double area, Double rooms, Double floor, Double floorCount, Integer builtYear, Date startDate, Integer duration) {
        this.landlord = landlord;
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

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getApartmentNumber() {
        return apartmentNumber;
    }

    public void setApartmentNumber(String apartmentNumber) {
        this.apartmentNumber = apartmentNumber;
    }

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public Double getRooms() {
        return rooms;
    }

    public void setRooms(Double rooms) {
        this.rooms = rooms;
    }

    public Double getFloor() {
        return floor;
    }

    public void setFloor(Double floor) {
        this.floor = floor;
    }

    public Double getFloorCount() {
        return floorCount;
    }

    public void setFloorCount(Double floorCount) {
        this.floorCount = floorCount;
    }

    public Integer getBuiltYear() {
        return builtYear;
    }

    public void setBuiltYear(Integer builtYear) {
        this.builtYear = builtYear;
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
