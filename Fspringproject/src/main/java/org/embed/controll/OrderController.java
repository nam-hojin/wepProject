package org.embed.controll;

import java.util.ArrayList;
import java.util.List;

import org.embed.dto.CartDTO;
import org.embed.dto.CartItemDTO;
import org.embed.dto.DeliveryInfoDTO;
import org.embed.dto.DeliveryInfoForm;
import org.embed.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class OrderController {

    private final OrderService orderService;
    private static final String SESSION_CART = "SESSION_CART";

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // 1. 배송정보 입력 페이지
    @GetMapping("/order/delivery")
    public String showDeliveryPage(HttpSession session, Model model) {
        CartDTO cart = (CartDTO) session.getAttribute(SESSION_CART);
        if (cart == null) cart = new CartDTO();

        List<CartItemDTO> cartItems = cart.getItems();
        if (cartItems == null) cartItems = new ArrayList<>();

        List<DeliveryInfoDTO> deliveryInfoDTOs = new ArrayList<>();
        for (CartItemDTO item : cartItems) {
            DeliveryInfoDTO dto = new DeliveryInfoDTO();
            dto.setItemId(item.getCartItemId());
            dto.setProductId(item.getProductId());
            dto.setQuantity(item.getQuantity());
            deliveryInfoDTOs.add(dto);
        }

        DeliveryInfoForm form = new DeliveryInfoForm();
        form.setDeliveryInfoDTOs(deliveryInfoDTOs);

        model.addAttribute("deliveryInfoForm", form);
        session.setAttribute("deliveryInfoForm", form); // 세션 저장
        return "delivery/delivery"; // Thymeleaf 뷰
    }

    // 2. 배송정보 입력 → 주문 완료
    @PostMapping("/order/delivery")
    public String processDelivery(@ModelAttribute DeliveryInfoForm form, HttpSession session) {

        // 로그인 유저 가져오기 (예제)
        Long userId = 1L;

        // 세션에서 CartItem 정보 가져오기
        DeliveryInfoForm sessionForm = (DeliveryInfoForm) session.getAttribute("deliveryInfoForm");
        List<DeliveryInfoDTO> sessionDeliveryList = sessionForm.getDeliveryInfoDTOs();

        // 입력 값 덮어쓰기
        List<DeliveryInfoDTO> inputList = form.getDeliveryInfoDTOs();
        for (int i = 0; i < inputList.size(); i++) {
            DeliveryInfoDTO input = inputList.get(i);
            DeliveryInfoDTO sessionDto = sessionDeliveryList.get(i);
            sessionDto.setRecipientName(input.getRecipientName());
            sessionDto.setAddress(input.getAddress());
            sessionDto.setPhone(input.getPhone());
        }

        // 주문 처리
        orderService.createOrder(sessionDeliveryList, userId);

        return "redirect:/order/complete";
    }

    // 3. 주문 완료 페이지
    @GetMapping("/order/complete")
    public String showOrderCompletePage() {
        return "delivery/confirm"; // templates/delivery/confirm.html
    }
}
