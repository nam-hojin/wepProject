package org.embed.controll;

import java.util.HashMap;
import java.util.Map;

import org.embed.dto.CartDTO;
import org.embed.dto.CartItemDTO;
import org.embed.dto.ProductDTO;
import org.embed.dto.UsDTO;
import org.embed.service.CartService;
import org.embed.service.ProductService;
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
    private final ProductService productService;

    public CartController(CartService cartService, ProductService productService) {
        this.cartService = cartService;
        this.productService = productService;
    }

    // 장바구니 메인 페이지
    @GetMapping({"", "/main", "/list"})
    public String cartMain(HttpSession session, Model model) {
        UsDTO loginUser = (UsDTO) session.getAttribute("loginUser");
        if (loginUser == null) {
            return "redirect:/login.to";
        }

        Integer userId = loginUser.getUserId();
        CartDTO cart = cartService.getCartByUserId(userId);

        Map<Integer, ProductDTO> productMap = new HashMap<>();
        if (cart.getItems() != null) {
            for (CartItemDTO item : cart.getItems()) {
                ProductDTO product = productService.getProductById(item.getProductId());
                if (product != null) {
                    productMap.put(item.getProductId(), product);
                }
            }
        }

        model.addAttribute("cart", cart);
        model.addAttribute("productMap", productMap);

        return "cart/cartMain";
    }

    // 장바구니에 상품 추가
    @PostMapping("/add")
    public String addCart(HttpSession session,
                          @RequestParam("productId") Integer productId,
                          @RequestParam(name = "quantity", defaultValue = "1") Integer quantity) {
        UsDTO loginUser = (UsDTO) session.getAttribute("loginUser");
        if (loginUser == null) {
            return "redirect:/login.to";
        }

        CartItemDTO item = new CartItemDTO();
        item.setProductId(productId);
        item.setQuantity(quantity);

        cartService.addItem(loginUser.getUserId(), item);
        return "redirect:/cart/main";
    }

    // 장바구니에서 특정 상품 제거
    @PostMapping("/remove")
    public String removeCart(HttpSession session,
                             @RequestParam("productId") Integer productId) {
        UsDTO loginUser = (UsDTO) session.getAttribute("loginUser");
        if (loginUser == null) {
            return "redirect:/login.to";
        }

        cartService.removeItem(loginUser.getUserId(), productId);
        return "redirect:/cart/main";
    }

    // 장바구니 전체 비우기
    @PostMapping("/clear")
    public String clearCart(HttpSession session) {
        UsDTO loginUser = (UsDTO) session.getAttribute("loginUser");
        if (loginUser == null) {
            return "redirect:/login.to";
        }

        cartService.clearCart(loginUser.getUserId());
        return "redirect:/cart/main";
    }
}
