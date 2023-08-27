package com.easypeach.shroop.modules.auth.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
// 폰 인증을 요청할때 전달하는 DTO
@Getter
@NoArgsConstructor
public class PhoneAuthRequest {

    private long uuid;

    private String phoneNumber;

    private String phoneAuthNumber;

    public PhoneAuthRequest(Long uuid, String phoneNumber, String phoneAuthNumber) {
        this.uuid = uuid;
        this.phoneNumber = phoneNumber;
        this.phoneAuthNumber = phoneAuthNumber;
    }
}
