package com.easypeach.shroop.modules.auth.domain;

import lombok.Getter;
import org.springframework.security.core.parameters.P;

import javax.persistence.*;
import java.util.Random;
import java.util.UUID;

@Entity
@Getter
public class PhoneAuth {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "phone_number", length = 50, nullable = false)
    private String phoneNumber;

    @Column(name = "auth_number", length = 4,nullable = false)
    private String authNumber;

    @Column(name = "auth_result")
    private boolean authResult;

    public String createAuthNumber(){
        Random random = new Random();
        return String.format("%04d", random.nextInt(10000));
    }

    public static PhoneAuth createPhoneAuth(String phoneNumber){
        PhoneAuth phoneAuth = new PhoneAuth();
        phoneAuth.phoneNumber = phoneNumber;
        phoneAuth.authNumber = phoneAuth.createAuthNumber();
        phoneAuth.authResult = false;
        return phoneAuth;
    }

    public void updateAuthResult(boolean result){
        this.authResult = result;
    }
}
