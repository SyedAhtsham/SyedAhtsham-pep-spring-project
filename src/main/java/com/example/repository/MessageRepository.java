package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.Message;

public interface MessageRepository extends JpaRepository<Message, Integer>{

    // Named query to find messages posted by a given account
    List<Message> findMessagesByPostedBy(Integer postedBy);
}
