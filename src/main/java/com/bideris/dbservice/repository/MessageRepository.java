package com.bideris.dbservice.repository;

import com.bideris.dbservice.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository <Message,Integer> {

    List<Message> findMessagesByAuctionFk(Integer id);

    Message findMessageById(Integer id);
}
