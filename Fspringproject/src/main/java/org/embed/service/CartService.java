package org.embed.service;

import java.util.List;

import org.embed.dto.CartDTO;
import org.embed.dto.CartItemDTO;
import org.springframework.stereotype.Service;
@Service
public interface CartService {
    CartDTO getCartByUserId(Integer userId); // DB에서 카트 조회
    void addItem(Integer userId, CartItemDTO item); // DB에 아이템 추가
    void removeItem(Integer userId, Integer productId); // DB에서 아이템 삭제
    void clearCart(Integer userId); // DB에서 전체 삭제
}
