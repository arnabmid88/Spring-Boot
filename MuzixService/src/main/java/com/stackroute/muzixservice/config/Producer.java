package com.stackroute.muzixservice.config;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.stackroute.rabbitmq.domain.UserDTO;

@Component
public class Producer {
	
	private RabbitTemplate rabbitTemplate;
	private DirectExchange exchange;
	
	@Autowired
	public Producer(RabbitTemplate rabbitTemplate, DirectExchange exchange) {
		this.rabbitTemplate = rabbitTemplate;
		this.exchange = exchange;
	}
	
	public void sendMessageToRabbitMQ(UserDTO userDTO) {
		this.rabbitTemplate.convertAndSend(exchange.getName(), "user_routing", userDTO);
	}
	
	public void sendTrackToRabbitMQ(UserDTO userDTO) {
		this.rabbitTemplate.convertAndSend(exchange.getName(), "track_routing", userDTO);
	}

}
