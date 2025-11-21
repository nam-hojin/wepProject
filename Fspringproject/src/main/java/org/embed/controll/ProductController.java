package org.embed.controll;

import org.embed.dto.ProductDTO;
import org.embed.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class ProductController {

    private final ProductService service;

    @Autowired
    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping("/products.fo")
    public String list(Model model) {
        model.addAttribute("products", service.getAllProducts());
        return "product/productList";
    }

    @GetMapping("/products/create.fo")
    public String createForm(Model model) {
        model.addAttribute("product", new ProductDTO());
        return "product/productCreate";
    }

    @PostMapping("/products/create.fo")
    public String create(ProductDTO product) throws Exception {
        service.createProduct(product);
        return "redirect:/admin/products.fo";
    }

    @GetMapping("/products/detail.fo")
    public String detail(@RequestParam("id") int id, Model model) {
        model.addAttribute("product", service.getProductById(id));
        return "product/productDetail";
    }

    @GetMapping("/products/update.fo")
    public String updateForm(@RequestParam("id") int id, Model model) {
        model.addAttribute("product", service.getProductById(id));
        return "product/productUpdate";
    }

    @PostMapping("/products/update.fo")
    public String update(ProductDTO product, RedirectAttributes redirectAttributes) throws Exception {
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
