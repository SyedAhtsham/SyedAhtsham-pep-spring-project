package com.example.service;

import java.lang.StackWalker.Option;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;


@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final AccountRepository accountRepository;
    @Autowired
    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository){
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }

    public Message addMessage(Message message){
        if(accountRepository.existsById(message.getPostedBy()) && (message.getMessageText().length() != 0) && (message.getMessageText().length() < 255)){
            return messageRepository.save(message);
        }
        return null;
    }

    // This method returns all the messages stored in Database as a list
    public List<Message> retrieveAllMessages(){
        return messageRepository.findAll();
    }

    // This method returns a single message found by its id
    public Message retrieveSingleMessage(Integer messageId){
        Optional<Message> optionalMessage = messageRepository.findById(messageId);
        if(optionalMessage.isPresent()){
            return optionalMessage.get();
        }
        return null;
    }

}
