package com.mediscan.service;

import com.mediscan.dto.request.PillSearchRequest;
import com.mediscan.dto.response.PillResponse;
import com.mediscan.entity.PillInfo;
import com.mediscan.repository.PillInfoRepository;
import com.mediscan.repository.SearchHistoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class PillSearchServiceTest {

	@Autowired
	private PillSearchService pillSearchService;

	@Autowired
	private PillInfoRepository pillInfoRepository;

	@Autowired
	private SearchHistoryRepository searchHistoryRepository;

	@MockBean
	private com.mediscan.client.PublicDataPillApiClient publicDataPillApiClient;

	@BeforeEach
	void setUp() {
		searchHistoryRepository.deleteAll();
		pillInfoRepository.deleteAll();

		PillInfo pill1 = PillInfo.builder()
			.itemSeq("200808876")
			.itemName("가스디알정50밀리그램")
			.printFront("IDG")
			.drugShape("원형")
			.colorClass("연두")
			.build();

		PillInfo pill2 = PillInfo.builder()
			.itemSeq("200808877")
			.itemName("다른알약정")
			.printFront("ABC")
			.drugShape("타원형")
			.colorClass("하양")
			.build();

		pillInfoRepository.saveAll(List.of(pill1, pill2));
	}

	@Test
	@DisplayName("Search by print - returns matching pills")
	void search_byPrint_returnsMatches() {
		PillSearchRequest request = new PillSearchRequest("IDG", null, null);
		List<PillResponse> result = pillSearchService.search(request);

		assertThat(result).hasSize(1);
		assertThat(result.get(0).itemName()).contains("가스디알정");
		assertThat(result.get(0).printFront()).isEqualTo("IDG");
	}

	@Test
	@DisplayName("Search by print, shape, color - returns matching pills")
	void search_byPrintShapeColor_returnsMatches() {
		PillSearchRequest request = new PillSearchRequest("IDG", "원형", "연두");
		List<PillResponse> result = pillSearchService.search(request);

		assertThat(result).hasSize(1);
		assertThat(result.get(0).drugShape()).isEqualTo("원형");
		assertThat(result.get(0).colorClass()).isEqualTo("연두");
	}

	@Test
	@DisplayName("Search with no match - returns empty list")
	void search_noMatch_returnsEmpty() {
		PillSearchRequest request = new PillSearchRequest("NONEXISTENT", null, null);
		List<PillResponse> result = pillSearchService.search(request);

		assertThat(result).isEmpty();
	}

	@Test
	@DisplayName("Search saves search history")
	void search_savesSearchHistory() {
		assertThat(searchHistoryRepository.count()).isZero();

		PillSearchRequest request = new PillSearchRequest("IDG", null, null);
		pillSearchService.search(request);

		assertThat(searchHistoryRepository.count()).isEqualTo(1);
	}
}
