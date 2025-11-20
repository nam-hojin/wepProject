package org.embed.service;

import java.util.List;

import org.embed.dto.CartDTO;
import org.embed.dto.CartItemDTO;
import org.springframework.stereotype.Service;
@Service
public interface CartService {
    void addItem(CartDTO cart, CartItemDTO item);
    void removeItem(CartDTO cart, Integer productId);
    void clearCart(CartDTO cart);
  
}
