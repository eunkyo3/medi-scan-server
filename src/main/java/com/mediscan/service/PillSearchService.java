package com.mediscan.service;

import com.mediscan.dto.request.PillSearchRequest;
import com.mediscan.dto.response.PillResponse;
import com.mediscan.entity.PillInfo;
import com.mediscan.entity.SearchHistory;
import com.mediscan.repository.PillInfoRepository;
import com.mediscan.repository.SearchHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

/**
 * Searches pill_info by print, shape, color. No external API calls at search time
 * (data is synced on startup by PillDataSyncService).
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PillSearchService {

	private static final String RESPONSE_CODE_OK = "00";

	private final PillInfoRepository pillInfoRepository;
	private final SearchHistoryRepository searchHistoryRepository;

	public List<PillResponse> search(PillSearchRequest request) {
		String print = request.print();
		String shape = request.drugShape();
		String color = request.colorClass();

		List<PillInfo> results = pillInfoRepository.searchByPrintShapeColor(print, shape, color);

		saveSearchHistory(print, RESPONSE_CODE_OK, results.size());

		return results.stream()
			.map(this::toPillResponse)
			.toList();
	}

	private void saveSearchHistory(String searchKeyword, String responseCode, int resultCount) {
		SearchHistory history = SearchHistory.builder()
			.searchKeyword(searchKeyword)
			.searchedAt(Instant.now())
			.responseCode(responseCode)
			.resultCount(resultCount)
			.build();
		searchHistoryRepository.save(history);
	}

	private PillResponse toPillResponse(PillInfo info) {
		return new PillResponse(
			info.getItemSeq(),
			info.getItemName(),
			info.getEntpSeq(),
			info.getEntpName(),
			info.getChart(),
			info.getItemImage(),
			info.getPrintFront(),
			info.getPrintBack(),
			info.getDrugShape(),
			info.getColorClass(),
			info.getColorClass2(),
			info.getLineFront(),
			info.getLineBack(),
			info.getLengLong(),
			info.getLengShort(),
			info.getThick(),
			info.getImgRegistTs(),
			info.getClassNo(),
			info.getClassName(),
			info.getEtcOtcName(),
			info.getItemPermitDate(),
			info.getFormCodeName(),
			info.getMarkCodeFrontAnal(),
			info.getMarkCodeBackAnal(),
			info.getMarkCodeFrontImg(),
			info.getMarkCodeBackImg(),
			info.getItemEngName(),
			info.getChangeDate(),
			info.getMarkCodeFront(),
			info.getMarkCodeBack(),
			info.getEdiCode(),
			info.getBizrno(),
			info.getStdCd(),
			info.getEfcyQesitm(),
			info.getUseMethodQesitm(),
			info.getAtpnQesitm()
		);
	}
}
