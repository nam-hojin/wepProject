package org.embed.controll;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.embed.dto.ProductDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/wishlist")
public class WishlistController {

    private static final String SESSION_WISHLIST = "SESSION_WISHLIST";

    @PostMapping("/add")
    public String addToWishlist(@RequestParam int productId, HttpSession session) {
        List<ProductDTO> wishlist = (List<ProductDTO>) session.getAttribute(SESSION_WISHLIST);
        if (wishlist == null) {
            wishlist = new ArrayList<>();
        }

        boolean exists = wishlist.stream().anyMatch(p -> p.getProductId() == productId);
        if (!exists) {
            ProductDTO product = new ProductDTO();
            product.setProductId(productId);
            product.setName("상품명 " + productId);
            product.setPrice(1000 * productId);
            product.setCreatedAt(LocalDateTime.now());

            wishlist.add(product);
        }

        session.setAttribute(SESSION_WISHLIST, wishlist);
        return "redirect:/cart/list";
    }

    @PostMapping("/remove")
    public String removeFromWishlist(@RequestParam int productId, HttpSession session) {
        List<ProductDTO> wishlist = (List<ProductDTO>) session.getAttribute(SESSION_WISHLIST);
        if (wishlist != null) {
            wishlist.removeIf(p -> p.getProductId() == productId);
        }
        session.setAttribute(SESSION_WISHLIST, wishlist);
        return "redirect:/cart/list";
    }

    @PostMapping("/clear")
    public String clearWishlist(HttpSession session) {
        session.removeAttribute(SESSION_WISHLIST);
        return "redirect:/cart/list";
    }
}
