package com.mediscan.exception;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * Global exception handler: returns structured error responses for validation,
 * external API, and server errors.
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException ex) {
		Map<String, String> errors = ex.getBindingResult().getFieldErrors().stream()
			.collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage, (a, b) -> a));

		return ResponseEntity
			.status(HttpStatus.BAD_REQUEST)
			.body(Map.of(
				"error", "Bad Request",
				"message", "요청 파라미터 검증 실패",
				"details", errors
			));
	}

	@ExceptionHandler(NoResourceFoundException.class)
	public ResponseEntity<Map<String, Object>> handleNoResource(NoResourceFoundException ex) {
		return ResponseEntity
			.status(HttpStatus.NOT_FOUND)
			.body(Map.of(
				"error", "Not Found",
				"message", "요청한 API 경로를 찾을 수 없습니다. /api/v1/pills?print=값 형태로 호출해 주세요.",
				"path", ex.getResourcePath() != null ? ex.getResourcePath() : ""
			));
	}

	@ExceptionHandler(FeignException.class)
	public ResponseEntity<Map<String, Object>> handleFeign(FeignException ex) {
		log.warn("External API call failed: status={}, message={}", ex.status(), ex.getMessage());
		return ResponseEntity
			.status(HttpStatus.BAD_GATEWAY)
			.body(Map.of(
				"error", "Bad Gateway",
				"message", "식약처 공공데이터 API 호출 중 오류가 발생했습니다. API 키 설정 및 네트워크를 확인해 주세요.",
				"status", ex.status()
			));
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Map<String, Object>> handleException(Exception ex) {
		log.error("Server error occurred", ex);
		return ResponseEntity
			.status(HttpStatus.INTERNAL_SERVER_ERROR)
			.body(Map.of(
				"error", "Internal Server Error",
				"message", "일시적인 오류가 발생했습니다. 잠시 후 다시 시도해 주세요."
			));
	}
}
