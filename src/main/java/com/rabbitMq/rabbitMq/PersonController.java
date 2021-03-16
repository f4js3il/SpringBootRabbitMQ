package com.rabbitMq.rabbitMq;

import Person.Person;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.SerializationUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1")
public class PersonController {

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public PersonController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @GetMapping("/test/{name}")
    public String getName(@PathVariable String name){

        Person person = new Person(1L,name);
        rabbitTemplate.convertAndSend("Mobile",person);
        rabbitTemplate.convertAndSend("Direct-Exchange","mobile",person);
        rabbitTemplate.convertAndSend("FanOut","",person);
        rabbitTemplate.convertAndSend("Topic","tv.mobile.ac",person);

        return  "Success";
    }

    @GetMapping("/test/headers/{name}")
    public String getNameFromHeaderExchange(@PathVariable String name){

        Person person = new Person(1L,name);
        byte[] messageBody = SerializationUtils.serialize(person);
        Message message = MessageBuilder.withBody(messageBody)
                .setHeader("item2","television")
                .setHeader("item1","mobile")
                .build();
        rabbitTemplate.send("Headers","",message);
        return  "Success";
    }
}
