package com.bideris.dbservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    Integer rate;

    String feedback;

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date date;


    //User_fk (vertintojas)
    @ManyToOne(fetch = FetchType.EAGER, optional = false,targetEntity = User.class)
    @JoinColumn(name="userRFk")
    private User userR;

    @Column(name = "userRFk", insertable = false, updatable = false)
    private Integer userRFk;

    //User_fk (vertinamas)
    @ManyToOne(fetch = FetchType.EAGER, optional = false,targetEntity = User.class)
    @JoinColumn(name="userFFk")
    private User userF;

    @Column(name = "userFFk", insertable = false, updatable = false)
    private Integer userFFk;


}
