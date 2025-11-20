package org.embed.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class OrderItemDTO {
    private Long orderItemId;
    private Long orderId;
    private Integer productId;
    private String productName;
    private Integer quantity;
    private Integer price;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
