package com.sjaquatech.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDateTime;

@Entity
@Table(name = "service_bookings")
public class ServiceBooking {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank private String customerName;
    @NotBlank @Pattern(regexp = "[0-9+() -]{8,20}", message = "Enter a valid phone number")
    private String phone;
    @NotBlank private String serviceType;
    @NotBlank @Column(length = 1000) private String address;
    private LocalDateTime createdAt = LocalDateTime.now();
    private String status = "PENDING";

    public Long getId() { return id; }
    public String getCustomerName() { return customerName; }
    public void setCustomerName(String v) { customerName = v; }
    public String getPhone() { return phone; }
    public void setPhone(String v) { phone = v; }
    public String getServiceType() { return serviceType; }
    public void setServiceType(String v) { serviceType = v; }
    public String getAddress() { return address; }
    public void setAddress(String v) { address = v; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public String getStatus() { return status; }
    public void setStatus(String v) { status = v; }
}
