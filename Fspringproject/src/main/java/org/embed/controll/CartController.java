package org.embed.controll;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.embed.dto.CartDTO;
import org.embed.dto.CartItemDTO;
import org.embed.dto.ProductDTO;
import org.embed.service.CartService;
import org.embed.service.ProductService; // 상품 정보 조회용 서비스
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;
    private final ProductService productService; // 상품 조회용
    private static final String SESSION_CART = "SESSION_CART";

    public CartController(CartService cartService, ProductService productService) {
        this.cartService = cartService;
        this.productService = productService;
    }

    // 장바구니 메인 / 리스트 모두 cartMain.html 사용
    @GetMapping({"/list", "/main"})
    public String cartList(HttpSession session, Model model) {
        CartDTO cart = (CartDTO) session.getAttribute(SESSION_CART);
        if (cart == null) {
            cart = new CartDTO();
            cart.setCreatedAt(LocalDateTime.now());
            cart.setItems(new ArrayList<>());
            session.setAttribute(SESSION_CART, cart);
        }

        // 상품 정보를 가져와 Map으로 모델에 추가
        Map<Integer, ProductDTO> productMap = new HashMap<>();
        for (CartItemDTO item : cart.getItems()) {
            ProductDTO product = productService.getProductById(item.getProductId());
            productMap.put(item.getProductId(), product);
        }

        model.addAttribute("cart", cart);
        model.addAttribute("productMap", productMap);

        return "cart/cartMain"; // templates/cart/cartMain.html
    }

    @PostMapping("/add")
    public String addCart(@RequestParam("productId") Integer productId,
                          @RequestParam(name = "quantity", defaultValue = "1") Integer quantity,
                          HttpSession session) {

        CartDTO cart = (CartDTO) session.getAttribute(SESSION_CART);
        if (cart == null) {
            cart = new CartDTO();
            cart.setCreatedAt(LocalDateTime.now());
            cart.setItems(new ArrayList<>());
            session.setAttribute(SESSION_CART, cart);
        }

        CartItemDTO existingItem = cart.getItems().stream()
                .filter(item -> item.getProductId().equals(productId))
                .findFirst()
                .orElse(null);

        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
            existingItem.setAddedAt(LocalDateTime.now());
        } else {
            CartItemDTO item = new CartItemDTO();
            item.setCartItemId(System.currentTimeMillis());
            item.setCartId(cart.getCartId());
            item.setProductId(productId);
            item.setQuantity(quantity);
            item.setAddedAt(LocalDateTime.now());

            cart.getItems().add(item);
        }

        return "redirect:/cart/main"; // cartMain.html로 이동
    }

    @PostMapping("/remove")
    public String removeCart(@RequestParam("productId") Integer productId, HttpSession session) {
        CartDTO cart = (CartDTO) session.getAttribute(SESSION_CART);
        if (cart != null && productId != null) {
            // CartService의 removeItem 메소드 호출
            cartService.removeItem(cart, productId);
        }
        return "redirect:/cart/main";
    }
    @PostMapping("/clear")
    public String clearCart(HttpSession session) {
        CartDTO cart = (CartDTO) session.getAttribute(SESSION_CART);
        if (cart != null) {
            cartService.clearCart(cart);
        }
        return "redirect:/cart/main";
    }
}
