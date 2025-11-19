package org.embed.controll;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CustomerController {

    @GetMapping("/customer.ko")
    public String customerPage() {
        // templates/customer/customer.html 위치라면
        return "customer/customer"; // 경로 구분자는 슬래시(/)
    }
    
    @GetMapping("/JuiceRecipes.ko")
    public String showJuiceRecipes() {
        // content 폴더 안의 JuiceRecipes.html 반환
        return "content/JuiceRecipes"; 
    }
    
    @GetMapping("/Health.ko")
    public String showVitaminInfo() {
        // templates/content/Health.html 반환
        return "content/Health"; 
    }
}