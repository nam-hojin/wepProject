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
    private static final String SESSION_DELIVERY = "deliveryInfoForm";

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
        session.setAttribute(SESSION_DELIVERY, form); // 세션 저장
        return "delivery/delivery";
    }

    // 2. 배송정보 입력 → 주문 완료
    @PostMapping("/order/delivery")
    public String processDelivery(@ModelAttribute DeliveryInfoForm form, HttpSession session) {

        // 로그인 유저 가져오기 (예제)
        Long userId = 1L;

        // 세션에서 DeliveryInfoForm 가져오기
        DeliveryInfoForm sessionForm = (DeliveryInfoForm) session.getAttribute(SESSION_DELIVERY);

        // 세션에 값이 없으면 새로운 폼 생성
        if (sessionForm == null || sessionForm.getDeliveryInfoDTOs() == null) {
            sessionForm = new DeliveryInfoForm();
            sessionForm.setDeliveryInfoDTOs(new ArrayList<>());
        }

        List<DeliveryInfoDTO> sessionDeliveryList = sessionForm.getDeliveryInfoDTOs();
        List<DeliveryInfoDTO> inputList = form.getDeliveryInfoDTOs();

        // 세션 리스트가 비어있으면 입력값으로 초기화
        if (sessionDeliveryList.isEmpty() && inputList != null) {
            sessionDeliveryList.addAll(inputList);
        } else if (inputList != null) {
            // 기존 리스트 덮어쓰기
            for (int i = 0; i < inputList.size(); i++) {
                DeliveryInfoDTO input = inputList.get(i);
                if (i < sessionDeliveryList.size()) {
                    DeliveryInfoDTO sessionDto = sessionDeliveryList.get(i);
                    sessionDto.setRecipientName(input.getRecipientName());
                    sessionDto.setAddress(input.getAddress());
                    sessionDto.setPhone(input.getPhone());
                } else {
                    sessionDeliveryList.add(input);
                }
            }
        }

        // 세션에 업데이트
        session.setAttribute(SESSION_DELIVERY, sessionForm);

        // 주문 처리
        orderService.createOrder(sessionDeliveryList, userId);

        return "redirect:/order/complete";
    }

    // 3. 주문 완료 페이지
    @GetMapping("/order/complete")
    public String showOrderCompletePage() {
        return "delivery/confirm";
    }
}
