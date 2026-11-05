package com.sjaquatech.controller;

import com.sjaquatech.repository.ProductRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    private final ProductRepository products;
    public HomeController(ProductRepository products) { this.products = products; }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("products", products.findByActiveTrueOrderByIdDesc());
        return "index";
    }
    @GetMapping("/about") public String about() { return "about"; }
    @GetMapping("/services") public String services() { return "services"; }
}
