package com.bideris.dbservice.helpers;

import lombok.Data;

@Data
public class Status {
    int status;
    String statusMessage;


    public Status(int status, String statusMessage) {
        this.status = status;
        this.statusMessage = statusMessage;
    }
}
