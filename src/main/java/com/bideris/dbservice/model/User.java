package com.bideris.dbservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import net.bytebuddy.dynamic.loading.InjectionClassLoader;
import lombok.Data;
import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String userName;

    private String email;

    private String password;

    private String firstName;

    private String lastName;

    private String about;

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date birthdate;

    private Integer apartmentCount = 0;

    private String role;

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
