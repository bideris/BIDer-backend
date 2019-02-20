package com.bideris.dbservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import net.bytebuddy.dynamic.loading.InjectionClassLoader;
import lombok.Data;
import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "users" ,catalog = "bideris")
public class User {

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
    private Date birthdate;



    public User() {
    }

    public User(String userName, String email, String password, String firstName, String lastName, String about, Date birthdate) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.about = about;
        this.birthdate = birthdate;
    }


}
