package org.embed.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class CartDTO {
	 private Long cartId;                // 카트 ID
	    private Integer userId;             // 사용자 ID
	    private LocalDateTime createdAt;    // 생성일시
	    private LocalDateTime updatedAt;    // 수정일시
	    private List<CartItemDTO> items; 

  
}
