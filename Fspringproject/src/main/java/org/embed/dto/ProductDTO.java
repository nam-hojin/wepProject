package org.embed.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDTO {

	private int productId;
	private String name;
	private String category;
	private int price;
	private int stock;
	private String description;
	private String imageUrl;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	private MultipartFile imageFile;

	private List<ProductFileDTO> files = new ArrayList<>();

	@Getter
	@Setter
	public static class ProductFileDTO {
		private byte[] imageData;
		private String imageType;
		private String imageUrl;

		public ProductFileDTO(byte[] imageData, String imageType, String imageUrl) {
			this.imageData = imageData;
			this.imageType = imageType;
			this.imageUrl = imageUrl;
		}
	}
}
