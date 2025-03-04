package com.rabbitmq.email.model;

import java.io.Serializable;

import lombok.Data;

@Data
//RabbitMQ requires the class to be Serializable
public class Email implements Serializable {
    private String email;
    private String title;
    private String message;
}