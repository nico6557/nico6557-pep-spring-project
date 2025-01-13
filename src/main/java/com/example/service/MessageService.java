package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.repository.MessageRepository;

@Service
public class MessageService {


    @Autowired
    private MessageRepository messageRepository;
    
    public List<Message> getAllMessages() {
        List<Message> x = messageRepository.findAll();
        return x;
        // TODO Auto-generated method stub
       
    }

    public Message createMessage(Message message) {
        Message x =  messageRepository.save(message);
        return x;
        // TODO Auto-generated method stub
    }


    public Message updateMessage(Integer messageId, Message messageUpdate) {
        if (messageRepository.existsById(messageId)) {

            if (messageUpdate.getMessageText().isEmpty()) {
                throw new IllegalArgumentException("Message text cannot be empty.");
            }
            if (messageUpdate.getMessageText().length() > 255) {
                throw new IllegalArgumentException("Message text exceeds the allowable limit.");
            }

            Message x = messageRepository.save(messageUpdate);
            return x;
        }
        return null;
        // TODO Auto-generated method stub
    }

    public Message getMessageById(Integer messageId) {
        Optional<Message> optional = messageRepository.findById(messageId);
        return optional.orElse(null);
        // TODO Auto-generated method stub

    }

    public List<Message> getAllMessagesByUser(Integer accountId) {
        List<Message> x = messageRepository.findAllByPosted(accountId);
        return x;
        // TODO Auto-generated method stub
    }

    public Message saveMessage(Message message) {
        // TODO Auto-generated method stub
        Message x = messageRepository.save(message);
        return x;
    }

    public int deleteMessageById(Integer messageId) {
        // TODO Auto-generated method stub
        return messageRepository.deleteMessageById(messageId);
    }


}
