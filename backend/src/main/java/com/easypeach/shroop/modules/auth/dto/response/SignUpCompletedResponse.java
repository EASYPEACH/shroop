package com.easypeach.shroop.modules.auth.dto.response;

import lombok.Getter;

@Getter
public class SignUpCompletedResponse {
    private Long memberId;

    public SignUpCompletedResponse(){};

    public SignUpCompletedResponse(Long memberId){
        this.memberId = memberId;
    }
}
