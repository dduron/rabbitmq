package com.rabbitmq.email.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.email.model.Email;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmailService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    //This is the producer that will send a message to a queue
    public ResponseEntity<String> sendEmail(Email email) {
        try {
            //Converting email object to json for sending
            ObjectMapper objectMapper = new ObjectMapper();
            String emailJson = objectMapper.writeValueAsString(email);
            rabbitTemplate.convertAndSend("emailExchange", "emailRoutingKey", emailJson);
            log.info("Email json sent is: " + emailJson);
            return new ResponseEntity<>("Sent", HttpStatus.CREATED);
        } catch(Exception e) {
            log.error("Recieved Exception during sendEmail: ", e);
            return new ResponseEntity<>("Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
