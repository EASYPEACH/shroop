package com.easypeach.shroop.modules.auth.support;

import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME) // (1)
@Target(ElementType.PARAMETER) // (2)
@AuthenticationPrincipal(expression = "#this == 'anonymousUser' ? null : member") // (3)
public @interface LoginMember {

}