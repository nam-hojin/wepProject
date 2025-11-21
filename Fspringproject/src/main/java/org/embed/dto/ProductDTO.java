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
    private String imageUrl; // 웹에서 접근할 수 있는 이미지 경로
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private MultipartFile imageFile; // 업로드할 파일

    // 여러 파일 업로드 지원
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
