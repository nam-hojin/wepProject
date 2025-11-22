package org.embed.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
public class DeliveryInfoForm {
	private List<DeliveryInfoDTO> deliveryInfoDTOs;
}
