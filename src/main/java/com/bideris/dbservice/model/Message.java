package com.bideris.dbservice.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "apartments", catalog = "bideris")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Auction auction;

    //time
    @JsonFormat(pattern="yyyy/MM/dd")
    @Column(name = "date")
    private Date date;

    @Column(name = "message")
    private String houseNumber;
}
