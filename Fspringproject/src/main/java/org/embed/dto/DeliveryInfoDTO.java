package org.embed.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DeliveryInfoDTO {
	private Long itemId;
	private Integer productId;
	private Integer quantity;
	private String recipientName;
	private String address;
	private String phone;
	private Boolean selected = false;
}
