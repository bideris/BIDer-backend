package com.bideris.dbservice.repository;

import com.bideris.dbservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.jws.soap.SOAPBinding;

public interface UsersRepository extends JpaRepository<User,Integer> {

    User findUserByUserNameAndRole(String username,String role);
    User findUserByEmailAndRole(String email,String role);
    User findUserByUserNameOrEmailAndRole(String username, String email,String role);

    User findUserByUserName(String username);
}
