package org.embed.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.embed.dto.ProductDTO;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Mapper
public interface ProductMapper {
    List<ProductDTO> selectAll();
    ProductDTO selectById(int productId);
    void insertProduct(ProductDTO productDTO, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception;
    void updateProduct(ProductDTO product);
    void deleteProduct(int productId);
}