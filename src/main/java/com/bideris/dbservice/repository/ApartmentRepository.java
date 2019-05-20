package com.bideris.dbservice.repository;

import com.bideris.dbservice.model.Post;
import com.bideris.dbservice.model.User;
import javafx.geometry.Pos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApartmentRepository extends JpaRepository<Post,Integer> {


    List<Post> findApartmentsByUser(User user);

    Post findApartmentById(Integer id);






}
