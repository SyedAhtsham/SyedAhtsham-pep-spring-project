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

    // This method will add a new message into the database using messageRepository's save method
    public Message addMessage(Message message){
        // first validating the existence of account, message text and length before trying to save
        if(accountRepository.existsById(message.getPostedBy()) && (message.getMessageText().length() != 0) && (message.getMessageText().length() < 255)){
            return messageRepository.save(message);
        }
        return null;
    }

    // This method returns all the messages stored in Database as a list
    public List<Message> retrieveAllMessages(){
        return messageRepository.findAll(); // findAll method is given by repository implicitly, which returns all the rows in the message table
    }

    // This method returns a single message found by its id
    public Message retrieveSingleMessage(Integer messageId){
        Optional<Message> optionalMessage = messageRepository.findById(messageId);
        if(optionalMessage.isPresent()){
            return optionalMessage.get(); // getting the message object from the optionalMessage
        }
        return null;
    }

    // This method will delete message by finding it by id
    public Integer removeMessage(Integer messageId){
        // first checking if the message exists
        if(messageRepository.existsById(messageId)){
            messageRepository.deleteById(messageId);
            // now checking again if the message has been deleted successfully, and return 1 if true
            if(!messageRepository.existsById(messageId)){
                return 1;
            }
        }
        return null;
    }


    // This method will update the message text of a message for a given message id
    public Integer updateMessage(Message message, Integer messageId){
        if(messageRepository.existsById(messageId) && message.getMessageText() != "" && message.getMessageText().length()<255){
            Optional<Message> optionalMessage = messageRepository.findById(messageId);
            if(optionalMessage.isPresent()){
                Message message2 = optionalMessage.get();
                // setting the new values into the message and save it
                    message2.setMessageText(message.getMessageText());
                    messageRepository.save(message2);
                    return 1;
            }
        }
    
        return null;
    }

    // This method retrieves all the messages posted by an account given accountID
    public List<Message> retrieveUserMessages(Integer accountId){
        return messageRepository.findMessagesByPostedBy(accountId);
    }

}
