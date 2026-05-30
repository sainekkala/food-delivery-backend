package com.fooddelivery.order_service.kafka;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderEvent {

	private Long orderId;
    private String status;
    private String userId;
}
