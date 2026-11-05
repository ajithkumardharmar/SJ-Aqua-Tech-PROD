package com.sjaquatech.controller;

import com.sjaquatech.repository.ProductRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ProductController {
    private final ProductRepository products;
    public ProductController(ProductRepository products) { this.products = products; }

    @GetMapping("/products")
    public String list(Model model) {
        model.addAttribute("products", products.findByActiveTrueOrderByIdDesc());
        return "products";
    }
    @GetMapping("/products/{id}")
    public String detail(@PathVariable Long id, Model model) {
        model.addAttribute("product", products.findById(id).orElseThrow());
        return "product-detail";
    }
}
