package com.fooddelivery.order_service.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OrderProducer {
	
	private static final String TOPIC = "order-events";
	
	private final KafkaTemplate<String, Object> kafkaTemplet;
	
	public void publishOrderEvent(OrderEvent event) {
		kafkaTemplet.send(TOPIC,event.getOrderId().toString(),event);
	}
		
		

}
