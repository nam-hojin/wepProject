package org.embed.service;

import java.util.List;

import org.embed.dto.ProductDTO;
import org.embed.fileutils.FileUtils;
import org.embed.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductMapper mapper;
    private final FileUtils fileUtils;

    @Autowired
    public ProductServiceImpl(ProductMapper mapper, FileUtils fileUtils) {
        this.mapper = mapper;
        this.fileUtils = fileUtils;
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
    public void createProduct(ProductDTO product) throws Exception {
        if (product.getImageFile() != null && !product.getImageFile().isEmpty()) {
            String imageUrl = fileUtils.saveFile(product);
            product.setImageUrl(imageUrl);
        }
        mapper.insertProduct(product);
    }

    @Override
    public void updateProduct(ProductDTO product) throws Exception {
        if (product.getImageFile() != null && !product.getImageFile().isEmpty()) {
            String imageUrl = fileUtils.saveFile(product);
            product.setImageUrl(imageUrl);
        }
        mapper.updateProduct(product);
    }

    @Override
    public void deleteProduct(int productId) {
        mapper.deleteProduct(productId);
    }
}
