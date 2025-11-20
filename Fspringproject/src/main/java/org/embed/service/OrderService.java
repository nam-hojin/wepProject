package org.embed.service;

import java.util.List;
import org.embed.dto.DeliveryInfoDTO;
import org.springframework.stereotype.Service;
@Service
public interface OrderService {
    void createOrder(List<DeliveryInfoDTO> deliveryList, Long userId);
}
