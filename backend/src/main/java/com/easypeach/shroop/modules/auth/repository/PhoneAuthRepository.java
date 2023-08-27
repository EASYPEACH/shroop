package com.easypeach.shroop.modules.auth.repository;


import com.easypeach.shroop.modules.auth.domain.PhoneAuth;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhoneAuthRepository extends JpaRepository<PhoneAuth, Long> {
}
