package org.embed.service;

import java.util.ArrayList;
import java.util.List;

import org.embed.dto.ProductDTO;
import org.embed.fileutils.FileUtils;
import org.embed.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductMapper mapper;
    
    @Autowired
    private FileUtils fileUtils;

    // 생성자 주입 방식
    public ProductServiceImpl(ProductMapper mapper) {
        this.mapper = mapper;
    }

    // 전체 상품 조회
    @Override
    public List<ProductDTO> getAllProducts() {
        return mapper.selectAll();
    }

    // 특정 상품 조회
    @Override
    public ProductDTO getProductById(int productId) {
        return mapper.selectById(productId);
    }

    // 상품 등록
    @Override
    public void createProduct(ProductDTO product, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception {
        // 파일 처리
        List<ProductDTO> uploadedFiles = fileUtils.parseFileInfo(product, multipartHttpServletRequest);

        // 파일 정보 리스트 준비
        List<ProductDTO.ProductFileDTO> fileList = new ArrayList<>();
        
        // 파일 업로드 처리
        if (uploadedFiles != null && !uploadedFiles.isEmpty()) {
            // 첫 번째 파일을 대표 이미지로 설정
            ProductDTO firstFile = uploadedFiles.get(0);
            product.setImageUrl(firstFile.getImageUrl());

            for (ProductDTO fileData : uploadedFiles) {
                byte[] imageBytes = fileData.getImageData();
                String mimeType = fileData.getImageType();

                if (imageBytes != null && imageBytes.length > 0) {
                    // Base64 인코딩된 이미지 정보 추가
                    String base64Image = "data:" + mimeType + ";base64," + java.util.Base64.getEncoder().encodeToString(imageBytes);
                    fileList.add(new ProductDTO.ProductFileDTO(imageBytes, mimeType, base64Image));
                }
            }
        }

        // 파일 정보 추가
        product.setFiles(fileList);

        // DB에 상품 정보 저장 (MultipartHttpServletRequest를 넘기지 않음)
        mapper.insertProduct(product);
    }

    // 상품 수정
    @Override
    public void updateProduct(ProductDTO product) {
        mapper.updateProduct(product);
    }

    // 상품 삭제
    @Override
    public void deleteProduct(int productId) {
        mapper.deleteProduct(productId);
    }
}
