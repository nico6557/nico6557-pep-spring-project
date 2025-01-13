package com.example.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.service.MessageService;

@RestController

public class MessageController {
    
    
    @Autowired
    private MessageService messageService;
    @Autowired
    private AccountRepository accountRepository;

    @PostMapping("/messages")
    public ResponseEntity<Message> createMessage(@RequestBody Message message) {
        if (message.getMessageText().trim().isEmpty() || message.getMessageText().length() > 254) {
            return ResponseEntity.badRequest().build();
        } else if (!accountRepository.existsById(message.getPostedBy())) {
            return ResponseEntity.badRequest().build();
        }

        Message createdMessage = messageService.createMessage(message);
        
        return ResponseEntity.ok(createdMessage);

    } 

    @PatchMapping("/messages/{messageId}")
    public ResponseEntity<?> updateMessage(@PathVariable Integer messageId, @RequestBody Message messageUpdate) {
        try {
            
            if (messageUpdate.getMessageText().trim().isEmpty() || messageUpdate.getMessageText().length() > 255) {
                return ResponseEntity.badRequest().body("Message text is either empty or exceeds the allowable limit.");
            }
            
            Message message = messageService.getMessageById(messageId);

            if (message == null) {
                return new ResponseEntity<> (messageId, HttpStatus.NOT_FOUND);
            }
            Message updatedMessage = messageService.updateMessage(messageId, messageUpdate);

            if (updatedMessage == null) {
                return new ResponseEntity<> (messageId, HttpStatus.NOT_FOUND);
            }
            return ResponseEntity.ok(1); 

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages() {

        List<Message> messages = messageService.getAllMessages();

        if (messages.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(messages);
    }
    
    @GetMapping("/messages/{messageId}")
    public ResponseEntity<Message> getMessageById(@PathVariable Integer messageId) {

        Message message = messageService.getMessageById(messageId);

        if (message == null) {
            return ResponseEntity.ok().build(); 
        }

        return ResponseEntity.ok(message);
    }
    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> getAllMessagesByUser(@PathVariable Integer accountId) {

        List<Message> messages = messageService.getAllMessagesByUser(accountId);

        if (messages.isEmpty()) {
            return ResponseEntity.ok(messages);
        }

        return ResponseEntity.ok(messages);
    }

    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<Integer> deleteMessageById(@PathVariable Integer messageId) {

        int rowsDeleted = messageService.deleteMessageById(messageId);
        
        if (messageId == null) {
            return ResponseEntity.ok().build(); 
        }

        return ResponseEntity.ok(rowsDeleted);
    }

    


}
