package com.bideris.dbservice.helpers;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
@Slf4j
public class StatusCodes {

  private Status[] statuses = {
    new Status(0,"OK"),

    new Status(10,"Landlord with this username not found"),
    new Status(11,"Apartment with this id not found"),
    new Status(12,"Apartments for this user not found"),
    new Status(13,"User with this username not found"),
    new Status(14,"User with this email not found"),
    new Status(15,"User with this email or username not found"),

    new Status(20,"landlord with this username already exists"),
    new Status(21,"User with this username already exists "),
    new Status(22,"Username already exists"),
    new Status(23,"User with this email already exists "),

    new Status(30,"Password is too weak"),
    new Status(31,"Passwords did not mach"),
    new Status(32,"Bad email"),

    };

    public Status getStatuse(int id) {
        Status status = statuses[0];
            for (Status statuse : statuses) {
                if (statuse.getStatus() == id) {
                    status = statuse;
                }
            }

        log.info("{}: {}",status.status,status.statusMessage);
        return status;
    }
}
