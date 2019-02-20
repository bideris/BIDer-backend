package com.bideris.dbservice.model;

import com.bideris.dbservice.helpers.PasswordHashing;
import com.bideris.dbservice.repository.UsersRepository;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
@Data
public class UserRegistration {


    private PasswordHashing passwordHashing;
    private UsersRepository usersRepository;

    private Integer id;
    private String userName;
    private String email;
    private String password;
    private String password2;
    private String firstName;
    private String lastName;
    private String about;
    @JsonFormat(pattern="yyyy/MM/dd")
    private Date birthdate;

    public UserRegistration() {
    }

    public UserRegistration(Integer id, String userName, String email, String password, String password2, String firstName, String lastName, String about, Date birthdate) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.password2 = password2;
        this.firstName = firstName;
        this.lastName = lastName;
        this.about = about;
        this.birthdate = birthdate;
    }





    public User toUser(){
        User user = new User();
        user.setUserName(getUserName());
        user.setEmail(getEmail());
        user.setPassword(passwordHashing.hashPassword(getPassword()));//ennnncoding
        user.setFirstName(getFirstName());
        user.setLastName(getLastName());
        user.setAbout(getAbout());
        user.setBirthdate(getBirthdate());

        //passwordHashing.hashPassword(getPassword());

        return user;
    }




}
