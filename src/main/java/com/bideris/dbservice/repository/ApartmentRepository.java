package com.bideris.dbservice.repository;

import com.bideris.dbservice.model.Apartment;
import com.bideris.dbservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApartmentRepository extends JpaRepository<Apartment,Integer> {


    List<Apartment> findApartmentsByUser(User user);

    Apartment findApartmentById(Integer id);




}
