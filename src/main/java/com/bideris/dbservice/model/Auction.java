package com.bideris.dbservice.model;

import com.bideris.dbservice.helpers.Status;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "auction", catalog = "bideris")
public class Auction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @JsonFormat(pattern="yyyy/MM/dd")
    @Column(name = "start")
    private Date builtYear;

    @Column(name = "duration")
    private Integer duration;

    @Column(name = "status")
    private String status;

    @OneToOne
    private Post postFk;

}
