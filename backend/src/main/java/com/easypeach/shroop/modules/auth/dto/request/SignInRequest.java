package com.easypeach.shroop.modules.auth.dto.request;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class SignInRequest {
    @NotBlank
    private String loginId;

    @NotBlank
    private String password;
}
