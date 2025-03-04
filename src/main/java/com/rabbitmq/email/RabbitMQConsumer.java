package com.rabbitmq.email;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.email.model.Email;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class RabbitMQConsumer {

    @RabbitListener(queues = "emailQueue")
    public void receiveEmail(String emailJson) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            /*This configuration will prevent an exception if we receive
            json keys that the class properties does not have*/
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            Email email = objectMapper.readValue(emailJson, Email.class);
            log.info("Email object received is: " + email.toString());
        } catch(Exception e) {
            log.error("Recieved Exception during receiveEmail: ", e);
        }
    }
}