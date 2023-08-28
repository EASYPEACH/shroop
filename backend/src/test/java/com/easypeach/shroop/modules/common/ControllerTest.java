package com.easypeach.shroop.modules.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import com.easypeach.shroop.modules.auth.controller.AuthController;
import com.easypeach.shroop.modules.auth.service.AuthService;
import com.easypeach.shroop.modules.auth.service.PhoneAuthService;

import com.easypeach.shroop.modules.member.domain.MemberRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
@WebMvcTest({
	AuthController.class
})
@TestPropertySource(properties = "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration")
public abstract class ControllerTest {

	@Autowired
	protected MockMvc mockMvc;
	@MockBean
	protected AuthService authService;
	@Autowired
	protected ObjectMapper objectMapper;
	@MockBean
	protected MemberRepository memberRepository;

	@MockBean
	protected PhoneAuthService phoneAuthService;

}
