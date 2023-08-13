package com.easypeach.shroop.modules.auth.dto.response;

import lombok.Getter;

@Getter
public class AccessTokenResponse {
    private String accessToken;

    public AccessTokenResponse() {
    }

    public AccessTokenResponse(String accessToken) {
        this.accessToken = accessToken;
    }
}
