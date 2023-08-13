package com.easypeach.shroop.modules.auth.dto.request;

import lombok.Getter;
import javax.validation.constraints.NotBlank;

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
}
