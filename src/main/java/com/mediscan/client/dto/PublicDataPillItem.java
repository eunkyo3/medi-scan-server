package com.mediscan.client.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Single pill item from MFDS API response. Maps all API fields.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record PublicDataPillItem(
	@JsonProperty("itemSeq") @JsonAlias("ITEM_SEQ") String itemSeq,
	@JsonProperty("itemName") @JsonAlias("ITEM_NAME") String itemName,
	@JsonProperty("entpSeq") @JsonAlias("ENTP_SEQ") String entpSeq,
	@JsonProperty("entpName") @JsonAlias("ENTP_NAME") String entpName,
	@JsonProperty("chart") @JsonAlias("CHART") String chart,
	@JsonProperty("itemImage") @JsonAlias("ITEM_IMAGE") String itemImage,
	@JsonProperty("printFront") @JsonAlias("PRINT_FRONT") String printFront,
	@JsonProperty("printBack") @JsonAlias("PRINT_BACK") String printBack,
	@JsonProperty("drugShape") @JsonAlias("DRUG_SHAPE") String drugShape,
	@JsonProperty("colorClass1") @JsonAlias("COLOR_CLASS1") String colorClass1,
	@JsonProperty("colorClass2") @JsonAlias("COLOR_CLASS2") String colorClass2,
	@JsonProperty("lineFront") @JsonAlias("LINE_FRONT") String lineFront,
	@JsonProperty("lineBack") @JsonAlias("LINE_BACK") String lineBack,
	@JsonProperty("lengLong") @JsonAlias("LENG_LONG") String lengLong,
	@JsonProperty("lengShort") @JsonAlias("LENG_SHORT") String lengShort,
	@JsonProperty("thick") @JsonAlias("THICK") String thick,
	@JsonProperty("imgRegistTs") @JsonAlias("IMG_REGIST_TS") String imgRegistTs,
	@JsonProperty("classNo") @JsonAlias("CLASS_NO") String classNo,
	@JsonProperty("className") @JsonAlias("CLASS_NAME") String className,
	@JsonProperty("etcOtcName") @JsonAlias("ETC_OTC_NAME") String etcOtcName,
	@JsonProperty("itemPermitDate") @JsonAlias("ITEM_PERMIT_DATE") String itemPermitDate,
	@JsonProperty("formCodeName") @JsonAlias("FORM_CODE_NAME") String formCodeName,
	@JsonProperty("markCodeFrontAnal") @JsonAlias("MARK_CODE_FRONT_ANAL") String markCodeFrontAnal,
	@JsonProperty("markCodeBackAnal") @JsonAlias("MARK_CODE_BACK_ANAL") String markCodeBackAnal,
	@JsonProperty("markCodeFrontImg") @JsonAlias("MARK_CODE_FRONT_IMG") String markCodeFrontImg,
	@JsonProperty("markCodeBackImg") @JsonAlias("MARK_CODE_BACK_IMG") String markCodeBackImg,
	@JsonProperty("itemEngName") @JsonAlias("ITEM_ENG_NAME") String itemEngName,
	@JsonProperty("changeDate") @JsonAlias("CHANGE_DATE") String changeDate,
	@JsonProperty("markCodeFront") @JsonAlias("MARK_CODE_FRONT") String markCodeFront,
	@JsonProperty("markCodeBack") @JsonAlias("MARK_CODE_BACK") String markCodeBack,
	@JsonProperty("ediCode") @JsonAlias("EDI_CODE") String ediCode,
	@JsonProperty("bizrno") @JsonAlias("BIZRNO") String bizrno,
	@JsonProperty("stdCd") @JsonAlias("STD_CD") String stdCd,
	@JsonProperty("efcyQesitm") @JsonAlias("EFCY_QESITM") String efcyQesitm,
	@JsonProperty("useMethodQesitm") @JsonAlias("USE_METHOD_QESITM") String useMethodQesitm,
	@JsonProperty("atpnQesitm") @JsonAlias("ATPN_QESITM") String atpnQesitm
) {
	public String colorClass() {
		if (colorClass1 != null && !colorClass1.isBlank()) {
			return colorClass1;
		}
		return colorClass2;
	}
}
