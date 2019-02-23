package com.bideris.dbservice.helpers;

import com.bideris.dbservice.model.User;
import lombok.Data;

@Data
public class ResponseUser {
    User user;
    Status status;
}

