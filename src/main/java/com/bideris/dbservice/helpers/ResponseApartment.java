package com.bideris.dbservice.helpers;

import com.bideris.dbservice.model.Post;
import lombok.Data;

import java.util.List;

@Data
public class ResponseApartment {
    List<Post> posts;
    Status status;
}
