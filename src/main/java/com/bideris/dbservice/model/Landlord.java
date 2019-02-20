package com.bideris.dbservice.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import net.bytebuddy.dynamic.loading.InjectionClassLoader;

import javax.persistence.*;
@Data
@Entity
@Table(name = "landlords" ,catalog = "bideris")
public class Landlord {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "Last_name")
    private String lastName;

    @Column(name = "about")
    private String about;

    @JsonFormat(pattern="yyyy/MM/dd")
    @Column(name = "birthdate")
    private Integer birthdate;

    @Column(name = "apartment_count")
    private Integer apartmentCount;



    public Landlord() {
    }


    public Landlord(String userName, String email, String password, String firstName, String lastName, String about, Integer birthdate, Integer apartmentCount) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.about = about;
        this.birthdate = birthdate;
        this.apartmentCount = apartmentCount;
    }


}

