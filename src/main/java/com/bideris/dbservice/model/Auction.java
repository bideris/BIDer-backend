package com.bideris.dbservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class Auction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date startDate;

    private Integer duration;

    private String status;

    //postFk
    @OneToOne(fetch = FetchType.EAGER, optional = false,targetEntity = Post.class)
    @JoinColumn(name="postFk")
    private Post post;

    @Column(name = "postFk", insertable = false, updatable = false)
    private Integer postFk;

    //User_fk
    @ManyToOne(fetch = FetchType.LAZY,targetEntity = User.class)
    @JoinColumn(name="winnerFk")
    private User winner;

    @Column(name = "winnerFk", insertable = false, updatable = false)
    private Integer winnerFk;

}
