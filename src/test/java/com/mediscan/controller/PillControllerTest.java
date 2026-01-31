package com.mediscan.controller;

import com.mediscan.dto.response.PillResponse;
import com.mediscan.service.PillSearchService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PillController.class)
class PillControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private PillSearchService pillSearchService;

	@Test
	@DisplayName("GET /api/v1/pills - returns 200 with pill list")
	void search_withValidParams_returnsOk() throws Exception {
		PillResponse pill = new PillResponse(
			"200808876", "가스디알정", "19540006", "일동제약",
			"녹색의 원형", null, "IDG", null, "원형", "연두",
			null, null, null, "7.6", "7.6", "3.6", null,
			"02390", "기타의 소화기관용약", null, null, "당의정",
			null, null, null, null, null, null, null, null,
			null, null, null, null, null, null
		);

		when(pillSearchService.search(any())).thenReturn(List.of(pill));

		mockMvc.perform(get("/api/v1/pills").param("print", "IDG"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$").isArray())
			.andExpect(jsonPath("$[0].itemName").value("가스디알정"))
			.andExpect(jsonPath("$[0].printFront").value("IDG"))
			.andExpect(jsonPath("$[0].drugShape").value("원형"))
			.andExpect(jsonPath("$[0].colorClass").value("연두"));
	}

	@Test
	@DisplayName("GET /api/v1/pills - returns empty array when no results")
	void search_noResults_returnsEmptyArray() throws Exception {
		when(pillSearchService.search(any())).thenReturn(List.of());

		mockMvc.perform(get("/api/v1/pills").param("print", "NONEXISTENT"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$").isArray())
			.andExpect(jsonPath("$.length()").value(0));
	}

	@Test
	@DisplayName("GET /api/v1/pills - missing print returns 400")
	void search_missingPrint_returnsBadRequest() throws Exception {
		mockMvc.perform(get("/api/v1/pills"))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.error").value("Bad Request"))
			.andExpect(jsonPath("$.details.print").exists());
	}

	@Test
	@DisplayName("GET /api/v1/pills - accepts shape and color params")
	void search_withShapeAndColor_returnsOk() throws Exception {
		when(pillSearchService.search(any())).thenReturn(List.of());

		mockMvc.perform(get("/api/v1/pills")
				.param("print", "IDG")
				.param("shape", "원형")
				.param("color", "연두"))
			.andExpect(status().isOk());
	}
}
