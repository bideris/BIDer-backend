package com.bideris.dbservice.repository;

import com.bideris.dbservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<User,Integer> {

    User findUserByUserName(String username);
    User findUserByEmail(String email);
    User findUserByUserNameOrEmail(String username, String email);
}
