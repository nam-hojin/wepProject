package org.embed.service;

import java.util.List;

import org.embed.dto.DeliveryInfoDTO;
import org.embed.dto.OrderDTO;
import org.embed.dto.OrderItemDTO;
import org.embed.mapper.OrderMapper;
import org.embed.service.OrderService;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderMapper orderMapper;

    public OrderServiceImpl(OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }

    @Override
    public void createOrder(List<DeliveryInfoDTO> deliveryList, Long userId) {
        // 주문 DTO 생성
        OrderDTO order = new OrderDTO();
        order.setUserId(userId);
        order.setOrderDate(java.time.LocalDateTime.now());

        int total = 0;
        List<OrderItemDTO> items = new java.util.ArrayList<>();
        for (DeliveryInfoDTO dto : deliveryList) {
            OrderItemDTO item = new OrderItemDTO();
            item.setProductId(dto.getProductId());
            item.setQuantity(dto.getQuantity());
            item.setPrice(10000); // 예시 가격
            item.setCreatedAt(java.time.LocalDateTime.now());
            item.setUpdatedAt(java.time.LocalDateTime.now());

            items.add(item);
            total += item.getPrice() * item.getQuantity();
        }

        order.setItems(items);
        order.setTotalPrice(total);

        // DB 저장
        orderMapper.insertOrder(order);
        for (OrderItemDTO item : items) {
            item.setOrderId(order.getOrderId()); // FK
            orderMapper.insertOrderItem(item);
        }
    }
}
