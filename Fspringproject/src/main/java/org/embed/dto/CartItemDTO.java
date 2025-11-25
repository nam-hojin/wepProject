package org.embed.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemDTO {
	private Long cartItemId;
	private Long cartId;
	private Integer productId;
	private Integer quantity;
	private LocalDateTime addedAt;
}