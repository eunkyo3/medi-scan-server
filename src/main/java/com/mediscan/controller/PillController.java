package com.mediscan.controller;

import com.mediscan.dto.request.PillSearchRequest;
import com.mediscan.dto.response.PillResponse;
import com.mediscan.service.PillSearchService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Pill search API for MediScan clients (Flutter, etc.).
 * Queries local DB by print/shape/color; returns 0..N items as JSON array.
 */
@RestController
@RequestMapping("/api/v1/pills")
@RequiredArgsConstructor
public class PillController {

	private final PillSearchService pillSearchService;

	/**
	 * Search pills by print (required), shape, color.
	 * Returns JSON array: empty, single item, or multiple items.
	 */
	@GetMapping
	public ResponseEntity<List<PillResponse>> search(@Valid @ModelAttribute PillSearchRequest request) {
		List<PillResponse> result = pillSearchService.search(request);
		return ResponseEntity.ok(result);
	}
}
