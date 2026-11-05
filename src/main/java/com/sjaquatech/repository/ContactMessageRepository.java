package com.sjaquatech.repository;
import com.sjaquatech.model.ContactMessage;
import org.springframework.data.jpa.repository.JpaRepository;
public interface ContactMessageRepository extends JpaRepository<ContactMessage, Long> {}
