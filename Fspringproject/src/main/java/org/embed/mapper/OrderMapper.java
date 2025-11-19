package org.embed.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.embed.dto.OrderDTO;
import org.embed.dto.OrderItemDTO;

@Mapper
public interface OrderMapper {
    void insertOrder(OrderDTO order);
    void insertOrderItem(OrderItemDTO item);
}
