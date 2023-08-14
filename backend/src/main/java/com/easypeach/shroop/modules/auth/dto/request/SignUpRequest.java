package com.easypeach.shroop.modules.auth.dto.request;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@EqualsAndHashCode
@Getter
public class SignUpRequest {

    @NotBlank
    @Size(min = 5, max = 255,message = "최소 5자 이상 입력해주세요")

    private String loginId;

    @NotBlank
    @Size(min = 5, max = 255,message = "최소 5자 이상 입력해주세요")
    private String password;

    @NotBlank
    @Size(min = 2, max = 255,message = "최소 2자 이상 입력해주세요")
    private String nickname;

    @NotBlank
    @Pattern(regexp = "^010\\d{7,8}$")
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
