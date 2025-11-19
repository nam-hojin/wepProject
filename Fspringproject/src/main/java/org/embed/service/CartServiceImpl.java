package org.embed.service;
import java.util.Iterator;

import org.embed.dto.CartDTO;
import org.embed.dto.CartItemDTO;
import org.embed.service.CartService;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService {

    @Override
    public void addItem(CartDTO cart, CartItemDTO item) {
        cart.getItems().add(item);
    }

    @Override
    public void removeItem(CartDTO cart, Integer productId) {
        Iterator<CartItemDTO> iterator = cart.getItems().iterator();
        while (iterator.hasNext()) {
            CartItemDTO item = iterator.next();
            if (item.getProductId().equals(productId)) {
                iterator.remove();
                break;
            }
        }
    }

    @Override
    public void clearCart(CartDTO cart) {
        cart.getItems().clear();
    }
}
