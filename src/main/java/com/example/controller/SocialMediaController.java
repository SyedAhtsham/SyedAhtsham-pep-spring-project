package com.example.controller;


import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {

    private final AccountService accountService;
    private final MessageService messageService;

    
    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService){
        this.accountService = accountService;
        this.messageService = messageService;
    }

    // This api end point will create a new account in the database using the accountSerivice's method, if there is already an account with this username, it'll give a CONFLICT response status
    @PostMapping("register")
    public ResponseEntity<Account> registerAccount(@RequestBody Account account) {
        Account createdAccount = accountService.createAccount(account);
        return new ResponseEntity<>(createdAccount, createdAccount!=null?HttpStatus.OK:HttpStatus.CONFLICT);
    }

    // log in api end point, it'll check if uesrname and password match a row in the database
    @PostMapping("login")
    public ResponseEntity<Account> loginAccount(@RequestBody Account account) {
        Account loggedInAccount = accountService.loginAccount(account);
        return new ResponseEntity<>(loggedInAccount, loggedInAccount!=null?HttpStatus.OK:HttpStatus.UNAUTHORIZED);
    }

    // This method adds a new message to the database using message Service and returns a response
    @PostMapping("messages")
    public ResponseEntity<Message> postMessage(@RequestBody Message message){
        Message postedMessage = messageService.addMessage(message);
        return new ResponseEntity<>(postedMessage, postedMessage!=null?HttpStatus.OK:HttpStatus.BAD_REQUEST);
    }


    // This method will retrieve all the messages via Message service 
    @GetMapping("messages")
    public ResponseEntity<List<Message>> getAllMessages(){
        List<Message> allMessages = messageService.retrieveAllMessages();
        return new ResponseEntity<>(allMessages, HttpStatus.OK);
    }

    // This method finds a message by its id, will return a response entity containing found message and status
    @GetMapping("messages/{messageId}")
    public ResponseEntity<Message> getSingleMessage(@PathVariable Integer messageId){
        return new ResponseEntity<>(messageService.retrieveSingleMessage(messageId), HttpStatus.OK);

    }

    // This method will remove the message from the database
    @DeleteMapping("messages/{messageId}")
    public ResponseEntity<Integer> deleteMessage(@PathVariable Integer messageId){
        return new ResponseEntity<>(messageService.removeMessage(messageId), HttpStatus.OK);
    }

    // This method will update the message in the database and return 200 if update occured else 400
    @PatchMapping("messages/{messageId}")
    public ResponseEntity<Integer> patchMessage(@RequestBody Message message, @PathVariable Integer messageId){
        Integer noOfRowsEffected = messageService.updateMessage(message, messageId);
        return new ResponseEntity<>(noOfRowsEffected, noOfRowsEffected!=null?HttpStatus.OK:HttpStatus.BAD_REQUEST);

    }

    // This method will retrieve all messages posted by a user and send them as response to the user with 200 status
    @GetMapping("accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> getAllMessagesByUser(@PathVariable Integer accountId){
        return new ResponseEntity<>(messageService.retrieveUserMessages(accountId), HttpStatus.OK);
    }

}
