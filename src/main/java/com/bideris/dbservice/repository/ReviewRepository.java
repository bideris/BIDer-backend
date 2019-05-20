package com.bideris.dbservice.repository;

import com.bideris.dbservice.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ReviewRepository extends JpaRepository<Review,Integer> {

    List<Review> findReviewsByUserRFk(Integer id);
}
