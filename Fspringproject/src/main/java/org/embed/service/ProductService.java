package org.embed.service;

import java.util.List;

import org.embed.dto.ProductDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartHttpServletRequest;
@Service
public interface ProductService {
    List<ProductDTO> getAllProducts();
    ProductDTO getProductById(int productId);
    void createProduct(ProductDTO product,MultipartHttpServletRequest multipartHttpServletRequest) throws Exception;
    void updateProduct(ProductDTO product);
    void deleteProduct(int productId);
}
