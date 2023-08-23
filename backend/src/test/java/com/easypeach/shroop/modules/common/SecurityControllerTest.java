package com.easypeach.shroop.modules.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.easypeach.shroop.infra.security.filter.JsonUsernamePasswordAuthenticationFilter;
import com.easypeach.shroop.modules.auth.controller.AuthController;
import com.easypeach.shroop.modules.auth.service.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;

@AutoConfigureRestDocs
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class SecurityControllerTest {
	@Autowired
	protected MockMvc mockMvc;

	@Autowired
	protected AuthService authService;

	@Autowired
	protected ObjectMapper objectMapper;

	@Autowired
	private JsonUsernamePasswordAuthenticationFilter jsonUsernamePasswordAuthenticationFilter;
}
