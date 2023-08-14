package com.easypeach.shroop.modules.auth.dto.request;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import javax.validation.constraints.NotBlank;

@EqualsAndHashCode
@Getter
public class SignUpRequest {

    @NotBlank
    private String loginId;

    @NotBlank
    private String password;

    @NotBlank
    private String nickname;

    @NotBlank
    private String phoneNumber;

    public SignUpRequest(){

    }

    public SignUpRequest(String loginId, String password, String nickname, String phoneNumber) {
        this.loginId = loginId;
        this.password = password;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
    }
}
