package com.bideris.dbservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "bid", catalog = "bideris")
public class Bid {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "sum")
    private Double sum;

    @JsonFormat(pattern="yyyy/MM/dd")
    @Column(name = "data")
    private Date data;

    @Column(name = "accepted")
    private boolean accepted;


}
