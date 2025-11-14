package org.embed.service;

import java.util.List;

import org.embed.dto.ProductDTO;
import org.embed.dto.ProductDTO.ProductFileDTO;
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

    public ProductServiceImpl(ProductMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        return mapper.selectAll();
    }

    @Override
    public ProductDTO getProductById(int productId) {
    	
    	return mapper.selectById(productId);
    }

    @Override
    public void createProduct(ProductDTO product, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception {
    	List<ProductDTO> list = fileUtils.parseFileInfo(product, multipartHttpServletRequest);
    }
    @Override
    public void updateProduct(ProductDTO product) {
        mapper.updateProduct(product);
    }

    @Override
    public void deleteProduct(int productId) {
        mapper.deleteProduct(productId);
    }
}
