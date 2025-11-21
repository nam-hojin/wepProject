package org.embed.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DeliveryInfoDTO {
    private Long itemId;       // CartItemId
    private Integer productId; // 상품 ID
    private Integer quantity;
    private String recipientName;
    private String address;
    private String phone;
    private Boolean selected = false; // 체크박스 선택 여부
}
