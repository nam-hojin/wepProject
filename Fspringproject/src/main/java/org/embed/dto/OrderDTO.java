package org.embed.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDTO {
    private Long orderId;
    private Long userId;
    private String recipientName;
    private String address;
    private String phone;
    private Integer totalPrice;
    private LocalDateTime orderDate;
    private List<OrderItemDTO> items;
}
