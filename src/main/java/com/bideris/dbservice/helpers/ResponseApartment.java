package com.bideris.dbservice.helpers;

import com.bideris.dbservice.model.Apartment;
import com.bideris.dbservice.model.User;
import lombok.Data;

import java.util.List;

@Data
public class ResponseApartment {
    List<Apartment> apartments;
    Status status;
}
