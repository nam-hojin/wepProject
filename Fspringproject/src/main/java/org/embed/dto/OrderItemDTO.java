package org.embed.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemDTO {
	private Long orderItemId;
	private Long orderId;
	private Integer productId;
	private Integer quantity;
	private Integer price;
}
