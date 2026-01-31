package com.mediscan.dto.request;

import jakarta.validation.constraints.NotBlank;

/**
 * Pill search request params from client.
 * print: identifier text (OCR result, required); shape: drug shape; color: color class.
 */
public record PillSearchRequest(
	@NotBlank(message = "식별문자(print)는 필수입니다.")
	String print,

	String shape,

	String color
) {
	public String drugShape() {
		return shape;
	}

	public String colorClass() {
		return color;
	}
}
