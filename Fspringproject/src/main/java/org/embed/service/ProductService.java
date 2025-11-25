package org.embed.service;

import java.util.List;

import org.embed.dto.ProductDTO;
import org.springframework.web.multipart.MultipartHttpServletRequest;

public interface ProductService {

	List<ProductDTO> getAllProducts();

	ProductDTO getProductById(int productId);

	void createProduct(ProductDTO product) throws Exception;

	void updateProduct(ProductDTO product) throws Exception;

	void deleteProduct(int productId);
}
