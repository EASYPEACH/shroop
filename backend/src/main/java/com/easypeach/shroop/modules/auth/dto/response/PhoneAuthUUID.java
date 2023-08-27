package com.easypeach.shroop.modules.auth.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
// 폰 인증 번호를 전송하고, 개인 UUID 발급하는 DTO
@Getter
@NoArgsConstructor
public class PhoneAuthUUID {
    private Long UUID;

    public PhoneAuthUUID(Long uuid) {
        this.UUID = uuid;
    }
}
