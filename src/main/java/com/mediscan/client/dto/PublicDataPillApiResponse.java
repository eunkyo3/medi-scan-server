package com.mediscan.client.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.List;

/**
 * MFDS pill identification API response wrapper.
 * Structure: { header, body: { pageNo, totalCount, numOfRows, items } }
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record PublicDataPillApiResponse(
	@JsonProperty("header") Header header,
	@JsonProperty("body") Body body
) {
	public List<PublicDataPillItem> getItems() {
		if (body == null || body.items() == null) {
			return List.of();
		}
		return body.items();
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public record Header(
		@JsonProperty("resultCode") String resultCode,
		@JsonProperty("resultMsg") String resultMsg
	) {}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public record Body(
		@JsonProperty("pageNo") Integer pageNo,
		@JsonProperty("totalCount") @JsonAlias("total_count") Integer totalCount,
		@JsonProperty("numOfRows") Integer numOfRows,
		@JsonProperty("items")
		@JsonDeserialize(using = PillItemsDeserializer.class)
		List<PublicDataPillItem> items
	) {
		public int getTotalCount() {
			return totalCount != null ? totalCount : 0;
		}
	}
}
