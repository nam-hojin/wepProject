package org.embed.service;

import java.time.LocalDateTime;
import java.util.List;

import org.embed.dto.CartDTO;
import org.embed.dto.CartItemDTO;
import org.embed.mapper.CartMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartMapper cartMapper;

    @Override
    public CartDTO getCartByUserId(Integer userId) {
        CartDTO cart = cartMapper.selectCartByUserId(userId);
        if (cart == null) {
            cart = new CartDTO();
            cart.setUserId(userId);
            cart.setCreatedAt(LocalDateTime.now());
            cart.setUpdatedAt(LocalDateTime.now());
            cartMapper.insertCart(cart); // 새 카트 생성
        }
        List<CartItemDTO> items = cartMapper.selectCartItems(cart.getCartId());
        cart.setItems(items);
        return cart;
    }

    @Override
    public void addItem(Integer userId, CartItemDTO item) {
        CartDTO cart = getCartByUserId(userId);
        item.setCartId(cart.getCartId());

        // 기존에 같은 상품이 있는지 확인
        CartItemDTO existingItem = cart.getItems().stream()
                .filter(i -> i.getProductId().equals(item.getProductId()))
                .findFirst().orElse(null);

        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + item.getQuantity());
            existingItem.setAddedAt(LocalDateTime.now());
            cartMapper.deleteCartItem(cart.getCartId(), item.getProductId()); // 기존 삭제
        }
        item.setAddedAt(LocalDateTime.now());
        cartMapper.insertCartItem(item);
    }

    @Override
    public void removeItem(Integer userId, Integer productId) {
        CartDTO cart = getCartByUserId(userId);
        cartMapper.deleteCartItem(cart.getCartId(), productId);
    }

    @Override
    public void clearCart(Integer userId) {
        CartDTO cart = getCartByUserId(userId);
        cartMapper.deleteCartItems(cart.getCartId());
    }
}
