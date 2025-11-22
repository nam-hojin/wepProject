package org.embed.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.embed.dto.CartDTO;
import org.embed.dto.CartItemDTO;

@Mapper
public interface CartMapper {

	int insertCart(CartDTO cart);

	int insertCartItem(CartItemDTO item);

	int deleteCartItem(@Param("cartId") Long cartId, @Param("productId") Integer productId);

	CartDTO selectCartByUserId(@Param("userId") Integer userId);

	List<CartItemDTO> selectCartItems(@Param("cartId") Long cartId);

	int deleteCartItems(@Param("cartId") Long cartId);

}
