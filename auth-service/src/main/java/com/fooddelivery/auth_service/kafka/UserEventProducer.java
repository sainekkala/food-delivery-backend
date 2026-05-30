package com.fooddelivery.auth_service.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.fooddelivery.auth_service.dto.UserCreatedEvent;
import com.fooddelivery.auth_service.entity.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserEventProducer {

    private static final String TOPIC = "user.created";

    private final KafkaTemplate<String, UserCreatedEvent> kafkaTemplate;

    public void publishUserCreated(User user) {

        UserCreatedEvent event = new UserCreatedEvent(
                user.getId(),
                user.getEmail(),
                user.getName(),
                user.getRole()
        );

        kafkaTemplate.send(TOPIC, user.getId(), event);

        log.info("Published USER_CREATED event for userId={}", user.getId());
    }
}
