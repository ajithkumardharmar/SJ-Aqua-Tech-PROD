package com.sjaquatech.controller;

import com.sjaquatech.model.ServiceBooking;
import com.sjaquatech.repository.ServiceBookingRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class BookingController {
    private final ServiceBookingRepository bookings;
    public BookingController(ServiceBookingRepository bookings) { this.bookings = bookings; }

    @GetMapping("/booking")
    public String form(Model model) {
        model.addAttribute("booking", new ServiceBooking());
        return "booking";
    }
    @PostMapping("/booking")
    public String submit(@Valid @ModelAttribute("booking") ServiceBooking booking,
                         BindingResult result, Model model) {
        if (result.hasErrors()) return "booking";
        bookings.save(booking);
        model.addAttribute("success", "Your service request has been received.");
        model.addAttribute("booking", new ServiceBooking());
        return "booking";
    }
}
