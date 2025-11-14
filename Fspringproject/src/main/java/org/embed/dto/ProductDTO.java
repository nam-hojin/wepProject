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
	 private int productId;          // product_id
	    private String name;            // name
	    private String category;        // category
	    private int price;       // price (DECIMAL)
	    private int stock;              // stock
	    private String description;     // description
	    private String imageUrl;        // image_url
	    private LocalDateTime createdAt; // created_at
	    private LocalDateTime updatedAt; // updated_at


    // 기존 단일 이미지 관련 필드 (하위 호환 가능)
    private byte[] imageData; // BLOB 컬럼에 저장
    private String imageType; // MIME 타입 (ex: image/png)
    private MultipartFile imageFile;
    private String originalFileName;   // 원본 파일명
    private long fileSize; 

    // 여러 파일 업로드를 위한 리스트
    private List<ProductFileDTO> files = new ArrayList<>();

    @Getter
    @Setter
    public static class ProductFileDTO {
        private byte[] imageData;
        private String imageType;
        private String imageUrl;
        private String originalFileName;
        private long fileSize;

        public ProductFileDTO(byte[] imageData, String imageType, String imageUrl) {
            this.imageData = imageData;
            this.imageType = imageType;
            this.imageUrl = imageUrl;
        }
    }
}