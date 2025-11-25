package org.embed.controll;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.embed.dto.CartDTO;
import org.embed.dto.CartItemDTO;
import org.embed.dto.OrderDTO;
import org.embed.dto.ProductDTO;
import org.embed.dto.UsDTO;
import org.embed.service.CartService;
import org.embed.service.OrderService;
import org.embed.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/order")
public class OrderController {

	private final OrderService orderService;
	private final CartService cartService;
	private final ProductService productService;

	public OrderController(OrderService orderService, CartService cartService, ProductService productService) {
		this.orderService = orderService;
		this.cartService = cartService;
		this.productService = productService;
	}

	@GetMapping("/delivery")
	public String deliveryPage(HttpSession session, Model model) {
		UsDTO user = (UsDTO) session.getAttribute("loginUser");
		if (user == null)
			return "redirect:/login.to";

		CartDTO cart = cartService.getCartByUserId(user.getUserId());
		if (cart == null || cart.getItems() == null) {
			model.addAttribute("items", new ArrayList<>());
			model.addAttribute("totalPrice", 0);
			return "delivery/delivery";
		}

		int totalPrice = 0;
		List<Map<String, Object>> items = new ArrayList<>();

		for (CartItemDTO item : cart.getItems()) {
			ProductDTO p = productService.getProductById(item.getProductId());
			if (p == null)
				continue;

			Map<String, Object> map = new HashMap<>();
			map.put("productName", p.getName());
			map.put("price", p.getPrice());
			map.put("quantity", item.getQuantity());
			items.add(map);

			totalPrice += p.getPrice() * item.getQuantity();
		}

		model.addAttribute("items", items);
		model.addAttribute("totalPrice", totalPrice);

		return "delivery/delivery";
	}

	@PostMapping("/pay")
	public String pay(HttpSession session, @RequestParam("address") String address, @ModelAttribute OrderDTO order) {
		UsDTO user = (UsDTO) session.getAttribute("loginUser");
		if (user == null)
			return "redirect:/login.to";

		boolean success = true;

		if (success) {

			orderService.createOrder(user.getUserId(), address);

			cartService.clearCart(user.getUserId());

			return "delivery/confirm";
		} else {

			return "delivery/delivery";
		}
	}
}
