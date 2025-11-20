package org.embed.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.embed.dto.CartDTO;
import org.embed.dto.CartItemDTO;

@Mapper
public interface CartMapper {

    // 카트 생성
    int insertCart(CartDTO cart);

    // 카트 아이템 추가
    int insertCartItem(CartItemDTO item);

    // 카트 아이템 삭제
    int deleteCartItem(@Param("cartId") Long cartId, @Param("productId") Integer productId);

    // 카트 조회
    CartDTO selectCartByUserId(@Param("userId") Integer userId);

    // 카트 아이템 목록 조회
    List<CartItemDTO> selectCartItems(@Param("cartId") Long cartId);

    // 카트 전체 비우기
    int deleteCartItems(@Param("cartId") Long cartId);
    
    
}
