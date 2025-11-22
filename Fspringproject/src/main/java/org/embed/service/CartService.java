package org.embed.service;

import java.util.List;

import org.embed.dto.CartDTO;
import org.embed.dto.CartItemDTO;
import org.springframework.stereotype.Service;

@Service
public interface CartService {
	CartDTO getCartByUserId(Integer userId);

	void addItem(Integer userId, CartItemDTO item);

	void removeItem(Integer userId, Integer productId);

	void clearCart(Integer userId);
}
