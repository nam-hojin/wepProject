package org.embed.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.embed.dto.OrderDTO;
import org.embed.dto.OrderItemDTO;

@Mapper
public interface OrderMapper {

	void insertOrder(OrderDTO order);

	void insertOrderItem(OrderItemDTO item);

	Integer getProductPrice(@Param("productId") Integer productId);
}
