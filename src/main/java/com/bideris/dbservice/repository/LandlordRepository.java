package com.bideris.dbservice.repository;

import com.bideris.dbservice.model.Landlord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LandlordRepository extends JpaRepository<Landlord,Integer> {

    Landlord findLandlordByUserName(String username);
}
