package com.sjaquatech.controller;

import com.sjaquatech.model.ContactMessage;
import com.sjaquatech.repository.ContactMessageRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class ContactController {
    private final ContactMessageRepository messages;
    public ContactController(ContactMessageRepository messages) { this.messages = messages; }

    @GetMapping("/contact")
    public String form(Model model) {
        model.addAttribute("contact", new ContactMessage());
        return "contact";
    }
    @PostMapping("/contact")
    public String submit(@Valid @ModelAttribute("contact") ContactMessage contact,
                         BindingResult result, Model model) {
        if (result.hasErrors()) return "contact";
        messages.save(contact);
        model.addAttribute("success", "Your message has been sent successfully.");
        model.addAttribute("contact", new ContactMessage());
        return "contact";
    }
}
