package org.embed.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
	public class CartItemDTO {
	  private Long cartItemId;     // 카트 아이템 ID
	    private Long cartId;         // 카트 ID
	    private Integer productId;   // 상품 ID
	    private Integer quantity;    // 수량
	    private LocalDateTime addedAt; // 추가일시
}