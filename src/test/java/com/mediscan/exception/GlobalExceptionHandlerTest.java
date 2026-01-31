package com.mediscan.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import com.mediscan.controller.PillController;
import com.mediscan.service.PillSearchService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = PillController.class)
@Import(GlobalExceptionHandler.class)
class GlobalExceptionHandlerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private PillSearchService pillSearchService;

	@Test
	@DisplayName("Validation error - returns 400 with details")
	void validationError_returnsBadRequest() throws Exception {
		mockMvc.perform(get("/api/v1/pills"))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.error").value("Bad Request"))
			.andExpect(jsonPath("$.message").exists())
			.andExpect(jsonPath("$.details").exists())
			.andExpect(jsonPath("$.details.print").exists());
	}
}
