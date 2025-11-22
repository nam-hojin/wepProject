package org.embed.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartDTO {
	private Long cartId;
	private Integer userId;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private List<CartItemDTO> items;

}
