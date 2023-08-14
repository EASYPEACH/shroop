package com.easypeach.shroop.modules.auth.dto.request;

import lombok.Getter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
public class SignInRequest {
    @NotBlank
    @Size(min = 5, max = 255,message = "최소 5자 이상 입력해주세요")
    private String loginId;

    @NotBlank
    @Size(min = 5, max = 255,message = "최소 5자 이상 입력해주세요")
    private String password;
}
