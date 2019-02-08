package com.bideris.dbservice.repository;

import com.bideris.dbservice.model.Apartment;
import com.bideris.dbservice.model.Landlord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApartmentRepository extends JpaRepository<Apartment,Integer> {


    List<Apartment> findApartmentsByLandlord(Landlord landlord);

    Apartment findApartmentById(Integer id);




}
