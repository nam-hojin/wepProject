package org.embed.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDTO {
	private Long orderId;
	private Integer userId;
	private String address;
	private Integer totalPrice;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	private List<OrderItemDTO> items;

	private String cardNumber;
	private String expiry;
	private String cvc;
}
