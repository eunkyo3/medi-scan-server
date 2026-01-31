package com.mediscan.client;

import com.mediscan.client.dto.PublicDataPillApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Feign client for MFDS pill identification API (data.go.kr).
 * Endpoint: MdcinGrnIdntfcInfoService03/getMdcinGrnIdntfcInfoList03
 */
@FeignClient(
	name = "publicDataPillApi",
	url = "${api.public-data.base-url}",
	configuration = PublicDataPillApiClientConfig.class
)
public interface PublicDataPillApiClient {

	@GetMapping("/MdcinGrnIdntfcInfoService03/getMdcinGrnIdntfcInfoList03")
	PublicDataPillApiResponse search(
		@RequestParam("serviceKey") String serviceKey,
		@RequestParam(value = "pageNo", defaultValue = "1") int pageNo,
		@RequestParam(value = "numOfRows", defaultValue = "100") int numOfRows,
		@RequestParam(value = "type", defaultValue = "json") String type,
		@RequestParam(value = "item_name", required = false) String itemName,
		@RequestParam(value = "entp_name", required = false) String entpName,
		@RequestParam(value = "item_seq", required = false) String itemSeq
	);
}
