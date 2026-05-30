package com.fooddelivery.user_service.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.fooddelivery.user_service.dto.UserCreatedEvent;
import com.fooddelivery.user_service.entity.User;
import com.fooddelivery.user_service.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserCreatedConsumer {
	
	private final UserRepository userRepository;
	
	@KafkaListener(topics = "user.created", groupId = "user-service")
	public void consume(UserCreatedEvent event) {
		log.info("Received USER_CREATED event for userId={}", event.getId());

        if (userRepository.existsById(event.getId())) {
            log.warn("User already exists: {}", event.getId());
            return;
        }

        User user = User.builder()
                .id(event.getId())       // SAME UUID
                .email(event.getEmail())
                .name(event.getName())
                .role(event.getRole())
                .build();

        userRepository.save(user);

        log.info("User profile created for userId={}", event.getId());
	}

}
