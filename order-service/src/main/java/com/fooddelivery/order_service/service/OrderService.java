package com.fooddelivery.order_service.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fooddelivery.order_service.dto.CreateOrderRequest;
import com.fooddelivery.order_service.dto.FoodResponse;
import com.fooddelivery.order_service.dto.OrderStatus;
import com.fooddelivery.order_service.entity.Order;
import com.fooddelivery.order_service.feignclient.FoodClient;
import com.fooddelivery.order_service.feignclient.PaymentClient;
import com.fooddelivery.order_service.feignclient.RestaurantClient;
import com.fooddelivery.order_service.feignclient.UserClient;
import com.fooddelivery.order_service.kafka.OrderEvent;
import com.fooddelivery.order_service.kafka.OrderProducer;
import com.fooddelivery.order_service.repository.OrderRepository;

import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {

	private final OrderRepository repository;

	private final FoodClient foodClient;

	private final RestaurantClient restaurantClient;

	private final UserClient userClient;

	private final PaymentClient paymentClient;

	private final OrderProducer orderProducer;

	public Order create(String userid, CreateOrderRequest req) {

		userClient.me(userid);

		restaurantClient.getRestaurent(req.getRestaurantId());

		List<FoodResponse> foods = foodClient.getFoods(req.getRestaurantId());

		double total = foods.stream().filter(f -> req.getFoodIds().contains(f.getId()))
				.mapToDouble(FoodResponse::getPrice).sum();

		Order order = Order.builder().userId(userid).restaurantId(req.getRestaurantId()).totalAmount(total)
				.Status(OrderStatus.CREATED).createdAt(LocalDateTime.now()).build();


		// Save first so ID is generated
		Order savedOrder = repository.save(order);

		// Now the ID is available
		OrderEvent orderEvent = OrderEvent.builder().orderId(savedOrder.getId()).status("CREATED").userId(userid)
				.build();

		orderProducer.publishOrderEvent(orderEvent);
		
		System.out.println("order id" + order.getId());

		return savedOrder;

	}

	public List<Order> getUseOrders(String userid) {

		return repository.findByUserId(userid);

	}

	public void updateStatus(long orderId, OrderStatus status) {
		// TODO Auto-generated method stub

		Order order = repository.findById(orderId).orElseThrow(() -> new NotFoundException("order not found"));

		order.setStatus(status);

		repository.save(order);

	}

	public void cancelOrder(Long orderId) {

		Order order = repository.findById(orderId).orElseThrow(() -> new NotFoundException("order not found"));

		order.setStatus(OrderStatus.CANCELLED);
		repository.save(order);

		paymentClient.refund(orderId);
	}

}
