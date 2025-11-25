package org.embed.service;

import org.embed.dto.CartDTO;
import org.embed.dto.CartItemDTO;
import org.embed.dto.OrderDTO;
import org.embed.dto.OrderItemDTO;
import org.embed.mapper.CartMapper;
import org.embed.mapper.OrderMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

	private final OrderMapper orderMapper;
	private final CartMapper cartMapper;

	public OrderServiceImpl(OrderMapper orderMapper, CartMapper cartMapper) {
		this.orderMapper = orderMapper;
		this.cartMapper = cartMapper;
	}

	@Override
	public void createOrder(Integer userId, String address) {

		CartDTO cart = cartMapper.selectCartByUserId(userId);
		if (cart == null || cart.getItems() == null || cart.getItems().isEmpty()) {
			throw new IllegalStateException("장바구니가 비어있습니다.");
		}

		int total = cart.getItems().stream().mapToInt(i -> {
			Integer price = orderMapper.getProductPrice(i.getProductId());
			return (price != null ? price : 0) * i.getQuantity();
		}).sum();

		OrderDTO order = new OrderDTO();
		order.setUserId(userId);
		order.setAddress(address);
		order.setTotalPrice(total);

		orderMapper.insertOrder(order);

		for (CartItemDTO item : cart.getItems()) {
			Integer price = orderMapper.getProductPrice(item.getProductId());
			if (price == null)
				price = 0;

			OrderItemDTO orderItem = new OrderItemDTO();
			orderItem.setOrderId(order.getOrderId());
			orderItem.setProductId(item.getProductId());
			orderItem.setQuantity(item.getQuantity());
			orderItem.setPrice(price);

			orderMapper.insertOrderItem(orderItem);
		}

		cartMapper.deleteCartItems(cart.getCartId());
	}

	public boolean processPayment(OrderDTO order) {

		return true;
	}
}
