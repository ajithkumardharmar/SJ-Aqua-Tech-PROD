
package com.sjaquatech.controller;

import com.sjaquatech.model.Product;
import com.sjaquatech.repository.*;
import com.sjaquatech.service.FileStorageService;

import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final ProductRepository products;
    private final ServiceBookingRepository bookings;
    private final ContactMessageRepository messages;
    private final FileStorageService fileStorageService;

    public AdminController(
            ProductRepository products,
            ServiceBookingRepository bookings,
            ContactMessageRepository messages,
            FileStorageService fileStorageService) {

        this.products = products;
        this.bookings = bookings;
        this.messages = messages;
        this.fileStorageService = fileStorageService;
    }

    @GetMapping("/login")
    public String login() {
        return "admin-login";
    }

    @GetMapping({"", "/"})
    public String dashboard(Model model) {
        model.addAttribute("productCount", products.count());
        model.addAttribute("bookingCount", bookings.count());
        model.addAttribute("messageCount", messages.count());

        return "admin/dashboard";
    }

    @GetMapping("/products")
    public String list(Model model) {
        model.addAttribute("products", products.findAll());
        return "admin/products";
    }

    @GetMapping("/products/new")
    public String create(Model model) {
        model.addAttribute("product", new Product());
        return "admin/product-form";
    }

    @PostMapping("/products/save")
    public String save(
            @Valid @ModelAttribute("product") Product product,
            BindingResult result,
            @RequestParam(value = "imageFile", required = false)
            MultipartFile imageFile,
            RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return "admin/product-form";
        }

        try {
            // Preserve existing image when editing without selecting a new one
            if (product.getId() != null) {
                Product existing = products.findById(product.getId())
                        .orElseThrow();

                if (imageFile == null || imageFile.isEmpty()) {
                    product.setImageUrl(existing.getImageUrl());
                }
            }

            // Upload new image if selected
            if (imageFile != null && !imageFile.isEmpty()) {
                String imagePath =
                        fileStorageService.saveImage(imageFile);

                product.setImageUrl(imagePath);
            }

            products.save(product);

            redirectAttributes.addFlashAttribute(
                    "successMessage",
                    "Product saved successfully."
            );

            return "redirect:/admin/products";

        } catch (IOException | IllegalArgumentException e) {

            redirectAttributes.addFlashAttribute(
                    "errorMessage",
                    "Image upload failed: " + e.getMessage()
            );

            return product.getId() == null
                    ? "redirect:/admin/products/new"
                    : "redirect:/admin/products/edit/" + product.getId();
        }
    }

    @GetMapping("/products/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute(
                "product",
                products.findById(id).orElseThrow()
        );

        return "admin/product-form";
    }

    @PostMapping("/products/delete/{id}")
    public String delete(
            @PathVariable Long id,
            RedirectAttributes redirectAttributes) {

        if (!products.existsById(id)) {
            redirectAttributes.addFlashAttribute(
                    "errorMessage",
                    "That product could not be found."
            );
        } else {
            products.deleteById(id);

            redirectAttributes.addFlashAttribute(
                    "successMessage",
                    "Product deleted successfully."
            );
        }

        return "redirect:/admin/products";
    }

    @GetMapping("/bookings")
    public String bookingList(Model model) {
        model.addAttribute("bookings", bookings.findAll());
        return "admin/bookings";
    }

    @PostMapping("/bookings/status/{id}")
    public String updateStatus(
            @PathVariable Long id,
            @RequestParam String status) {

        if (!java.util.Set.of(
                "PENDING",
                "CONFIRMED",
                "COMPLETED",
                "CANCELLED"
        ).contains(status)) {
            throw new IllegalArgumentException("Invalid booking status");
        }

        var booking = bookings.findById(id).orElseThrow();

        booking.setStatus(status);
        bookings.save(booking);

        return "redirect:/admin/bookings";
    }

    @GetMapping("/messages")
    public String messageList(Model model) {
        model.addAttribute("messages", messages.findAll());
        return "admin/messages";
    }
}
