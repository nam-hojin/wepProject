package org.embed.controll;

import java.util.ArrayList;
import java.util.List;

import org.embed.dto.ProductDTO;
import org.embed.fileutils.FileUtils;
import org.embed.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Controller
@RequestMapping("/admin")
public class ProductController {

    private final ProductService service;
    private final FileUtils fileUtils;

    @Autowired
    public ProductController(ProductService service, FileUtils fileUtils) {
        this.service = service;
        this.fileUtils = fileUtils;
    }

    @GetMapping("/products.fo")
    public String list(Model model) {
        model.addAttribute("products", service.getAllProducts());
        return "product/productList";
    }

    @GetMapping("/products/create.fo")
    public String createForm(Model model) {
        model.addAttribute("productDTO", new ProductDTO());
        return "product/productCreate";
    }

    @PostMapping("/products/create.fo")
    public String create(MultipartHttpServletRequest request,
                         @ModelAttribute ProductDTO productDTO) throws Exception {

        log.info("==> Product creation started");

        List<ProductDTO> uploadedFiles = fileUtils.parseFileInfo(productDTO, request);

        List<ProductDTO.ProductFileDTO> fileList = new ArrayList<>();
        if (uploadedFiles != null && !uploadedFiles.isEmpty()) {
            for (ProductDTO fileData : uploadedFiles) {
                byte[] imageBytes = fileData.getImageData();
                String mimeType = fileData.getImageType();
                if (imageBytes != null && imageBytes.length > 0) {
                    String base64Image = "data:" + mimeType + ";base64," +
                            java.util.Base64.getEncoder().encodeToString(imageBytes);
                    fileList.add(new ProductDTO.ProductFileDTO(imageBytes, mimeType, base64Image));
                }
            }
        }
        productDTO.setFiles(fileList);

        service.createProduct(productDTO,request);
       
        return "redirect:/admin/products.fo";
    }

    @GetMapping("/products/detail.fo")
    public String detail(@RequestParam("id") int id, Model model) {
        model.addAttribute("product", service.getProductById(id));
        return "product/productDetail";
    }

    @GetMapping("/products/update.fo")
    public String updateForm(@RequestParam("id") int id, Model model) {
        ProductDTO product = service.getProductById(id);
        if (product == null) {
            return "redirect:/admin/products.fo";
        }
        model.addAttribute("product", product);
        return "product/productUpdate";
    }

    @PostMapping("/products/update.fo")
    public String update(MultipartHttpServletRequest request,
                         @ModelAttribute ProductDTO product,
                         RedirectAttributes redirectAttributes) throws Exception {

        List<ProductDTO> uploadedFiles = fileUtils.parseFileInfo(product,request);
        List<ProductDTO.ProductFileDTO> fileList = new ArrayList<>();

        if (uploadedFiles != null && !uploadedFiles.isEmpty()) {
            for (ProductDTO fileData : uploadedFiles) {
                byte[] imageBytes = fileData.getImageData();
                String mimeType = fileData.getImageType();
                if (imageBytes != null && imageBytes.length > 0) {
                    String base64Image = "data:" + mimeType + ";base64," +
                            java.util.Base64.getEncoder().encodeToString(imageBytes);
                    fileList.add(new ProductDTO.ProductFileDTO(imageBytes, mimeType, base64Image));
                }
            }
            product.setFiles(fileList);
        } else {
            ProductDTO oldProduct = service.getProductById(product.getProductId());
            product.setFiles(oldProduct.getFiles());
        }

        service.updateProduct(product);
        redirectAttributes.addAttribute("id", product.getProductId());
        return "redirect:/admin/products/detail.fo";
    }

    @PostMapping("/products/delete.fo")
    public String delete(@RequestParam("id") int id) {
        service.deleteProduct(id);
        return "redirect:/admin/products.fo";
    }
}