package com.mediscan.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Pill search result DTO (33 fields, camelCase). null values omitted in JSON.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record PillResponse(
	@JsonProperty("itemSeq") String itemSeq,
	@JsonProperty("itemName") String itemName,
	@JsonProperty("entpSeq") String entpSeq,
	@JsonProperty("entpName") String entpName,
	@JsonProperty("chart") String chart,
	@JsonProperty("itemImage") String itemImage,
	@JsonProperty("printFront") String printFront,
	@JsonProperty("printBack") String printBack,
	@JsonProperty("drugShape") String drugShape,
	@JsonProperty("colorClass") String colorClass,
	@JsonProperty("colorClass2") String colorClass2,
	@JsonProperty("lineFront") String lineFront,
	@JsonProperty("lineBack") String lineBack,
	@JsonProperty("lengLong") String lengLong,
	@JsonProperty("lengShort") String lengShort,
	@JsonProperty("thick") String thick,
	@JsonProperty("imgRegistTs") String imgRegistTs,
	@JsonProperty("classNo") String classNo,
	@JsonProperty("className") String className,
	@JsonProperty("etcOtcName") String etcOtcName,
	@JsonProperty("itemPermitDate") String itemPermitDate,
	@JsonProperty("formCodeName") String formCodeName,
	@JsonProperty("markCodeFrontAnal") String markCodeFrontAnal,
	@JsonProperty("markCodeBackAnal") String markCodeBackAnal,
	@JsonProperty("markCodeFrontImg") String markCodeFrontImg,
	@JsonProperty("markCodeBackImg") String markCodeBackImg,
	@JsonProperty("itemEngName") String itemEngName,
	@JsonProperty("changeDate") String changeDate,
	@JsonProperty("markCodeFront") String markCodeFront,
	@JsonProperty("markCodeBack") String markCodeBack,
	@JsonProperty("ediCode") String ediCode,
	@JsonProperty("bizrno") String bizrno,
	@JsonProperty("stdCd") String stdCd,
	@JsonProperty("efcyQesitm") String efcyQesitm,
	@JsonProperty("useMethodQesitm") String useMethodQesitm,
	@JsonProperty("atpnQesitm") String atpnQesitm
) {}
