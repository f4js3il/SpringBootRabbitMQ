package com.rabbitMq.rabbitMq;

import Person.Person;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.springframework.util.SerializationUtils;

@Service
public class PersonReceiver {
//    @RabbitListener(queues={"AC","Mobile"})
//    public void getMessage(Person person){
//        System.out.println(person);
//    }

    @RabbitListener(queues={"AC","Mobile", "TV"})
    public void getMessage(byte[] message){
        Person  person = (Person)SerializationUtils.deserialize(message);
        System.out.println(person);
    }
}
